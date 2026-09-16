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
| Freecam | https://github.com/MinecraftFreecam/Freecam | analyzed | Target integration for Freecam 1.5.0-alpha.0 |

## New Target Platform

| Topic | Value |
| --- | --- |
| Game / application | Minecraft Java Edition |
| Version | 26.3 (final release) |
| Modding framework | Fabric |
| Programming language | Java 25 |
| Build system | Gradle 9.5.0 with Fabric Loom 1.17.21 |
| Fabric Loader | 0.19.5 |
| Fabric API | 0.160.5+26.3 |

## Function Matrix

| Function | Old implementation | New implementation | Status | Deviation |
| --- | --- | --- | --- | --- |
| Mod ID | `antifreecam` | `antifreecam-evolved` | ported | Updated project identity |
| Shared initialization | Registration of `FreecamConfigS2CPacket` | Registration of `FreecamConfigClientboundPayload` | ported | Class names adapted to Mojang/Fabric 26.1 names |
| Required client mod | Disconnect if the client cannot send the payload | Disconnect if the client cannot send the payload | ported | Text changed to sentence case |
| Server setting | `forceCollision=true` in the configuration handshake | `forceCollision=true` in the configuration handshake | ported | none |
| Client reception | `ClientConfigurationNetworking.registerGlobalReceiver` | `ClientConfigurationNetworking.registerGlobalReceiver` | ported | Payload types adapted to 26.1 |
| Freecam detection | `FabricLoader.isModLoaded("freecam")` | `FabricLoader.isModLoaded("freecam")` | ported | none |
| Freecam collision enforcement | Mixin on `CollisionBehavior.isIgnored` | Mixin on `ModConfigImpl.ignoreCollisionWith` | ported | Freecam 1.5.0-alpha.0 retains the modern configuration target |
| WI Freecam collision enforcement | not available | Mixin applies Minecraft block collision handling to WI Freecam camera movement | ported | Enabled by the server setting or automatically in singleplayer |
| Data generator | Client data generator exists | not carried over | open | No functional contribution to the old behavior identified |

## Open Points

- Run an in-world multiplayer test with Minecraft 26.3 and Freecam 1.5.0-alpha.0, including the optional render-distance limit.
- Verify WI Freecam when a compatible build for Minecraft 26.3 is available.
- Decide whether the data generator from the old mod is still needed.

## Verification

| Check | Result |
| --- | --- |
| Gradle build | passed against Minecraft 26.3 |
| Artifact | `build/libs/antifreecam-evolved-1.2.1+mc26.3.jar` |
| Freecam API inspection | verified `ModConfigImpl.ignoreCollisionWith(Block)` and the `FreeCamera` entity in the published 1.5.0-alpha.0 JAR |
| Client startup | passed with Minecraft 26.3, Fabric Loader 0.19.5, Fabric API 0.160.5+26.3, and Freecam 1.5.0-alpha.0 |
| Collision runtime smoke test | passed: stone, glass, and bedrock respect Freecam settings when enforcement is off, return `false` from `ignoreCollisionWith` when enforcement is on, and restore Freecam behavior after reset |
| Dedicated-server initialization | passed: mod loaded and default configuration created; server stopped at the EULA check before world startup |
| In-world multiplayer / render-distance behavior | not tested for this release |
| WI Freecam runtime | not tested for this release |

The temporary verification mod is kept outside the release artifact. Local test sources and logs are in the ignored `build/verification/` directory. The collision smoke test checks the actual Mixin-transformed Freecam configuration during client startup; it does not simulate camera movement in a world.
