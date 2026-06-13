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
| Freecam | https://github.com/MinecraftFreecam/Freecam | analyzed | Target integration for Freecam 1.4.0-alpha.3 |

## New Target Platform

| Topic | Value |
| --- | --- |
| Game / application | Minecraft Java Edition |
| Version | 26.2 RC1 and RC2 |
| Modding framework | Fabric |
| Programming language | Java 25 |
| Build system | Gradle with Fabric Loom |
| Fabric Loader | 0.19.3 |
| Fabric API | 0.152.0+26.2 |

## Function Matrix

| Function | Old implementation | New implementation | Status | Deviation |
| --- | --- | --- | --- | --- |
| Mod ID | `antifreecam` | `antifreecam-evolved` | ported | Updated project identity |
| Shared initialization | Registration of `FreecamConfigS2CPacket` | Registration of `FreecamConfigClientboundPayload` | ported | Class names adapted to Mojang/Fabric 26.1 names |
| Required client mod | Disconnect if the client cannot send the payload | Disconnect if the client cannot send the payload | ported | Text changed to sentence case |
| Server setting | `forceCollision=true` in the configuration handshake | `forceCollision=true` in the configuration handshake | ported | none |
| Client reception | `ClientConfigurationNetworking.registerGlobalReceiver` | `ClientConfigurationNetworking.registerGlobalReceiver` | ported | Payload types adapted to 26.1 |
| Freecam detection | `FabricLoader.isModLoaded("freecam")` | `FabricLoader.isModLoaded("freecam")` | ported | none |
| Freecam collision enforcement | Mixin on `CollisionBehavior.isIgnored` | Mixin on `ModConfigDTO.ignoreCollisionWith` | ported | Freecam 1.4.0-alpha.3 replaced the old target class |
| WI Freecam collision enforcement | not available | Mixin applies Minecraft block collision handling to WI Freecam camera movement | ported | Enabled by the server setting or automatically in singleplayer |
| Data generator | Client data generator exists | not carried over | open | No functional contribution to the old behavior identified |

## Open Points

- Run a dedicated multiplayer runtime test with Minecraft 26.2 RC1 or RC2.
- Decide whether the data generator from the old mod is still needed.

## Verification

| Check | Result |
| --- | --- |
| Gradle build | passed against Minecraft 26.2 RC1 and RC2 |
| Artifact | `build/libs/antifreecam-evolved-1.1.0-rc.7+mc26.2-rc.jar` |
| Runtime test | WI Freecam collision enforcement passed in singleplayer |
