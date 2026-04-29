# Portierungsplan

## Ziel

Die alte Mod-Funktionalitaet wird soweit moeglich eins zu eins in `AntiFreecam evolved` uebernommen.

## Vorgehen

1. Quellcode der alten Mods aufnehmen.
2. Zielversion und Modding-Framework der neuen Version bestimmen.
3. Funktionale Bestandsaufnahme erstellen.
4. Abhaengigkeiten, Events, Hooks und Konfigurationen vergleichen.
5. Portierung in kleinen, nachvollziehbaren Commits umsetzen.
6. Verhalten gegen die alte Version pruefen.

## Inventar alte Mods

| Mod | Quelle | Status | Notizen |
| --- | --- | --- | --- |
| AntiFreecam | https://github.com/Kesuaheli/AntiFreecam | analysiert | Fabric-Mod fuer Server und Client |
| Freecam | https://github.com/MinecraftFreecam/Freecam | analysiert | Zielintegration fuer Freecam 1.4.0-alpha.3 |

## Zielplattform neue Version

| Thema | Wert |
| --- | --- |
| Spiel / Anwendung | Minecraft Java Edition |
| Version | 26.1.2 |
| Modding-Framework | Fabric |
| Programmiersprache | Java 25 |
| Build-System | Gradle mit Fabric Loom |
| Fabric Loader | 0.19.2 |
| Fabric API | 0.147.0+26.1.2 |

## Funktionsmatrix

| Funktion | Alte Umsetzung | Neue Umsetzung | Status | Abweichung |
| --- | --- | --- | --- | --- |
| Mod-ID | `antifreecam` | `antifreecam` | portiert | keine |
| Gemeinsame Initialisierung | Registrierung von `FreecamConfigS2CPacket` | Registrierung von `FreecamConfigClientboundPayload` | portiert | Klassennamen auf Mojang/Fabric-26.1-Namen angepasst |
| Serverpflicht fuer Client-Mod | Disconnect, wenn Client Payload nicht senden kann | Disconnect, wenn Client Payload nicht senden kann | portiert | Text auf Satzschreibung angepasst |
| Servervorgabe | `forceCollision=true` im Configuration-Handshake | `forceCollision=true` im Configuration-Handshake | portiert | keine |
| Clientempfang | `ClientConfigurationNetworking.registerGlobalReceiver` | `ClientConfigurationNetworking.registerGlobalReceiver` | portiert | Payload-Typen auf 26.1 angepasst |
| Freecam-Erkennung | `FabricLoader.isModLoaded("freecam")` | `FabricLoader.isModLoaded("freecam")` | portiert | keine |
| Freecam-Kollisionszwang | Mixin auf `CollisionBehavior.isIgnored` | Mixin auf `ModConfigDTO.ignoreCollisionWith` | portiert | Freecam 1.4.0-alpha.3 hat die alte Zielklasse ersetzt |
| Datengenerator | Client-Datengenerator vorhanden | nicht uebernommen | offen | kein funktionaler Beitrag im alten Verhalten erkennbar |

## Offene Punkte

- Laufzeittest mit Minecraft 26.1.2, Fabric Loader und Freecam 1.4.0-alpha.3 durchfuehren.
- Entscheiden, ob Datengenerator aus der alten Mod weiterhin gebraucht wird.

## Verifikation

| Pruefung | Ergebnis |
| --- | --- |
| Gradle Build | bestanden mit `.\gradlew.bat build` |
| Artefakt | `build/libs/antifreecam-evolved-1.0.0.jar` |
| Laufzeittest | offen |
