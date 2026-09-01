# Chest ESP

A lightweight Minecraft Fabric mod that highlights storage and utility blocks with customizable ESP boxes and labels.

## Features

* **Chest ESP**

    * Chests
    * Trapped Chests
    * Ender Chests
    * Barrels
    * Shulker Boxes
    * Copper Chests

* **Utility ESP**

    * Furnaces
    * Blast Furnaces
    * Smokers
    * Hoppers
    * Droppers
    * Dispensers
    * Brewing Stands
    * Crafters

* **Customizable ESP**

    * Enable/disable ESP
    * Through-walls rendering
    * Filled or outline-only boxes
    * Adjustable render distance
    * Custom colors for each block type

* **Labels**

    * Block names
    * Optional distance display
    * Adjustable label size
    * Separate storage and utility label controls

* **Performance Mode**

    * Caches detected blocks
    * Avoids unnecessary chunk scanning
    * Updates the cache when the player moves significantly
    * Configurable cache refresh rate
    * Uses only already-loaded chunks

* **Mod Menu integration**

    * Full configuration screen through Mod Menu
    * Settings are saved automatically

## Controls

### Toggle ESP

**Default key:** `Y`

Press `Y` to toggle Chest ESP on or off.

The key can be changed through:

**Options → Controls → Key Binds**

## Configuration

Open the configuration screen through:

**Mods → Chest ESP → Configure**

### General

| Setting         | Description                           |
| --------------- | ------------------------------------- |
| Enable ESP      | Enables or disables ESP               |
| Through Walls   | Makes ESP visible through blocks      |
| Filled Boxes    | Adds transparent filling to ESP boxes |
| Render Distance | Maximum detection distance            |

### Performance

| Setting            | Description                                    |
| ------------------ | ---------------------------------------------- |
| Performance Mode   | Enables cached block detection                 |
| Cache Refresh Rate | Controls how frequently the cache is refreshed |

### Labels

| Setting        | Description                        |
| -------------- | ---------------------------------- |
| Show Labels    | Displays block names               |
| Storage Labels | Controls labels for storage blocks |
| Utility Labels | Controls labels for utility blocks |
| Show Distance  | Displays distance from the player  |
| Label Scale    | Changes label size                 |

### Storage

Individual toggles are available for:

* Chests
* Trapped Chests
* Ender Chests
* Barrels
* Shulker Boxes
* Copper Chests

### Utility

Individual toggles are available for:

* Furnaces
* Blast Furnaces
* Smokers
* Hoppers
* Droppers
* Dispensers
* Brewing Stands
* Crafters

### Colors

Each supported block type has its own configurable RGB color.

Colors are stored as RGB integer values without an alpha channel.

For example:

`0xFFAA00` = orange

## Performance

Chest ESP is designed to minimize unnecessary work during rendering.

The mod separates detection, caching, and rendering:

```text
World
  ↓
ESPDetector
  ↓
ESPCache
  ↓
ESPRenderer
```

### Performance Mode

When Performance Mode is enabled, the mod:

1. Scans loaded chunks for supported blocks.
2. Stores detected blocks in a cache.
3. Avoids scanning chunks every frame.
4. Rebuilds the cache when necessary.
5. Only renders the cached targets.

The cache can be refreshed when:

* The player moves sufficiently far.
* The render distance changes.
* The configured refresh interval is reached.
* The player enters a different world.
* The cache is manually invalidated.

The renderer itself does not scan chunks or search the world.

## Supported Minecraft Version

* **Minecraft:** 1.21.11
* **Fabric Loader:** 0.19.3+
* **Java:** 21+
* **Fabric API:** 0.141.6+1.21.11
* **Cloth Config:** 21.11.153
* **Mod Menu:** 17.0.0

## Installation

1. Install **Minecraft 1.21.11**.
2. Install **Java 21 or newer**.
3. Install **Fabric Loader**.
4. Install **Fabric API**.
5. Install **Cloth Config**.
6. Install **Mod Menu**.
7. Place the Chest ESP `.jar` file into your Minecraft `mods` folder.
8. Launch Minecraft using the Fabric profile.

## Building From Source

Clone or download the project and open a terminal in the project directory.

On Windows:

```bash
gradlew build
```

On macOS/Linux:

```bash
./gradlew build
```

After a successful build, the distributable JAR will be located in:

```text
build/libs/
```

The `-sources.jar` file is the source archive. The regular remapped `.jar` is the file intended for installation.

## Project Structure

```text
src/
├── main/
│   └── java/
│       └── com/ricardo/chestesp/
│           └── ChestESP.java
│
└── client/
    └── java/
        └── com/ricardo/chestesp/client/
            ├── ChestESPClient.java
            ├── ChestESPModMenu.java
            ├── ESPConfig.java
            ├── ESPConfigScreen.java
            ├── ESPDetector.java
            ├── ESPCache.java
            └── ESPRenderer.java

src/
└── main/
    └── resources/
        ├── fabric.mod.json
        └── assets/
            └── chest-esp/
                └── icon.png
```

## Version

Current version:

**1.0.0**

## License

This project is currently licensed under:

**CC0-1.0**

See the `LICENSE` file for the complete license text.

## Disclaimer

Chest ESP is a client-side Minecraft mod intended for use in environments where client-side ESP modifications are permitted.

Servers may have their own rules regarding ESP and other client modifications. Always follow the rules of the server you are playing on.

## Credits

Created by and for **emoneybag**.

Built using:

* Fabric
* Fabric API
* Cloth Config
* Mod Menu
* Minecraft 1.21.11
