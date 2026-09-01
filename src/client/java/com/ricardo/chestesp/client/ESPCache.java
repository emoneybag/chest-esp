package com.ricardo.chestesp.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ESPCache {

    private static final List<ESPDetector.Target> TARGETS =
            new ArrayList<>();

    private static int timer = 0;

    private static int lastRenderDistance = -1;

    private static BlockPos lastPlayerPos;

    private static Object lastLevel;

    private ESPCache() {
    }

    /**
     * Called every client tick.
     *
     * Only performs a full scan when necessary.
     */
    public static void tick(
            Minecraft minecraft
    ) {

        if (!ESPConfig.performanceMode) {
            clear();
            return;
        }

        if (!ESPConfig.enabled) {
            clear();
            return;
        }

        if (minecraft.level == null
                || minecraft.player == null) {

            return;
        }

        /*
         * A new world requires a complete rebuild.
         */
        if (lastLevel != minecraft.level) {

            clear();

            lastLevel = minecraft.level;

            update(minecraft);

            return;
        }

        timer++;

        int refreshRate =
                Math.max(
                        1,
                        ESPConfig.cacheRefreshRate
                );

        BlockPos currentPos =
                minecraft.player.blockPosition();

        /*
         * Only consider movement significant when
         * the player has moved at least 4 blocks.
         *
         * This prevents rebuilding the cache every
         * tick while the player is standing still
         * or moving tiny amounts.
         */
        boolean movedEnough =
                lastPlayerPos == null
                        || currentPos.distSqr(
                        lastPlayerPos
                ) >= 16.0D;

        boolean renderDistanceChanged =
                lastRenderDistance
                        != ESPConfig.renderDistance;

        if (
                timer >= refreshRate
                        || movedEnough
                        || renderDistanceChanged
        ) {

            update(minecraft);
        }
    }

    /**
     * Performs the actual chunk scan.
     */
    public static void update(
            Minecraft minecraft
    ) {

        if (minecraft.level == null
                || minecraft.player == null) {

            clear();

            return;
        }

        TARGETS.clear();

        BlockPos playerPos =
                minecraft.player.blockPosition();

        int renderDistance =
                ESPConfig.renderDistance;

        int chunkDistance =
                (renderDistance + 15) / 16;

        int playerChunkX =
                playerPos.getX() >> 4;

        int playerChunkZ =
                playerPos.getZ() >> 4;

        /*
         * Only inspect chunks that are already loaded.
         *
         * getChunkNow() does not force chunk generation/loading.
         */
        for (
                int chunkX =
                playerChunkX - chunkDistance;

                chunkX <=
                        playerChunkX + chunkDistance;

                chunkX++
        ) {

            for (
                    int chunkZ =
                    playerChunkZ - chunkDistance;

                    chunkZ <=
                            playerChunkZ + chunkDistance;

                    chunkZ++
            ) {

                LevelChunk chunk =
                        minecraft.level
                                .getChunkSource()
                                .getChunkNow(
                                        chunkX,
                                        chunkZ
                                );

                if (chunk == null) {
                    continue;
                }

                TARGETS.addAll(
                        ESPDetector.scanChunk(
                                chunk,
                                playerPos,
                                renderDistance
                        )
                );
            }
        }

        lastPlayerPos =
                playerPos.immutable();

        lastRenderDistance =
                renderDistance;

        timer = 0;
    }

    /**
     * Forces the next tick to rebuild the cache.
     */
    public static void invalidate() {

        timer = Integer.MAX_VALUE;

        lastPlayerPos = null;

        lastRenderDistance = -1;
    }

    /**
     * Completely removes cached targets.
     */
    public static void clear() {

        TARGETS.clear();

        timer = 0;

        lastPlayerPos = null;

        lastRenderDistance = -1;

        lastLevel = null;
    }

    /**
     * Returns an immutable view of the cache.
     *
     * The renderer never modifies this list.
     */
    public static List<ESPDetector.Target> getTargets() {

        return Collections.unmodifiableList(
                TARGETS
        );
    }
}