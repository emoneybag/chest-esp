package com.ricardo.chestesp.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.ricardo.chestesp.ChestESP;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ChestESPClient
		implements ClientModInitializer {

	/*
	 * ============================================================
	 * KEYBIND
	 * ============================================================
	 */

	private static final KeyMapping.Category CATEGORY =
			KeyMapping.Category.register(
					Identifier.fromNamespaceAndPath(
							ChestESP.MOD_ID,
							"general"
					)
			);

	private static final KeyMapping TOGGLE_ESP_KEY =
			KeyBindingHelper.registerKeyBinding(
					new KeyMapping(
							"key.chest-esp.toggle",
							InputConstants.Type.KEYSYM,
							InputConstants.KEY_Y,
							CATEGORY
					)
			);

	/*
	 * ============================================================
	 * INITIALIZATION
	 * ============================================================
	 */

	@Override
	public void onInitializeClient() {

		ESPConfig.load();

		/*
		 * --------------------------------------------------------
		 * CLIENT TICK
		 * --------------------------------------------------------
		 */

		ClientTickEvents.END_CLIENT_TICK.register(
				ChestESPClient::onClientTick
		);

		/*
		 * --------------------------------------------------------
		 * WORLD RENDER
		 * --------------------------------------------------------
		 */

		WorldRenderEvents.AFTER_ENTITIES.register(
				context -> {

					Minecraft minecraft =
							Minecraft.getInstance();

					if (!ESPConfig.enabled) {
						return;
					}

					if (
							minecraft.level == null
									|| minecraft.player == null
					) {
						return;
					}

					ESPRenderer.render(
							minecraft
					);
				}
		);

		ChestESP.LOGGER.info(
				"Chest ESP initialized!"
		);
	}

	/*
	 * ============================================================
	 * CLIENT TICK
	 * ============================================================
	 */

	private static void onClientTick(
			Minecraft minecraft
	) {

		/*
		 * Toggle ESP with Y.
		 */

		while (
				TOGGLE_ESP_KEY.consumeClick()
		) {

			ESPConfig.enabled =
					!ESPConfig.enabled;

			ESPConfig.save();

			if (!ESPConfig.enabled) {
				ESPCache.clear();
			} else {
				ESPCache.invalidate();
			}

			if (minecraft.player != null) {

				minecraft.player
						.displayClientMessage(
								Component.literal(
										"Chest ESP: "
												+ (
												ESPConfig.enabled
														? "ON"
														: "OFF"
										)
								),
								true
						);
			}
		}

		/*
		 * The cache handles:
		 *
		 * - player movement
		 * - render distance changes
		 * - refresh rate
		 * - world changes
		 * - invalidation
		 */
		ESPCache.tick(
				minecraft
		);
	}
}