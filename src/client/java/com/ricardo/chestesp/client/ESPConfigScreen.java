package com.ricardo.chestesp.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ESPConfigScreen {

    private ESPConfigScreen() {
    }

    public static Screen create(Screen parent) {

        ConfigBuilder builder =
                ConfigBuilder.create()
                        .setParentScreen(parent)
                        .setTitle(
                                Component.literal("Chest ESP")
                        );

        ConfigEntryBuilder entries =
                builder.entryBuilder();

        /*
         * ========================================================
         * GENERAL
         * ========================================================
         */

        ConfigCategory general =
                builder.getOrCreateCategory(
                        Component.literal("General")
                );

        general.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Enable ESP"),
                                ESPConfig.enabled
                        )
                        .setTooltip(
                                Component.literal(
                                        "Enable or disable Chest ESP."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.enabled = value
                        )
                        .build()
        );

        general.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Through Walls"),
                                ESPConfig.throughWalls
                        )
                        .setTooltip(
                                Component.literal(
                                        "Render ESP boxes through walls."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.throughWalls = value
                        )
                        .build()
        );

        general.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Filled Boxes"),
                                ESPConfig.filledBoxes
                        )
                        .setTooltip(
                                Component.literal(
                                        "Fill ESP boxes with transparency."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.filledBoxes = value
                        )
                        .build()
        );

        general.addEntry(
                entries.startIntSlider(
                                Component.literal("Render Distance"),
                                ESPConfig.renderDistance,
                                8,
                                128
                        )
                        .setTooltip(
                                Component.literal(
                                        "Maximum ESP distance in blocks."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.renderDistance = value
                        )
                        .build()
        );

        /*
         * ========================================================
         * PERFORMANCE
         * ========================================================
         */

        ConfigCategory performance =
                builder.getOrCreateCategory(
                        Component.literal("Performance")
                );

        performance.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Performance Mode"),
                                ESPConfig.performanceMode
                        )
                        .setTooltip(
                                Component.literal(
                                        "Cache detected blocks instead of scanning every frame."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.performanceMode = value
                        )
                        .build()
        );

        performance.addEntry(
                entries.startIntSlider(
                                Component.literal("Cache Refresh Rate"),
                                ESPConfig.cacheRefreshRate,
                                1,
                                60
                        )
                        .setTooltip(
                                Component.literal(
                                        "Number of client ticks between cache updates."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.cacheRefreshRate = value
                        )
                        .build()
        );

        /*
         * ========================================================
         * LABELS
         * ========================================================
         */

        ConfigCategory labels =
                builder.getOrCreateCategory(
                        Component.literal("Labels")
                );

        labels.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Show Labels"),
                                ESPConfig.showLabels
                        )
                        .setTooltip(
                                Component.literal(
                                        "Show the name of detected blocks."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.showLabels = value
                        )
                        .build()
        );

        labels.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Storage Labels"),
                                ESPConfig.showStorageLabels
                        )
                        .setTooltip(
                                Component.literal(
                                        "Show labels for storage blocks."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.showStorageLabels = value
                        )
                        .build()
        );

        labels.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Utility Labels"),
                                ESPConfig.showUtilityLabels
                        )
                        .setTooltip(
                                Component.literal(
                                        "Show labels for utility blocks."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.showUtilityLabels = value
                        )
                        .build()
        );

        labels.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Show Distance"),
                                ESPConfig.showDistance
                        )
                        .setTooltip(
                                Component.literal(
                                        "Show the distance next to labels."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.showDistance = value
                        )
                        .build()
        );

        labels.addEntry(
                entries.startIntSlider(
                                Component.literal("Label Scale"),
                                ESPConfig.labelScale,
                                25,
                                200
                        )
                        .setTooltip(
                                Component.literal(
                                        "25% to 200%. 100% is normal size."
                                )
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.labelScale = value
                        )
                        .build()
        );

        /*
         * ========================================================
         * STORAGE
         * ========================================================
         */

        ConfigCategory storage =
                builder.getOrCreateCategory(
                        Component.literal("Storage")
                );

        storage.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Chests"),
                                ESPConfig.chests
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.chests = value
                        )
                        .build()
        );

        storage.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Trapped Chests"),
                                ESPConfig.trappedChests
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.trappedChests = value
                        )
                        .build()
        );

        storage.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Ender Chests"),
                                ESPConfig.enderChests
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.enderChests = value
                        )
                        .build()
        );

        storage.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Barrels"),
                                ESPConfig.barrels
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.barrels = value
                        )
                        .build()
        );

        storage.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Shulker Boxes"),
                                ESPConfig.shulkerBoxes
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.shulkerBoxes = value
                        )
                        .build()
        );

        storage.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Copper Chests"),
                                ESPConfig.copperChests
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.copperChests = value
                        )
                        .build()
        );

        /*
         * ========================================================
         * UTILITY
         * ========================================================
         */

        ConfigCategory utility =
                builder.getOrCreateCategory(
                        Component.literal("Utility")
                );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Furnaces"),
                                ESPConfig.furnaces
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.furnaces = value
                        )
                        .build()
        );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Blast Furnaces"),
                                ESPConfig.blastFurnaces
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.blastFurnaces = value
                        )
                        .build()
        );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Smokers"),
                                ESPConfig.smokers
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.smokers = value
                        )
                        .build()
        );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Hoppers"),
                                ESPConfig.hoppers
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.hoppers = value
                        )
                        .build()
        );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Droppers"),
                                ESPConfig.droppers
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.droppers = value
                        )
                        .build()
        );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Dispensers"),
                                ESPConfig.dispensers
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.dispensers = value
                        )
                        .build()
        );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Brewing Stands"),
                                ESPConfig.brewingStands
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.brewingStands = value
                        )
                        .build()
        );

        utility.addEntry(
                entries.startBooleanToggle(
                                Component.literal("Crafters"),
                                ESPConfig.crafters
                        )
                        .setSaveConsumer(
                                value -> ESPConfig.crafters = value
                        )
                        .build()
        );

        /*
         * ========================================================
         * COLORS
         * ========================================================
         *
         * IMPORTANT:
         *
         * We intentionally DO NOT use startColorField().
         *
         * Cloth Config 21.11 can expose the color field as an
         * AbstractWidget in this setup, which causes:
         *
         * addEntry(AbstractConfigListEntry)
         *
         * to fail.
         *
         * Instead we use RGB integer fields.
         *
         * Enter colors as:
         *
         * 16755200
         *
         * or use decimal values calculated from RRGGBB.
         *
         * This also guarantees there is NO alpha channel.
         */

        ConfigCategory colors =
                builder.getOrCreateCategory(
                        Component.literal("Colors")
                );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Chest Color"),
                                ESPConfig.chestColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setTooltip(
                                Component.literal(
                                        "RGB color. Example: 0xFFAA00 = orange."
                                )
                        )
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.chestColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Trapped Chest Color"),
                                ESPConfig.trappedChestColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.trappedChestColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Ender Chest Color"),
                                ESPConfig.enderChestColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.enderChestColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Barrel Color"),
                                ESPConfig.barrelColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.barrelColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Shulker Box Color"),
                                ESPConfig.shulkerColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.shulkerColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Copper Chest Color"),
                                ESPConfig.copperChestColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.copperChestColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Furnace Color"),
                                ESPConfig.furnaceColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.furnaceColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Blast Furnace Color"),
                                ESPConfig.blastFurnaceColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.blastFurnaceColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Smoker Color"),
                                ESPConfig.smokerColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.smokerColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Hopper Color"),
                                ESPConfig.hopperColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.hopperColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Dropper Color"),
                                ESPConfig.dropperColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.dropperColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Dispenser Color"),
                                ESPConfig.dispenserColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.dispenserColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Brewing Stand Color"),
                                ESPConfig.brewingStandColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.brewingStandColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        colors.addEntry(
                entries.startIntField(
                                Component.literal("Crafter Color"),
                                ESPConfig.crafterColor
                        )
                        .setMin(0)
                        .setMax(0xFFFFFF)
                        .setSaveConsumer(
                                value ->
                                        ESPConfig.crafterColor =
                                                value & 0xFFFFFF
                        )
                        .build()
        );

        /*
         * ========================================================
         * SAVE
         * ========================================================
         */

        builder.setSavingRunnable(
                ESPConfig::save
        );

        return builder.build();
    }

}
