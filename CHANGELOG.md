# AntiFreecam evolved 1.2.2+mc26.2

- Require Fabric Loader 0.19.5 for current YACL/MixinExtras compatibility.
- Add optional Camera Tweaks integration (1.11.2-26.2 API).
- Enforce collisions during camera movement, activation, and saved-camera loading.
- Prevent saved-camera interpolation from bypassing collision checks.
- Apply the existing optional render-distance limit to Camera Tweaks.

# AntiFreecam evolved 1.2.0

- Add an optional server-configured limit that keeps Freecam and WI Freecam cameras inside the player's effective render distance.
- Add automatic generation of `config/antifreecam-evolved.json` with the render-distance limit disabled by default.
- Add a backward-compatible configuration payload and require updated clients only when the new limit is enabled.
- Add support for Minecraft 26.2.
- Add collision enforcement for WI Freecam 1.1.
- Enable collision enforcement automatically in singleplayer worlds.
- Keep the existing Freecam integration and dedicated-server handshake.
