package de.kesuaheli.antifreecam.server;

import de.kesuaheli.antifreecam.AntiFreecam;
import de.kesuaheli.antifreecam.packet.FreecamConfigClientboundPayload;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.chat.Component;

public class AntiFreecamServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        AntiFreecam.LOGGER.info("AntiFreecam loaded.");

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            if (!ServerConfigurationNetworking.canSend(handler, FreecamConfigClientboundPayload.TYPE)) {
                handler.disconnect(Component.literal("This server requires the AntiFreecam mod to be installed."));
                return;
            }

            ServerConfigurationNetworking.send(handler, new FreecamConfigClientboundPayload(true));
            AntiFreecam.LOGGER.info("A player with AntiFreecam is connecting to the server.");
        });
    }
}

