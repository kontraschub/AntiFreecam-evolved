package de.kesuaheli.antifreecam.client;

import de.kesuaheli.antifreecam.AntiFreecam;
import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import de.kesuaheli.antifreecam.packet.FreecamConfigClientboundPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;

public class AntiFreecamClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FreecamIntegration.enable();

        ClientConfigurationNetworking.registerGlobalReceiver(FreecamConfigClientboundPayload.TYPE, (payload, context) -> {
            FreecamIntegration.forceCollision = payload.forceCollision();
            AntiFreecam.LOGGER.info("Server config: force collisions {}", payload.forceCollision());
        });
    }
}

