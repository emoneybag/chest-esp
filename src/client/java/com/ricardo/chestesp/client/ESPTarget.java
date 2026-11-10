package com.ricardo.chestesp.client;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;

/**
 * Every kind of block the ESP can highlight.
 *
 * {@link #enabled} and {@link #color} are user settings, loaded and saved by {@link ESPConfig}.
 */
public enum ESPTarget {
    CHEST("Chest", "Chests", true, 0xFFAA00),
    TRAPPED_CHEST("Trapped Chest", "Trapped Chests", true, 0xFF3333),
    ENDER_CHEST("Ender Chest", "Ender Chests", true, 0xAA00FF),
    BARREL("Barrel", "Barrels", true, 0xAA6633),
    SHULKER_BOX("Shulker Box", "Shulker Boxes", true, 0xFF66FF),
    COPPER_CHEST("Copper Chest", "Copper Chests", true, 0x00CCCC),

    FURNACE("Furnace", "Furnaces", false, 0x888888),
    BLAST_FURNACE("Blast Furnace", "Blast Furnaces", false, 0xFF5555),
    SMOKER("Smoker", "Smokers", false, 0xAA6633),
    HOPPER("Hopper", "Hoppers", false, 0x666666),
    DROPPER("Dropper", "Droppers", false, 0x999999),
    DISPENSER("Dispenser", "Dispensers", false, 0x55AAFF),
    BREWING_STAND("Brewing Stand", "Brewing Stands", false, 0xAA55FF),
    CRAFTER("Crafter", "Crafters", false, 0x55FFAA);

    public final String label;
    public final String plural;
    /** Storage blocks vs. utility blocks; labels can be toggled per group. */
    public final boolean storage;
    public final int defaultColor;

    public boolean enabled = true;
    /** RGB, no alpha. */
    public int color;

    ESPTarget(String label, String plural, boolean storage, int defaultColor) {
        this.label = label;
        this.plural = plural;
        this.storage = storage;
        this.defaultColor = defaultColor;
        this.color = defaultColor;
    }

    /** Returns the target for a block, or null if the block isn't supported. */
    public static ESPTarget of(Block block) {
        if (block instanceof ShulkerBoxBlock) {
            return SHULKER_BOX;
        }
        return Lookup.BY_BLOCK.get(block);
    }

    /** Built on first use so vanilla blocks are guaranteed to be registered by then. */
    private static final class Lookup {
        static final Map<Block, ESPTarget> BY_BLOCK = new HashMap<>();

        static {
            BY_BLOCK.put(Blocks.CHEST, CHEST);
            BY_BLOCK.put(Blocks.TRAPPED_CHEST, TRAPPED_CHEST);
            BY_BLOCK.put(Blocks.ENDER_CHEST, ENDER_CHEST);
            BY_BLOCK.put(Blocks.BARREL, BARREL);

            for (Block copper : new Block[] {
                    Blocks.COPPER_CHEST, Blocks.EXPOSED_COPPER_CHEST,
                    Blocks.WEATHERED_COPPER_CHEST, Blocks.OXIDIZED_COPPER_CHEST,
                    Blocks.WAXED_COPPER_CHEST, Blocks.WAXED_EXPOSED_COPPER_CHEST,
                    Blocks.WAXED_WEATHERED_COPPER_CHEST, Blocks.WAXED_OXIDIZED_COPPER_CHEST}) {
                BY_BLOCK.put(copper, COPPER_CHEST);
            }

            BY_BLOCK.put(Blocks.FURNACE, FURNACE);
            BY_BLOCK.put(Blocks.BLAST_FURNACE, BLAST_FURNACE);
            BY_BLOCK.put(Blocks.SMOKER, SMOKER);
            BY_BLOCK.put(Blocks.HOPPER, HOPPER);
            BY_BLOCK.put(Blocks.DROPPER, DROPPER);
            BY_BLOCK.put(Blocks.DISPENSER, DISPENSER);
            BY_BLOCK.put(Blocks.BREWING_STAND, BREWING_STAND);
            BY_BLOCK.put(Blocks.CRAFTER, CRAFTER);
        }
    }
}
