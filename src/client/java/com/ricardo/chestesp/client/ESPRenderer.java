package com.ricardo.chestesp.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoProperties;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Draws the highlights for everything in {@link ESPCache}. */
public final class ESPRenderer {

    private static final int FILL_ALPHA = 60;
    private static final float LINE_WIDTH = 2.0F;

    /** Corner pairs that make up the 12 edges of a box (corners are numbered bottom ring, then top ring). */
    private static final int[][] EDGES = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0},
            {4, 5}, {5, 6}, {6, 7}, {7, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };

    private ESPRenderer() {
    }

    public static void render(Minecraft minecraft) {
        if (!ESPConfig.enabled || minecraft.level == null || minecraft.player == null) {
            return;
        }

        if (!ESPConfig.performanceMode) {
            ESPCache.update(minecraft);
        }

        BlockPos playerPos = minecraft.player.blockPosition();

        for (ESPCache.Target target : ESPCache.getTargets()) {
            if (!target.type().enabled) {
                continue;
            }
            drawBox(target.pos(), target.type().color);
            drawLabel(target, playerPos);
        }
    }

    private static void drawBox(BlockPos pos, int rgb) {
        if (ESPConfig.filledBoxes) {
            int fill = (rgb & 0xFFFFFF) | (FILL_ALPHA << 24);
            applyThroughWalls(Gizmos.cuboid(new AABB(pos), GizmoStyle.fill(fill)));
        }

        double x0 = pos.getX();
        double y0 = pos.getY();
        double z0 = pos.getZ();
        double x1 = x0 + 1.0D;
        double y1 = y0 + 1.0D;
        double z1 = z0 + 1.0D;

        Vec3[] corners = {
                new Vec3(x0, y0, z0), new Vec3(x1, y0, z0), new Vec3(x1, y0, z1), new Vec3(x0, y0, z1),
                new Vec3(x0, y1, z0), new Vec3(x1, y1, z0), new Vec3(x1, y1, z1), new Vec3(x0, y1, z1)
        };

        int lineColor = (rgb & 0xFFFFFF) | 0xFF000000;
        for (int[] edge : EDGES) {
            applyThroughWalls(Gizmos.line(corners[edge[0]], corners[edge[1]], lineColor, LINE_WIDTH));
        }
    }

    private static void drawLabel(ESPCache.Target target, BlockPos playerPos) {
        ESPTarget type = target.type();
        boolean groupEnabled = type.storage ? ESPConfig.showStorageLabels : ESPConfig.showUtilityLabels;
        if (!ESPConfig.showLabels || !groupEnabled) {
            return;
        }

        String text = type.label;
        if (ESPConfig.showDistance) {
            text += " \u2022 " + Math.round(Math.sqrt(target.pos().distSqr(playerPos))) + "m";
        }

        float scale = Math.max(0.1F, ESPConfig.labelScale / 100.0F);
        applyThroughWalls(Gizmos.billboardTextOverBlock(text, target.pos(), 0, 0xFFFFFFFF, scale));
    }

    private static void applyThroughWalls(GizmoProperties properties) {
        if (ESPConfig.throughWalls) {
            properties.setAlwaysOnTop();
        }
    }
}
