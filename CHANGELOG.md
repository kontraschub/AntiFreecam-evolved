# AntiFreecam evolved 1.2.0

- Add an optional server-configured limit that keeps Freecam and WI Freecam cameras inside the player's effective render distance.
- Add automatic generation of `config/antifreecam-evolved.json` with the render-distance limit disabled by default.
- Add a backward-compatible configuration payload and require updated clients only when the new limit is enabled.
- Add support for Minecraft 26.3-snapshot-7.
- Accept the `26.3-alpha.7` runtime version reported by Fabric Loader for this snapshot.
- Add collision enforcement for WI Freecam 1.1.
- Enable collision enforcement automatically in singleplayer worlds.
- Keep compatibility with current and legacy Freecam configuration implementations.
- Keep the dedicated-server handshake.
