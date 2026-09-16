# AntiFreecam evolved

`AntiFreecam evolved` is a Fabric mod for Minecraft servers that forces Freecam clients to keep block collisions enabled when the server requires it.

The mod is derived from Kesuaheli's `AntiFreecam` and ports the functionality to the current technical baseline.

## Functionality

Freecam mods allow players to move the camera independently from the player body. This is useful for screenshots, building, and administration, but it can be problematic on servers when players use it to look into or move through areas that are normally limited by block collisions.

`AntiFreecam evolved` addresses that specific behavior:

- The server registers a custom configuration payload.
- During connection setup, the server requires the client to have this AntiFreecam mod installed.
- The server sends the client the setting `forceCollision=true`.
- The client stores this server setting for the current connection.
- If the Freecam mod is installed, a mixin hooks into its configuration.
- Block collisions then remain active in Freecam, even when Freecam would normally disable them.

The goal is not to ban Freecam completely. The camera can still be used. The mod only ensures that Freecam does not use disabled block collisions on servers that require collision enforcement.

## Origin

This project is based on the original idea and functionality of Kesuaheli's `AntiFreecam`. This derived version keeps that behavior working on newer Minecraft, Fabric, and Freecam versions.

## Status

- Release build: `1.2.1+mc26.3`.
- Updated for the final Minecraft Java Edition 26.3 release.
- Freecam 1.5.0-alpha.0 uses the supported `ModConfigImpl.ignoreCollisionWith` integration.
- Gradle build and client startup pass. A runtime smoke test verifies collision enforcement for stone, glass, and bedrock, including restoration after enforcement is reset.
- Existing server handshake, singleplayer collision enforcement, and optional render-distance limit retained.
- Dedicated-server initialization and default configuration creation pass. An in-world multiplayer test is still outstanding.
- WI Freecam integration retained; runtime compatibility with a WI Freecam build for 26.3 has not been verified.

## Target Platform

- Minecraft Java Edition: 26.3
- Mod loader: Fabric
- Fabric Loader: 0.19.5 or newer
- Fabric API: 0.160.5+26.3
- Freecam: 1.5.0-alpha.0 for Minecraft 26.3 (optional)
- Java: 25 or newer
- Build tools: Gradle 9.5.0 and Fabric Loom 1.17.21

## Workflow

1. Inventory the old mods.
2. Document functionality and behavior in a traceable way.
3. Clarify the target platform and technical constraints of the new version.
4. Port features one by one.
5. Track deviations and open points.

## Ported Functionality

- Server-side configuration handshake.
- Required client-side mod installation.
- Transfer of the server setting `forceCollision=true`.
- Client-side storage of the server setting.
- Integration with Freecam through a client mixin.
- Enforcement of active block collisions in Freecam.
- Enforcement of camera collisions in WI Freecam.
- Optional enforcement that keeps Freecam and WI Freecam cameras inside the player's effective render distance.
- Automatic collision enforcement in singleplayer worlds.

## Configuration

The dedicated server creates `config/antifreecam-evolved.json` on its first start:

```json
{
  "limitFreecamToRenderDistance": false
}
```

Set `limitFreecamToRenderDistance` to `true` and restart the server to keep supported Freecam cameras inside the chunk area around the player that is covered by the effective render distance. The option is disabled by default.

## Build

```powershell
.\gradlew.bat build
```

The installable artifact is `build/libs/antifreecam-evolved-1.2.1+mc26.3.jar`. Install it on the server and all connecting clients, together with Fabric API for Minecraft 26.3. Install Freecam separately on clients that need it. Do not install the `-sources.jar` file.

This build targets the final 26.3 release. It does not accept the earlier 26.3 snapshots or release candidates.

## Publish to CurseForge

Set the local credentials before publishing:

```env
CURSEFORGE_TOKEN=your-token
CURSEFORGE_ANTIFREECAM_ID=your-project-id
```

The project also accepts `CURSEFORGE_PROJECT_ID` or `curseforge_project_id` in `gradle.properties`. Keep tokens in the environment, `.env.local`, or your user Gradle properties.

```powershell
.\gradlew.bat publishCurseforge
```
