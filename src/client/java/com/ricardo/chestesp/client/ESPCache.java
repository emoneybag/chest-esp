package com.ricardo.chestesp.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * Keeps a list of nearby blocks to highlight so the renderer doesn't have to search
 * the world every frame. Only chunks that are already loaded are inspected.
 */
public final class ESPCache {

    /** A block to highlight. */
    public record Target(BlockPos pos, ESPTarget type) {
    }

    private static List<Target> targets = List.of();
    private static int ticksSinceUpdate;
    private static BlockPos lastPlayerPos;
    private static int lastRenderDistance = -1;
    private static Level lastLevel;

    private ESPCache() {
    }

    public static List<Target> getTargets() {
        return targets;
    }

    /** Called every client tick; rescans only when something relevant changed. */
    public static void tick(Minecraft minecraft) {
        if (!ESPConfig.enabled || !ESPConfig.performanceMode) {
            clear();
            return;
        }
        if (minecraft.level == null || minecraft.player == null) {
            return;
        }

        ticksSinceUpdate++;

        boolean newWorld = lastLevel != minecraft.level;
        // Moving a few blocks changes which chests are in range.
        boolean moved = lastPlayerPos == null
                || minecraft.player.blockPosition().distSqr(lastPlayerPos) >= 16.0D;
        boolean distanceChanged = lastRenderDistance != ESPConfig.renderDistance;

        if (newWorld || moved || distanceChanged || ticksSinceUpdate >= ESPConfig.cacheRefreshRate) {
            update(minecraft);
        }
    }

    /** Rescans the loaded chunks around the player. */
    public static void update(Minecraft minecraft) {
        if (minecraft.level == null || minecraft.player == null) {
            clear();
            return;
        }

        BlockPos playerPos = minecraft.player.blockPosition();
        int range = ESPConfig.renderDistance;
        double rangeSquared = (double) range * range;
        int chunkRange = (range + 15) / 16;
        int playerChunkX = playerPos.getX() >> 4;
        int playerChunkZ = playerPos.getZ() >> 4;

        List<Target> found = new ArrayList<>();

        for (int chunkX = playerChunkX - chunkRange; chunkX <= playerChunkX + chunkRange; chunkX++) {
            for (int chunkZ = playerChunkZ - chunkRange; chunkZ <= playerChunkZ + chunkRange; chunkZ++) {
                // getChunkNow never loads or generates chunks.
                LevelChunk chunk = minecraft.level.getChunkSource().getChunkNow(chunkX, chunkZ);
                if (chunk == null) {
                    continue;
                }

                for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                    BlockPos pos = blockEntity.getBlockPos();
                    ESPTarget type = ESPTarget.of(blockEntity.getBlockState().getBlock());

                    if (type != null && pos.distSqr(playerPos) <= rangeSquared) {
                        found.add(new Target(pos.immutable(), type));
                    }
                }
            }
        }

        targets = found;
        ticksSinceUpdate = 0;
        lastPlayerPos = playerPos.immutable();
        lastRenderDistance = range;
        lastLevel = minecraft.level;
    }

    /** Forces a rescan on the next tick. */
    public static void invalidate() {
        lastPlayerPos = null;
    }

    public static void clear() {
        targets = List.of();
        ticksSinceUpdate = 0;
        lastPlayerPos = null;
        lastRenderDistance = -1;
        lastLevel = null;
    }
}
