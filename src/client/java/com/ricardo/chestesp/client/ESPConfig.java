package com.ricardo.chestesp.client;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ESPConfig {

    private ESPConfig() {
    }

    /*
     * ============================================================
     * CONFIG FILE
     * ============================================================
     */

    private static final Path CONFIG_FILE =
            FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve("chest-esp.properties");

    /*
     * ============================================================
     * GENERAL
     * ============================================================
     */

    public static boolean enabled = true;

    public static boolean throughWalls = true;

    public static boolean filledBoxes = false;

    public static int renderDistance = 32;

    /*
     * ============================================================
     * PERFORMANCE
     * ============================================================
     */

    public static boolean performanceMode = true;

    public static int cacheRefreshRate = 10;

    /*
     * ============================================================
     * LABELS
     * ============================================================
     */

    public static boolean showLabels = true;

    public static boolean showStorageLabels = true;

    public static boolean showUtilityLabels = true;

    public static boolean showDistance = true;

    public static int labelScale = 100;

    /*
     * ============================================================
     * STORAGE
     * ============================================================
     */

    public static boolean chests = true;

    public static boolean trappedChests = true;

    public static boolean enderChests = true;

    public static boolean barrels = true;

    public static boolean shulkerBoxes = true;

    public static boolean copperChests = true;

    /*
     * ============================================================
     * UTILITY
     * ============================================================
     */

    public static boolean furnaces = true;

    public static boolean blastFurnaces = true;

    public static boolean smokers = true;

    public static boolean hoppers = true;

    public static boolean droppers = true;

    public static boolean dispensers = true;

    public static boolean brewingStands = true;

    public static boolean crafters = true;

    /*
     * ============================================================
     * COLORS
     * ============================================================
     *
     * IMPORTANT:
     * These are RGB ONLY.
     *
     * Format:
     *
     * 0xRRGGBB
     *
     * There is NO alpha channel here.
     */

    public static int chestColor = 0xFFAA00;

    public static int trappedChestColor = 0xFF3333;

    public static int enderChestColor = 0xAA00FF;

    public static int barrelColor = 0xAA6633;

    public static int shulkerColor = 0xFF66FF;

    public static int copperChestColor = 0x00CCCC;

    public static int furnaceColor = 0x888888;

    public static int blastFurnaceColor = 0xFF5555;

    public static int smokerColor = 0xAA6633;

    public static int hopperColor = 0x666666;

    public static int dropperColor = 0x999999;

    public static int dispenserColor = 0x55AAFF;

    public static int brewingStandColor = 0xAA55FF;

    public static int crafterColor = 0x55FFAA;

    /*
     * ============================================================
     * LOAD
     * ============================================================
     */

    public static void load() {

        if (!Files.exists(CONFIG_FILE)) {
            save();
            return;
        }

        Properties properties = new Properties();

        try (InputStream input =
                     Files.newInputStream(CONFIG_FILE)) {

            properties.load(input);

            /*
             * GENERAL
             */

            enabled = getBoolean(
                    properties,
                    "enabled",
                    enabled
            );

            throughWalls = getBoolean(
                    properties,
                    "throughWalls",
                    throughWalls
            );

            filledBoxes = getBoolean(
                    properties,
                    "filledBoxes",
                    filledBoxes
            );

            renderDistance = getInt(
                    properties,
                    "renderDistance",
                    renderDistance,
                    8,
                    128
            );

            /*
             * PERFORMANCE
             */

            performanceMode = getBoolean(
                    properties,
                    "performanceMode",
                    performanceMode
            );

            cacheRefreshRate = getInt(
                    properties,
                    "cacheRefreshRate",
                    cacheRefreshRate,
                    1,
                    60
            );

            /*
             * LABELS
             */

            showLabels = getBoolean(
                    properties,
                    "showLabels",
                    showLabels
            );

            showStorageLabels = getBoolean(
                    properties,
                    "showStorageLabels",
                    showStorageLabels
            );

            showUtilityLabels = getBoolean(
                    properties,
                    "showUtilityLabels",
                    showUtilityLabels
            );

            showDistance = getBoolean(
                    properties,
                    "showDistance",
                    showDistance
            );

            labelScale = getInt(
                    properties,
                    "labelScale",
                    labelScale,
                    25,
                    200
            );

            /*
             * STORAGE
             */

            chests = getBoolean(
                    properties,
                    "chests",
                    chests
            );

            trappedChests = getBoolean(
                    properties,
                    "trappedChests",
                    trappedChests
            );

            enderChests = getBoolean(
                    properties,
                    "enderChests",
                    enderChests
            );

            barrels = getBoolean(
                    properties,
                    "barrels",
                    barrels
            );

            shulkerBoxes = getBoolean(
                    properties,
                    "shulkerBoxes",
                    shulkerBoxes
            );

            copperChests = getBoolean(
                    properties,
                    "copperChests",
                    copperChests
            );

            /*
             * UTILITY
             */

            furnaces = getBoolean(
                    properties,
                    "furnaces",
                    furnaces
            );

            blastFurnaces = getBoolean(
                    properties,
                    "blastFurnaces",
                    blastFurnaces
            );

            smokers = getBoolean(
                    properties,
                    "smokers",
                    smokers
            );

            hoppers = getBoolean(
                    properties,
                    "hoppers",
                    hoppers
            );

            droppers = getBoolean(
                    properties,
                    "droppers",
                    droppers
            );

            dispensers = getBoolean(
                    properties,
                    "dispensers",
                    dispensers
            );

            brewingStands = getBoolean(
                    properties,
                    "brewingStands",
                    brewingStands
            );

            crafters = getBoolean(
                    properties,
                    "crafters",
                    crafters
            );

            /*
             * COLORS
             */

            chestColor = getColor(
                    properties,
                    "chestColor",
                    chestColor
            );

            trappedChestColor = getColor(
                    properties,
                    "trappedChestColor",
                    trappedChestColor
            );

            enderChestColor = getColor(
                    properties,
                    "enderChestColor",
                    enderChestColor
            );

            barrelColor = getColor(
                    properties,
                    "barrelColor",
                    barrelColor
            );

            shulkerColor = getColor(
                    properties,
                    "shulkerColor",
                    shulkerColor
            );

            copperChestColor = getColor(
                    properties,
                    "copperChestColor",
                    copperChestColor
            );

            furnaceColor = getColor(
                    properties,
                    "furnaceColor",
                    furnaceColor
            );

            blastFurnaceColor = getColor(
                    properties,
                    "blastFurnaceColor",
                    blastFurnaceColor
            );

            smokerColor = getColor(
                    properties,
                    "smokerColor",
                    smokerColor
            );

            hopperColor = getColor(
                    properties,
                    "hopperColor",
                    hopperColor
            );

            dropperColor = getColor(
                    properties,
                    "dropperColor",
                    dropperColor
            );

            dispenserColor = getColor(
                    properties,
                    "dispenserColor",
                    dispenserColor
            );

            brewingStandColor = getColor(
                    properties,
                    "brewingStandColor",
                    brewingStandColor
            );

            crafterColor = getColor(
                    properties,
                    "crafterColor",
                    crafterColor
            );

        } catch (IOException exception) {

            exception.printStackTrace();
        }
    }

    /*
     * ============================================================
     * SAVE
     * ============================================================
     */

    public static void save() {

        Properties properties = new Properties();

        /*
         * GENERAL
         */

        properties.setProperty(
                "enabled",
                Boolean.toString(enabled)
        );

        properties.setProperty(
                "throughWalls",
                Boolean.toString(throughWalls)
        );

        properties.setProperty(
                "filledBoxes",
                Boolean.toString(filledBoxes)
        );

        properties.setProperty(
                "renderDistance",
                Integer.toString(renderDistance)
        );

        /*
         * PERFORMANCE
         */

        properties.setProperty(
                "performanceMode",
                Boolean.toString(performanceMode)
        );

        properties.setProperty(
                "cacheRefreshRate",
                Integer.toString(cacheRefreshRate)
        );

        /*
         * LABELS
         */

        properties.setProperty(
                "showLabels",
                Boolean.toString(showLabels)
        );

        properties.setProperty(
                "showStorageLabels",
                Boolean.toString(showStorageLabels)
        );

        properties.setProperty(
                "showUtilityLabels",
                Boolean.toString(showUtilityLabels)
        );

        properties.setProperty(
                "showDistance",
                Boolean.toString(showDistance)
        );

        properties.setProperty(
                "labelScale",
                Integer.toString(labelScale)
        );

        /*
         * STORAGE
         */

        properties.setProperty(
                "chests",
                Boolean.toString(chests)
        );

        properties.setProperty(
                "trappedChests",
                Boolean.toString(trappedChests)
        );

        properties.setProperty(
                "enderChests",
                Boolean.toString(enderChests)
        );

        properties.setProperty(
                "barrels",
                Boolean.toString(barrels)
        );

        properties.setProperty(
                "shulkerBoxes",
                Boolean.toString(shulkerBoxes)
        );

        properties.setProperty(
                "copperChests",
                Boolean.toString(copperChests)
        );

        /*
         * UTILITY
         */

        properties.setProperty(
                "furnaces",
                Boolean.toString(furnaces)
        );

        properties.setProperty(
                "blastFurnaces",
                Boolean.toString(blastFurnaces)
        );

        properties.setProperty(
                "smokers",
                Boolean.toString(smokers)
        );

        properties.setProperty(
                "hoppers",
                Boolean.toString(hoppers)
        );

        properties.setProperty(
                "droppers",
                Boolean.toString(droppers)
        );

        properties.setProperty(
                "dispensers",
                Boolean.toString(dispensers)
        );

        properties.setProperty(
                "brewingStands",
                Boolean.toString(brewingStands)
        );

        properties.setProperty(
                "crafters",
                Boolean.toString(crafters)
        );

        /*
         * COLORS
         */

        properties.setProperty(
                "chestColor",
                Integer.toString(chestColor)
        );

        properties.setProperty(
                "trappedChestColor",
                Integer.toString(trappedChestColor)
        );

        properties.setProperty(
                "enderChestColor",
                Integer.toString(enderChestColor)
        );

        properties.setProperty(
                "barrelColor",
                Integer.toString(barrelColor)
        );

        properties.setProperty(
                "shulkerColor",
                Integer.toString(shulkerColor)
        );

        properties.setProperty(
                "copperChestColor",
                Integer.toString(copperChestColor)
        );

        properties.setProperty(
                "furnaceColor",
                Integer.toString(furnaceColor)
        );

        properties.setProperty(
                "blastFurnaceColor",
                Integer.toString(blastFurnaceColor)
        );

        properties.setProperty(
                "smokerColor",
                Integer.toString(smokerColor)
        );

        properties.setProperty(
                "hopperColor",
                Integer.toString(hopperColor)
        );

        properties.setProperty(
                "dropperColor",
                Integer.toString(dropperColor)
        );

        properties.setProperty(
                "dispenserColor",
                Integer.toString(dispenserColor)
        );

        properties.setProperty(
                "brewingStandColor",
                Integer.toString(brewingStandColor)
        );

        properties.setProperty(
                "crafterColor",
                Integer.toString(crafterColor)
        );

        try {

            Files.createDirectories(
                    CONFIG_FILE.getParent()
            );

            try (OutputStream output =
                         Files.newOutputStream(CONFIG_FILE)) {

                properties.store(
                        output,
                        "Chest ESP Configuration"
                );
            }

        } catch (IOException exception) {

            exception.printStackTrace();
        }
    }

    /*
     * ============================================================
     * HELPERS
     * ============================================================
     */

    private static boolean getBoolean(
            Properties properties,
            String key,
            boolean defaultValue
    ) {

        String value =
                properties.getProperty(key);

        if (value == null) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    private static int getInt(
            Properties properties,
            String key,
            int defaultValue,
            int min,
            int max
    ) {

        String value =
                properties.getProperty(key);

        if (value == null) {
            return defaultValue;
        }

        try {

            int parsed =
                    Integer.parseInt(value);

            return Math.max(
                    min,
                    Math.min(max, parsed)
            );

        } catch (NumberFormatException exception) {

            return defaultValue;
        }
    }

    /*
     * RGB color loader.
     *
     * The old configuration may contain ARGB values
     * such as FFAA00. We intentionally strip the alpha
     * byte so the configuration is RGB-only.
     */

    private static int getColor(
            Properties properties,
            String key,
            int defaultValue
    ) {

        String value =
                properties.getProperty(key);

        if (value == null) {
            return defaultValue;
        }

        try {

            String cleaned =
                    value.trim()
                            .replace("#", "")
                            .replace("0x", "")
                            .replace("0X", "");

            long parsed =
                    Long.parseLong(
                            cleaned,
                            16
                    );

            /*
             * Keep only RRGGBB.
             */
            return (int) (parsed & 0xFFFFFF);

        } catch (NumberFormatException exception) {

            return defaultValue;
        }
    }
}
