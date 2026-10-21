# AntiFreecam evolved 1.2.2+mc1.21.1

Camera Tweaks collision integration added for movement, activation, and saved cameras. Apply the existing optional render-distance limit to Camera Tweaks.

Install `build/libs/antifreecam-evolved-1.2.2+mc1.21.1.jar` on the client and server. Camera Tweaks remains optional and is not bundled.

## Overview

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

- Fabric port created for Minecraft Java Edition 1.21.1.
- Gradle build passes with Java 21.
- Client startup with Freecam 1.3.0 was verified and the collision mixin applied.
- Client startup was verified with Fabric Loader 0.19.3 and 0.19.5.
- Dedicated server initialization and default configuration creation were verified.
- In-game camera movement and multiplayer behavior still require manual verification.

## Target Platform

- Minecraft Java Edition: 1.21.1
- Mod loader: Fabric
- Fabric Loader: 0.19.3 or newer, including 0.19.5
- Fabric API: 0.116.17+1.21.1
- Java: 21 or newer
- Freecam integration: Freecam 1.3.0 for Minecraft 1.21.1

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

The distributable mod is `build/libs/antifreecam-evolved-1.2.2+mc1.21.1.jar`.

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

## Camera Tweaks verification

Build, automated in-world collision tests, and client startup without Camera Tweaks verified.

The test creates an isolated world and checks collisions, activation, saved cameras, and enforcement reset.

```powershell
.\gradlew.bat -I verify-camera-tweaks.init.gradle runClient
.\gradlew.bat -I verify-camera-tweaks.init.gradle -PwithoutCameraTweaks runClient
```

Test dependencies and verification classes are not bundled in the release jar.
