# AntiFreecam evolved

Arbeitsrepository fuer das Projekt `AntiFreecam evolved`.

## Zielsetzung

Die bestehende Funktionalitaet der alten Mods soll soweit moeglich eins zu eins in die neue Version portiert werden.

Abweichungen sollen nur vorgenommen werden, wenn die neue technische Basis es erzwingt oder wenn eine alte Funktion nicht mehr sinnvoll oder stabil umsetzbar ist. Solche Abweichungen werden dokumentiert.

## Status

- Git-Repository initialisiert.
- Grundlegende Projektdateien angelegt.
- Zielsetzung fuer die Portierung dokumentiert.
- Fabric-Projektgeruest fuer Minecraft Java Edition 26.1.2 angelegt.
- Kernfunktionalitaet der alten Mod portiert.
- Gradle-Build erfolgreich.
- Naechster Schritt: Verhalten mit Freecam 1.4.0-alpha.3 im Client und Server testen.

## Zielplattform

- Minecraft Java Edition: 26.1.2
- Modloader: Fabric
- Fabric Loader: 0.19.2
- Fabric API: 0.147.0+26.1.2
- Java: 25 oder neuer

## Arbeitsweise

1. Alte Mods inventarisieren.
2. Funktionalitaet und Verhalten dokumentieren.
3. Zielplattform und technische Rahmenbedingungen der neuen Version klaeren.
4. Funktionen einzeln portieren.
5. Abweichungen und offene Punkte nachhalten.

## Portierte Funktionalitaet

- Server registriert ein Konfigurationspaket.
- Server verlangt AntiFreecam auf dem Client.
- Server sendet `forceCollision=true` beim Konfigurations-Handshake.
- Client speichert die Servervorgabe.
- Client-Mixin erzwingt bei installierter Freecam-Mod Blockkollisionen.

## Build

```powershell
.\gradlew.bat build
```
