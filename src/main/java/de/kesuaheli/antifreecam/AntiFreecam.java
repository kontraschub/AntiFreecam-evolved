package de.kesuaheli.antifreecam;

import de.kesuaheli.antifreecam.packet.FreecamConfigClientboundPayload;
import de.kesuaheli.antifreecam.packet.FreecamRenderDistanceConfigClientboundPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntiFreecam implements ModInitializer {
    public static final String MOD_ID = "antifreecam-evolved";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.clientboundConfiguration().register(
                FreecamConfigClientboundPayload.TYPE,
                FreecamConfigClientboundPayload.STREAM_CODEC
        );
        PayloadTypeRegistry.clientboundConfiguration().register(
                FreecamRenderDistanceConfigClientboundPayload.TYPE,
                FreecamRenderDistanceConfigClientboundPayload.STREAM_CODEC
        );
    }
}
