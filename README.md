# AntiFreecam evolved

`AntiFreecam evolved` ist eine Fabric-Mod fuer Minecraft-Server, die Freecam-Clients dazu zwingt, Blockkollisionen aktiv zu lassen, wenn der Server das verlangt.

Die Mod ist aus `AntiFreecam` von Kesuaheli abgeleitet und portiert die Funktionalitaet auf die aktuelle technische Basis.

## Funktionalitaet

Freecam-Mods erlauben es Spielern, die Kamera unabhaengig vom eigenen Spielerkoerper zu bewegen. Das ist fuer Screenshots, Bauen oder Administration praktisch, kann aber auf Servern problematisch sein, wenn Spieler damit in Bereiche schauen oder sich durch Raeume bewegen, die normalerweise durch Blockkollisionen begrenzt sind.

`AntiFreecam evolved` setzt genau an diesem Punkt an:

- Der Server registriert ein eigenes Konfigurationspaket.
- Beim Verbindungsaufbau verlangt der Server, dass der Client diese AntiFreecam-Mod installiert hat.
- Der Server sendet dem Client die Vorgabe `forceCollision=true`.
- Der Client speichert diese Servervorgabe fuer die laufende Verbindung.
- Wenn die Freecam-Mod installiert ist, greift ein Mixin in deren Konfiguration ein.
- Blockkollisionen bleiben dann in Freecam aktiv, auch wenn Freecam sie normalerweise deaktivieren wuerde.

Das Ziel ist nicht, Freecam komplett zu verbieten. Die Kamera kann weiter genutzt werden. Die Mod stellt nur sicher, dass Freecam auf Servern mit entsprechender Vorgabe keine deaktivierten Blockkollisionen nutzt.

## Herkunft

Dieses Projekt basiert auf der urspruenglichen Idee und Funktionalitaet von `AntiFreecam` von Kesuaheli. Die abgeleitete Version pflegt die Funktion fuer neuere Minecraft-, Fabric- und Freecam-Versionen weiter.

## Status

- Git-Repository initialisiert.
- Grundlegende Projektdateien angelegt.
- Funktionalitaet und Herkunft dokumentiert.
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
2. Funktionalitaet und Verhalten nachvollziehbar dokumentieren.
3. Zielplattform und technische Rahmenbedingungen der neuen Version klaeren.
4. Funktionen einzeln portieren.
5. Abweichungen und offene Punkte nachhalten.

## Portierte Funktionalitaet

- Serverseitiger Konfigurations-Handshake.
- Pflicht zur installierten Client-Mod.
- Uebertragung der Servervorgabe `forceCollision=true`.
- Clientseitige Speicherung der Servervorgabe.
- Integration mit Freecam ueber ein Client-Mixin.
- Erzwingen aktiver Blockkollisionen in Freecam.

## Build

```powershell
.\gradlew.bat build
```
