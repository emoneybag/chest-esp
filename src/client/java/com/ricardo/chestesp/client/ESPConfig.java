package com.ricardo.chestesp.client;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Settings, stored in {@code config/chest-esp.properties} as plain {@code key=value} lines.
 * Colors are hex codes such as {@code #FFAA00}. Per-block settings live on {@link ESPTarget}.
 */
public final class ESPConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChestESPClient.MOD_ID);
    private static final Path FILE =
            FabricLoader.getInstance().getConfigDir().resolve("chest-esp.properties");

    public static boolean enabled = true;
    public static boolean throughWalls = true;
    public static boolean filledBoxes = false;
    public static int renderDistance = 32;

    public static boolean performanceMode = true;
    public static int cacheRefreshRate = 10;

    public static boolean showLabels = true;
    public static boolean showStorageLabels = true;
    public static boolean showUtilityLabels = true;
    public static boolean showDistance = true;
    public static int labelScale = 100;

    private ESPConfig() {
    }

    public static void load() {
        if (!Files.exists(FILE)) {
            save();
            return;
        }

        Properties p = new Properties();
        try (Reader in = Files.newBufferedReader(FILE)) {
            p.load(in);
        } catch (IOException e) {
            LOGGER.warn("Could not read {}", FILE, e);
            return;
        }

        enabled = readBoolean(p, "enabled", enabled);
        throughWalls = readBoolean(p, "throughWalls", throughWalls);
        filledBoxes = readBoolean(p, "filledBoxes", filledBoxes);
        renderDistance = readInt(p, "renderDistance", renderDistance, 8, 128);

        performanceMode = readBoolean(p, "performanceMode", performanceMode);
        cacheRefreshRate = readInt(p, "cacheRefreshRate", cacheRefreshRate, 1, 60);

        showLabels = readBoolean(p, "showLabels", showLabels);
        showStorageLabels = readBoolean(p, "showStorageLabels", showStorageLabels);
        showUtilityLabels = readBoolean(p, "showUtilityLabels", showUtilityLabels);
        showDistance = readBoolean(p, "showDistance", showDistance);
        labelScale = readInt(p, "labelScale", labelScale, 25, 200);

        for (ESPTarget target : ESPTarget.values()) {
            String key = key(target);
            target.enabled = readBoolean(p, key + ".enabled", target.enabled);

            int color = parseColor(p.getProperty(key + ".color"));
            if (color >= 0) {
                target.color = color;
            } else if (p.getProperty(key + ".color") != null) {
                LOGGER.warn("Ignoring invalid color for {}: {}", key, p.getProperty(key + ".color"));
            }
        }
    }

    public static void save() {
        List<String> lines = new ArrayList<>();
        lines.add("# Chest ESP settings. Colors are hex codes, e.g. #FFAA00 (no alpha).");

        lines.add("enabled=" + enabled);
        lines.add("throughWalls=" + throughWalls);
        lines.add("filledBoxes=" + filledBoxes);
        lines.add("renderDistance=" + renderDistance);

        lines.add("performanceMode=" + performanceMode);
        lines.add("cacheRefreshRate=" + cacheRefreshRate);

        lines.add("showLabels=" + showLabels);
        lines.add("showStorageLabels=" + showStorageLabels);
        lines.add("showUtilityLabels=" + showUtilityLabels);
        lines.add("showDistance=" + showDistance);
        lines.add("labelScale=" + labelScale);

        for (ESPTarget target : ESPTarget.values()) {
            lines.add(key(target) + ".enabled=" + target.enabled);
            lines.add(key(target) + ".color=" + toHex(target.color));
        }

        try {
            Files.createDirectories(FILE.getParent());
            Files.write(FILE, lines);
        } catch (IOException e) {
            LOGGER.warn("Could not write {}", FILE, e);
        }
    }

    /** Formats an RGB value as {@code #RRGGBB}. */
    public static String toHex(int rgb) {
        return String.format("#%06X", rgb & 0xFFFFFF);
    }

    /**
     * Parses {@code #RRGGBB}, {@code RRGGBB}, {@code 0xRRGGBB} or the short form {@code #RGB}.
     *
     * @return the RGB value, or -1 if the text isn't a valid color
     */
    public static int parseColor(String text) {
        if (text == null) {
            return -1;
        }

        String hex = text.trim();
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        } else if (hex.regionMatches(true, 0, "0x", 0, 2)) {
            hex = hex.substring(2);
        }

        if (hex.length() == 3) {
            StringBuilder expanded = new StringBuilder();
            for (char c : hex.toCharArray()) {
                expanded.append(c).append(c);
            }
            hex = expanded.toString();
        }

        if (!hex.matches("[0-9a-fA-F]{6}")) {
            return -1;
        }
        return Integer.parseInt(hex, 16);
    }

    private static String key(ESPTarget target) {
        return target.name().toLowerCase(Locale.ROOT);
    }

    private static boolean readBoolean(Properties p, String key, boolean fallback) {
        String value = p.getProperty(key);
        return value == null ? fallback : Boolean.parseBoolean(value.trim());
    }

    private static int readInt(Properties p, String key, int fallback, int min, int max) {
        String value = p.getProperty(key);
        if (value == null) {
            return fallback;
        }
        try {
            return Math.max(min, Math.min(max, Integer.parseInt(value.trim())));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
