# AntiFreecam evolved 1.2.2+mc26.3

- Add optional Camera Tweaks 1.11.2 integration for Minecraft 26.3.
- Enforce block collisions during freecam movement, activation, and loading saved cameras.
- Correct both interpolation endpoints when a saved camera is blocked.
- Apply the configured render-distance limit to Camera Tweaks.
- Add an opt-in client game test and a startup check without Camera Tweaks.

## Previous 1.2.1 changes

- Target the final Minecraft Java Edition 26.3 release.
- Update Fabric Loader to 0.19.5 and Fabric API to 0.160.5+26.3.
- Update Fabric Loom to 1.17.21 and the Gradle wrapper to 9.5.0, with distribution checksum verification.
- Keep the collision integration compatible with Freecam 1.5.0-alpha.0 for Minecraft 26.3.
- Configure release publishing for Minecraft 26.3 on Modrinth and CurseForge.
- Retain singleplayer collision enforcement, the dedicated-server handshake, and the optional render-distance limit introduced in 1.2.0.

## Previous 1.2.0 snapshot changes

- Add an optional server-configured limit that keeps Freecam and WI Freecam cameras inside the player's effective render distance.
- Add automatic generation of `config/antifreecam-evolved.json` with the render-distance limit disabled by default.
- Add a backward-compatible configuration payload and require updated clients only when the new limit is enabled.
- Add support for Minecraft 26.3-snapshot-7.
- Accept the `26.3-alpha.7` runtime version reported by Fabric Loader for this snapshot.
- Add collision enforcement for WI Freecam 1.1.
- Enable collision enforcement automatically in singleplayer worlds.
- Keep compatibility with current and legacy Freecam configuration implementations.
- Keep the dedicated-server handshake.
