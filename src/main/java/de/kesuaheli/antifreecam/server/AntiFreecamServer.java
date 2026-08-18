package de.kesuaheli.antifreecam.server;

import de.kesuaheli.antifreecam.AntiFreecam;
import de.kesuaheli.antifreecam.config.AntiFreecamConfig;
import de.kesuaheli.antifreecam.packet.FreecamConfigClientboundPayload;
import de.kesuaheli.antifreecam.packet.FreecamRenderDistanceConfigClientboundPayload;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.chat.Component;

public class AntiFreecamServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        AntiFreecam.LOGGER.info("Antifreecam-evolved loaded.");
        AntiFreecamConfig config = AntiFreecamConfig.load();
        AntiFreecam.LOGGER.info(
                "Server config: limit Freecam to render distance {}.",
                config.limitFreecamToRenderDistance()
        );

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            if (!ServerConfigurationNetworking.canSend(handler, FreecamConfigClientboundPayload.TYPE)) {
                handler.disconnect(Component.literal("This server requires the Antifreecam-evolved mod to be installed."));
                return;
            }

            if (config.limitFreecamToRenderDistance()
                    && !ServerConfigurationNetworking.canSend(
                    handler,
                    FreecamRenderDistanceConfigClientboundPayload.TYPE
            )) {
                handler.disconnect(Component.literal(
                        "This server requires a newer Antifreecam-evolved version to enforce the Freecam render-distance limit."
                ));
                return;
            }

            ServerConfigurationNetworking.send(handler, new FreecamConfigClientboundPayload(true));
            if (ServerConfigurationNetworking.canSend(handler, FreecamRenderDistanceConfigClientboundPayload.TYPE)) {
                ServerConfigurationNetworking.send(
                        handler,
                        new FreecamRenderDistanceConfigClientboundPayload(config.limitFreecamToRenderDistance())
                );
            }
            AntiFreecam.LOGGER.info("A player with Antifreecam-evolved is connecting to the server.");
        });
    }
}
