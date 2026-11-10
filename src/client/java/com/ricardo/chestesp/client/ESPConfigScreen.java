package com.ricardo.chestesp.client;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** The Cloth Config screen opened from Mod Menu. */
public final class ESPConfigScreen {

    private ESPConfigScreen() {
    }

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Chest ESP"))
                .setSavingRunnable(ESPConfig::save);

        ConfigEntryBuilder entries = builder.entryBuilder();

        ConfigCategory general = category(builder, "General");
        toggle(entries, general, "Enable ESP", "Turn Chest ESP on or off.",
                ESPConfig.enabled, v -> ESPConfig.enabled = v);
        toggle(entries, general, "Through Walls", "Draw highlights through blocks.",
                ESPConfig.throughWalls, v -> ESPConfig.throughWalls = v);
        toggle(entries, general, "Filled Boxes", "Fill the boxes with a translucent color.",
                ESPConfig.filledBoxes, v -> ESPConfig.filledBoxes = v);
        slider(entries, general, "Render Distance", "Maximum distance in blocks.",
                ESPConfig.renderDistance, 8, 128, v -> ESPConfig.renderDistance = v);

        ConfigCategory performance = category(builder, "Performance");
        toggle(entries, performance, "Performance Mode", "Cache nearby blocks instead of scanning every frame.",
                ESPConfig.performanceMode, v -> ESPConfig.performanceMode = v);
        slider(entries, performance, "Cache Refresh Rate", "Client ticks between cache updates.",
                ESPConfig.cacheRefreshRate, 1, 60, v -> ESPConfig.cacheRefreshRate = v);

        ConfigCategory labels = category(builder, "Labels");
        toggle(entries, labels, "Show Labels", "Show a name label above each block.",
                ESPConfig.showLabels, v -> ESPConfig.showLabels = v);
        toggle(entries, labels, "Storage Labels", "Label storage blocks.",
                ESPConfig.showStorageLabels, v -> ESPConfig.showStorageLabels = v);
        toggle(entries, labels, "Utility Labels", "Label utility blocks.",
                ESPConfig.showUtilityLabels, v -> ESPConfig.showUtilityLabels = v);
        toggle(entries, labels, "Show Distance", "Add the distance to each label.",
                ESPConfig.showDistance, v -> ESPConfig.showDistance = v);
        slider(entries, labels, "Label Scale", "Label size in percent.",
                ESPConfig.labelScale, 25, 200, v -> ESPConfig.labelScale = v);

        ConfigCategory storage = category(builder, "Storage");
        ConfigCategory utility = category(builder, "Utility");
        ConfigCategory colors = category(builder, "Colors");

        for (ESPTarget target : ESPTarget.values()) {
            toggle(entries, target.storage ? storage : utility, target.plural,
                    "Highlight " + target.plural.toLowerCase(Locale.ROOT) + ".",
                    target.enabled, v -> target.enabled = v);
            colorField(entries, colors, target);
        }

        return builder.build();
    }

    private static ConfigCategory category(ConfigBuilder builder, String name) {
        return builder.getOrCreateCategory(Component.literal(name));
    }

    private static void toggle(ConfigEntryBuilder entries, ConfigCategory category, String name,
                               String tooltip, boolean value, Consumer<Boolean> onSave) {
        category.addEntry(entries.startBooleanToggle(Component.literal(name), value)
                .setTooltip(Component.literal(tooltip))
                .setSaveConsumer(onSave)
                .build());
    }

    private static void slider(ConfigEntryBuilder entries, ConfigCategory category, String name,
                               String tooltip, int value, int min, int max, Consumer<Integer> onSave) {
        category.addEntry(entries.startIntSlider(Component.literal(name), value, min, max)
                .setTooltip(Component.literal(tooltip))
                .setSaveConsumer(onSave)
                .build());
    }

    /**
     * Colors are edited as hex text (#RRGGBB). A text field is used instead of Cloth Config's
     * color field, which doesn't work with addEntry() on Cloth Config 21.11.
     */
    private static void colorField(ConfigEntryBuilder entries, ConfigCategory category, ESPTarget target) {
        category.addEntry(entries.startStrField(
                        Component.literal(target.label + " Color"), ESPConfig.toHex(target.color))
                .setDefaultValue(ESPConfig.toHex(target.defaultColor))
                .setTooltip(Component.literal("Hex color, e.g. #FFAA00"))
                .setErrorSupplier(ESPConfigScreen::validateColor)
                .setSaveConsumer(text -> target.color = ESPConfig.parseColor(text))
                .build());
    }

    private static Optional<Component> validateColor(String text) {
        if (ESPConfig.parseColor(text) >= 0) {
            return Optional.empty();
        }
        return Optional.of(Component.literal("Enter a hex color like #FFAA00"));
    }
}
