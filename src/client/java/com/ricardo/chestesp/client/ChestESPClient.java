package com.ricardo.chestesp.client;

import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/** Client-only entrypoint: registers the toggle key and hooks the tick and render events. */
public class ChestESPClient implements ClientModInitializer {

    public static final String MOD_ID = "chest-esp";

    private static final KeyMapping TOGGLE_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.chest-esp.toggle",
                    InputConstants.Type.KEYSYM,
                    InputConstants.KEY_Y,
                    KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "general"))
            )
    );

    @Override
    public void onInitializeClient() {
        ESPConfig.load();
        ClientTickEvents.END_CLIENT_TICK.register(ChestESPClient::onClientTick);
        WorldRenderEvents.AFTER_ENTITIES.register(context -> ESPRenderer.render(Minecraft.getInstance()));
    }

    private static void onClientTick(Minecraft minecraft) {
        while (TOGGLE_KEY.consumeClick()) {
            ESPConfig.enabled = !ESPConfig.enabled;
            ESPConfig.save();

            if (ESPConfig.enabled) {
                ESPCache.invalidate();
            } else {
                ESPCache.clear();
            }

            if (minecraft.player != null) {
                minecraft.player.displayClientMessage(
                        Component.literal("Chest ESP: " + (ESPConfig.enabled ? "ON" : "OFF")), true);
            }
        }

        ESPCache.tick(minecraft);
    }
}
