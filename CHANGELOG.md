# AntiFreecam evolved 1.2.2+mc1.21.1

- Add optional Camera Tweaks integration (1.7.1-1.21.1 API).
- Enforce collisions during camera movement, activation, and saved-camera loading.
- Prevent saved-camera interpolation from bypassing collision checks.
- Apply the existing optional render-distance limit to Camera Tweaks.

# AntiFreecam evolved 1.2.1 for Minecraft 1.21.1

- Add a Minecraft 1.21.1 Fabric release with Freecam 1.3.0 collision support.
- Support Fabric Loader 0.19.3 and 0.19.5 with one mod JAR.

# AntiFreecam evolved 1.2.0 for Minecraft 1.21.1

- Add an optional server-configured limit that keeps Freecam and WI Freecam cameras inside the player's effective render distance.
- Add automatic generation of `config/antifreecam-evolved.json` with the render-distance limit disabled by default.
- Add a backward-compatible configuration payload and require updated clients only when the new limit is enabled.
- Port to Minecraft 1.21.1 with Fabric Loader 0.19.5, Fabric API 0.116.17, and Java 21.
- Add a collision mixin for Freecam 1.3.0's `CollisionBehavior` class.
- Add collision enforcement for WI Freecam 1.1.
- Enable collision enforcement automatically in singleplayer worlds.
- Keep the existing Freecam integration and dedicated-server handshake.
