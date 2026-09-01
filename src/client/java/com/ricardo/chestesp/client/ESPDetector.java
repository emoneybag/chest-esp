package com.ricardo.chestesp.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.List;

public final class ESPDetector {

    private ESPDetector() {
    }

    /**
     * A detected ESP target.
     *
     * We store the position and block type.
     * Rendering does not need to inspect the world again.
     */
    public record Target(
            BlockPos pos,
            Block block
    ) {
    }

    /**
     * Scans a single chunk for blocks supported by Chest ESP.
     */
    public static List<Target> scanChunk(
            LevelChunk chunk,
            BlockPos playerPos,
            int renderDistance
    ) {

        List<Target> targets =
                new ArrayList<>();

        double maxDistanceSquared =
                (double) renderDistance
                        * renderDistance;

        for (BlockEntity blockEntity :
                chunk.getBlockEntities().values()) {

            BlockPos pos =
                    blockEntity
                            .getBlockPos();

            if (pos.distSqr(playerPos)
                    > maxDistanceSquared) {

                continue;
            }

            Block block =
                    blockEntity
                            .getBlockState()
                            .getBlock();

            /*
             * getESPColor() returning 0 means:
             *
             * - unsupported block
             * - disabled block type
             */
            if (getESPColor(block) == 0) {
                continue;
            }

            targets.add(
                    new Target(
                            pos.immutable(),
                            block
                    )
            );
        }

        return targets;
    }

    /**
     * Returns the configured ESP color for a block.
     *
     * 0 means the block should not be rendered.
     */
    public static int getESPColor(
            Block block
    ) {

        /*
         * STORAGE
         */

        if (block == Blocks.CHEST) {

            return ESPConfig.chests
                    ? ESPConfig.chestColor
                    : 0;
        }

        if (block == Blocks.TRAPPED_CHEST) {

            return ESPConfig.trappedChests
                    ? ESPConfig.trappedChestColor
                    : 0;
        }

        if (block == Blocks.ENDER_CHEST) {

            return ESPConfig.enderChests
                    ? ESPConfig.enderChestColor
                    : 0;
        }

        if (block == Blocks.BARREL) {

            return ESPConfig.barrels
                    ? ESPConfig.barrelColor
                    : 0;
        }

        if (block instanceof ShulkerBoxBlock) {

            return ESPConfig.shulkerBoxes
                    ? ESPConfig.shulkerColor
                    : 0;
        }

        if (isCopperChest(block)) {

            return ESPConfig.copperChests
                    ? ESPConfig.copperChestColor
                    : 0;
        }

        /*
         * UTILITY
         */

        if (block == Blocks.FURNACE) {

            return ESPConfig.furnaces
                    ? ESPConfig.furnaceColor
                    : 0;
        }

        if (block == Blocks.BLAST_FURNACE) {

            return ESPConfig.blastFurnaces
                    ? ESPConfig.blastFurnaceColor
                    : 0;
        }

        if (block == Blocks.SMOKER) {

            return ESPConfig.smokers
                    ? ESPConfig.smokerColor
                    : 0;
        }

        if (block == Blocks.HOPPER) {

            return ESPConfig.hoppers
                    ? ESPConfig.hopperColor
                    : 0;
        }

        if (block == Blocks.DROPPER) {

            return ESPConfig.droppers
                    ? ESPConfig.dropperColor
                    : 0;
        }

        if (block == Blocks.DISPENSER) {

            return ESPConfig.dispensers
                    ? ESPConfig.dispenserColor
                    : 0;
        }

        if (block == Blocks.BREWING_STAND) {

            return ESPConfig.brewingStands
                    ? ESPConfig.brewingStandColor
                    : 0;
        }

        if (block == Blocks.CRAFTER) {

            return ESPConfig.crafters
                    ? ESPConfig.crafterColor
                    : 0;
        }

        return 0;
    }

    /**
     * Returns the display name used by the ESP label.
     */
    public static String getBlockName(
            Block block
    ) {

        if (block == Blocks.CHEST) {
            return "Chest";
        }

        if (block == Blocks.TRAPPED_CHEST) {
            return "Trapped Chest";
        }

        if (block == Blocks.ENDER_CHEST) {
            return "Ender Chest";
        }

        if (block == Blocks.BARREL) {
            return "Barrel";
        }

        if (block instanceof ShulkerBoxBlock) {
            return "Shulker Box";
        }

        if (isCopperChest(block)) {
            return "Copper Chest";
        }

        if (block == Blocks.FURNACE) {
            return "Furnace";
        }

        if (block == Blocks.BLAST_FURNACE) {
            return "Blast Furnace";
        }

        if (block == Blocks.SMOKER) {
            return "Smoker";
        }

        if (block == Blocks.HOPPER) {
            return "Hopper";
        }

        if (block == Blocks.DROPPER) {
            return "Dropper";
        }

        if (block == Blocks.DISPENSER) {
            return "Dispenser";
        }

        if (block == Blocks.BREWING_STAND) {
            return "Brewing Stand";
        }

        if (block == Blocks.CRAFTER) {
            return "Crafter";
        }

        return "Unknown";
    }

    /**
     * Determines whether a block belongs to the storage category.
     */
    public static boolean isStorageBlock(
            Block block
    ) {

        return block == Blocks.CHEST
                || block == Blocks.TRAPPED_CHEST
                || block == Blocks.ENDER_CHEST
                || block == Blocks.BARREL
                || block instanceof ShulkerBoxBlock
                || isCopperChest(block);
    }

    /**
     * Determines whether a block belongs to the utility category.
     */
    public static boolean isUtilityBlock(
            Block block
    ) {

        return block == Blocks.FURNACE
                || block == Blocks.BLAST_FURNACE
                || block == Blocks.SMOKER
                || block == Blocks.HOPPER
                || block == Blocks.DROPPER
                || block == Blocks.DISPENSER
                || block == Blocks.BREWING_STAND
                || block == Blocks.CRAFTER;
    }

    /**
     * Minecraft 1.21.11 copper chest variants.
     */
    private static boolean isCopperChest(
            Block block
    ) {

        return block == Blocks.COPPER_CHEST
                || block == Blocks.EXPOSED_COPPER_CHEST
                || block == Blocks.WEATHERED_COPPER_CHEST
                || block == Blocks.OXIDIZED_COPPER_CHEST
                || block == Blocks.WAXED_COPPER_CHEST
                || block == Blocks.WAXED_EXPOSED_COPPER_CHEST
                || block == Blocks.WAXED_WEATHERED_COPPER_CHEST
                || block == Blocks.WAXED_OXIDIZED_COPPER_CHEST;
    }
}