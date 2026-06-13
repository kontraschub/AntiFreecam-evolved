package de.kesuaheli.antifreecam.client;

import de.kesuaheli.antifreecam.AntiFreecam;
import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import de.kesuaheli.antifreecam.packet.FreecamConfigClientboundPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class AntiFreecamClient implements ClientModInitializer {
    private static boolean singleplayerCollisionLogged;

    @Override
    public void onInitializeClient() {
        FreecamIntegration.enable();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!client.hasSingleplayerServer()) {
                singleplayerCollisionLogged = false;
                return;
            }

            FreecamIntegration.forceCollision = true;
            if (!singleplayerCollisionLogged) {
                singleplayerCollisionLogged = true;
                AntiFreecam.LOGGER.info("Singleplayer detected: force collisions true.");
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            FreecamIntegration.forceCollision = false;
            singleplayerCollisionLogged = false;
        });

        ClientConfigurationNetworking.registerGlobalReceiver(FreecamConfigClientboundPayload.TYPE, (payload, context) -> {
            FreecamIntegration.forceCollision = payload.forceCollision();
            AntiFreecam.LOGGER.info(
                    "Server config received: force collisions {}. Freecam installed: {}. WI Freecam installed: {}.",
                    payload.forceCollision(),
                    FreecamIntegration.isFreecamPresent(),
                    FreecamIntegration.isWiFreecamPresent()
            );
        });
    }
}
