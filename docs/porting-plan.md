# Porting Plan

## Goal

The old mod functionality is carried over to `AntiFreecam evolved` as closely as possible.

## Approach

1. Collect the source code of the old mods.
2. Determine the target version and modding framework for the new version.
3. Create a functional inventory.
4. Compare dependencies, events, hooks, and configuration points.
5. Implement the port in small, traceable commits.
6. Verify behavior against the old version.

## Old Mod Inventory

| Mod | Source | Status | Notes |
| --- | --- | --- | --- |
| AntiFreecam | https://github.com/Kesuaheli/AntiFreecam | analyzed | Fabric mod for server and client |
| Freecam | https://github.com/MinecraftFreecam/Freecam | analyzed | Target integration for Freecam 1.3.0 on Minecraft 1.21.1 |

## New Target Platform

| Topic | Value |
| --- | --- |
| Game / application | Minecraft Java Edition |
| Version | 1.21.1 |
| Modding framework | Fabric |
| Programming language | Java 21 |
| Build system | Gradle with Fabric Loom |
| Fabric Loader | 0.19.3 or newer, including 0.19.5 |
| Fabric API | 0.116.17+1.21.1 |

## Function Matrix

| Function | Old implementation | New implementation | Status | Deviation |
| --- | --- | --- | --- | --- |
| Mod ID | `antifreecam` | `antifreecam-evolved` | ported | Updated project identity |
| Shared initialization | Registration of `FreecamConfigS2CPacket` | Registration of `FreecamConfigClientboundPayload` | ported | Configuration payload registration uses Fabric's `configurationS2C` registry |
| Required client mod | Disconnect if the client cannot send the payload | Disconnect if the client cannot send the payload | ported | Text changed to sentence case |
| Server setting | `forceCollision=true` in the configuration handshake | `forceCollision=true` in the configuration handshake | ported | none |
| Client reception | `ClientConfigurationNetworking.registerGlobalReceiver` | `ClientConfigurationNetworking.registerGlobalReceiver` | ported | Payload types adapted to Minecraft 1.21.1 |
| Freecam detection | `FabricLoader.isModLoaded("freecam")` | `FabricLoader.isModLoaded("freecam")` | ported | none |
| Freecam collision enforcement | Mixin on `CollisionBehavior.isIgnored` | Mixin on `CollisionBehavior.isIgnored` for Freecam 1.3.0 | ported | Legacy target restored for Minecraft 1.21.1 |
| WI Freecam collision enforcement | not available | Mixin applies Minecraft block collision handling to WI Freecam camera movement | ported | Enabled by the server setting or automatically in singleplayer |
| Data generator | Client data generator exists | not carried over | open | No functional contribution to the old behavior identified |

## Open Points

- Run an in-game collision test and a dedicated multiplayer test with Minecraft 1.21.1.
- Decide whether the data generator from the old mod is still needed.

## Verification

| Check | Result |
| --- | --- |
| Gradle build | passed against Minecraft 1.21.1 |
| Artifact | `build/libs/antifreecam-evolved-1.2.1+mc1.21.1.jar` |
| Runtime test | Client started with Freecam 1.3.0 and applied the collision mixin under Fabric Loader 0.19.3 and 0.19.5; dedicated server initialized and created its default config |
