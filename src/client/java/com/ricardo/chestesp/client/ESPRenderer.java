package com.ricardo.chestesp.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoProperties;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public final class ESPRenderer {

    private ESPRenderer() {
    }

    /**
     * Renders all ESP targets.
     *
     * The renderer does not scan chunks or search the world.
     * It only renders the targets supplied by ESPCache.
     */
    public static void render(
            Minecraft minecraft
    ) {

        if (!ESPConfig.enabled) {
            return;
        }

        if (minecraft.level == null
                || minecraft.player == null) {

            return;
        }

        /*
         * Performance mode:
         *
         * Render directly from the cache.
         */
        if (ESPConfig.performanceMode) {

            for (
                    ESPDetector.Target target :
                    ESPCache.getTargets()
            ) {

                renderTarget(
                        target.pos(),
                        target.block(),
                        minecraft.player.blockPosition()
                );
            }

            return;
        }

        /*
         * Normal mode:
         *
         * Rebuild the cache before rendering.
         * This preserves the previous behavior where
         * non-performance mode scans continuously.
         */
        ESPCache.update(minecraft);

        for (
                ESPDetector.Target target :
                ESPCache.getTargets()
        ) {

            renderTarget(
                    target.pos(),
                    target.block(),
                    minecraft.player.blockPosition()
            );
        }
    }

    /*
     * ============================================================
     * TARGET
     * ============================================================
     */

    private static void renderTarget(
            BlockPos pos,
            Block block,
            BlockPos playerPos
    ) {

        double distanceSquared =
                pos.distSqr(playerPos);

        int color =
                ESPDetector.getESPColor(block);

        if (color == 0) {
            return;
        }

        /*
         * Draw the box/outline.
         */
        renderBox(
                pos,
                color
        );

        /*
         * Draw the label.
         */
        renderLabel(
                pos,
                block,
                distanceSquared
        );
    }

    /*
     * ============================================================
     * BOX
     * ============================================================
     */

    private static void renderBox(
            BlockPos pos,
            int color
    ) {

        /*
         * --------------------------------------------------------
         * FILLED BOX
         * --------------------------------------------------------
         *
         * Keep the existing filled-box behavior.
         *
         * We deliberately don't depend on the cuboid stroke
         * anymore because the explicit line rendering below
         * guarantees that the outline is visible.
         */

        if (ESPConfig.filledBoxes) {

            int fillColor =
                    withAlpha(
                            color,
                            60
                    );

            GizmoProperties fillProperties =
                    Gizmos.cuboid(
                            new net.minecraft.world.phys.AABB(pos),
                            net.minecraft.gizmos.GizmoStyle.fill(
                                    fillColor
                            )
                    );

            if (ESPConfig.throughWalls) {
                fillProperties.setAlwaysOnTop();
            }
        }

        /*
         * --------------------------------------------------------
         * OUTLINE
         * --------------------------------------------------------
         *
         * Minecraft 1.21.11 provides a dedicated line gizmo.
         *
         * A block has 8 corners and 12 edges:
         *
         * Bottom:
         *   0 -> 1
         *   1 -> 2
         *   2 -> 3
         *   3 -> 0
         *
         * Top:
         *   4 -> 5
         *   5 -> 6
         *   6 -> 7
         *   7 -> 4
         *
         * Vertical:
         *   0 -> 4
         *   1 -> 5
         *   2 -> 6
         *   3 -> 7
         */

        double x =
                pos.getX();

        double y =
                pos.getY();

        double z =
                pos.getZ();

        double x1 =
                x + 1.0D;

        double y1 =
                y + 1.0D;

        double z1 =
                z + 1.0D;

        Vec3 p000 =
                new Vec3(x, y, z);

        Vec3 p100 =
                new Vec3(x1, y, z);

        Vec3 p110 =
                new Vec3(x1, y, z1);

        Vec3 p010 =
                new Vec3(x, y, z1);

        Vec3 p001 =
                new Vec3(x, y1, z);

        Vec3 p101 =
                new Vec3(x1, y1, z);

        Vec3 p111 =
                new Vec3(x1, y1, z1);

        Vec3 p011 =
                new Vec3(x, y1, z1);

        /*
         * Minecraft gizmo colors use ARGB.
         *
         * The configuration stores RGB, so make the
         * outline fully opaque.
         */
        int lineColor =
                (color & 0x00FFFFFF)
                        | 0xFF000000;

        /*
         * Bottom four edges.
         */
        renderLine(
                p000,
                p100,
                lineColor
        );

        renderLine(
                p100,
                p110,
                lineColor
        );

        renderLine(
                p110,
                p010,
                lineColor
        );

        renderLine(
                p010,
                p000,
                lineColor
        );

        /*
         * Top four edges.
         */
        renderLine(
                p001,
                p101,
                lineColor
        );

        renderLine(
                p101,
                p111,
                lineColor
        );

        renderLine(
                p111,
                p011,
                lineColor
        );

        renderLine(
                p011,
                p001,
                lineColor
        );

        /*
         * Four vertical edges.
         */
        renderLine(
                p000,
                p001,
                lineColor
        );

        renderLine(
                p100,
                p101,
                lineColor
        );

        renderLine(
                p110,
                p111,
                lineColor
        );

        renderLine(
                p010,
                p011,
                lineColor
        );
    }

    /*
     * ============================================================
     * LINE
     * ============================================================
     */

    private static void renderLine(
            Vec3 start,
            Vec3 end,
            int color
    ) {

        GizmoProperties properties =
                Gizmos.line(
                        start,
                        end,
                        color,
                        2.0F
                );

        /*
         * Preserve the existing Through Walls option.
         */
        if (ESPConfig.throughWalls) {
            properties.setAlwaysOnTop();
        }
    }

    /*
     * ============================================================
     * LABEL
     * ============================================================
     */

    private static void renderLabel(
            BlockPos pos,
            Block block,
            double distanceSquared
    ) {

        if (!ESPConfig.showLabels) {
            return;
        }

        boolean storage =
                ESPDetector.isStorageBlock(
                        block
                );

        boolean utility =
                ESPDetector.isUtilityBlock(
                        block
                );

        /*
         * Respect category label settings.
         */
        if (
                storage
                        && !ESPConfig.showStorageLabels
        ) {
            return;
        }

        if (
                utility
                        && !ESPConfig.showUtilityLabels
        ) {
            return;
        }

        String label =
                ESPDetector.getBlockName(
                        block
                );

        /*
         * Add distance when enabled.
         */
        if (ESPConfig.showDistance) {

            int distance =
                    (int) Math.round(
                            Math.sqrt(
                                    distanceSquared
                            )
                    );

            label +=
                    " • "
                            + distance
                            + "m";
        }

        float scale =
                Math.max(
                        0.1F,
                        ESPConfig.labelScale
                                / 100.0F
                );

        GizmoProperties properties =
                Gizmos.billboardTextOverBlock(
                        label,
                        pos,
                        0,
                        0xFFFFFFFF,
                        scale
                );

        /*
         * Preserve Through Walls behavior.
         */
        if (ESPConfig.throughWalls) {
            properties.setAlwaysOnTop();
        }
    }

    /*
     * ============================================================
     * COLOR
     * ============================================================
     */

    private static int withAlpha(
            int color,
            int alpha
    ) {

        return (color & 0x00FFFFFF)
                | ((alpha & 0xFF) << 24);
    }
}
