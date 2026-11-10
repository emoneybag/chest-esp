# Chest ESP

A client-side Fabric mod for Minecraft 1.21.11 that highlights chests and other storage and
utility blocks with boxes and labels, optionally through walls.

**Storage:** chests, trapped chests, ender chests, barrels, shulker boxes, copper chests
**Utility:** furnaces, blast furnaces, smokers, hoppers, droppers, dispensers, brewing stands, crafters

## Requirements

Fabric Loader, [Fabric API](https://modrinth.com/mod/fabric-api),
[Cloth Config](https://modrinth.com/mod/cloth-config) and [Mod Menu](https://modrinth.com/mod/modmenu).
The mod is client-only and does nothing on a server.

## Use

Press **Y** to toggle (rebind under Options > Controls). Everything else is in the settings
screen, opened from Mod Menu.

## Colors

Colors are hex codes: `#FFAA00`, `FFAA00`, `0xFFAA00` and short `#FA0` all work. Set them in the
settings screen (Colors tab) or in `config/chest-esp.properties`:

```properties
chest.color=#FFAA00
trapped_chest.color=#FF3333
```

## Build

```
./gradlew build
```

The jar is written to `build/libs/`.

## Note

Many multiplayer servers ban ESP mods. Check the rules before using it online.
