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

- Git repository initialized.
- Basic project files created.
- Functionality and origin documented.
- Fabric project scaffold updated for Minecraft Java Edition 26.2 RC1 and RC2.
- Core functionality from the old mod ported.
- WI Freecam collision enforcement added.
- Gradle build passes.
- Freecam and WI Freecam integrations tested on the client.

## Target Platform

- Minecraft Java Edition: 26.2 RC1 and RC2
- Mod loader: Fabric
- Fabric Loader: 0.19.3
- Fabric API: 0.152.0+26.2
- Java: 25 or newer

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
- Automatic collision enforcement in singleplayer worlds.

## Build

```powershell
.\gradlew.bat build
```

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
