package de.kesuaheli.antifreecam.packet;

import de.kesuaheli.antifreecam.AntiFreecam;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record FreecamConfigClientboundPayload(boolean forceCollision) implements CustomPacketPayload {
    public static final Type<FreecamConfigClientboundPayload> TYPE = new Type<>(
            Identifier.fromNamespaceAndPath(AntiFreecam.MOD_ID, "freecam_config_packet")
    );

    public static final StreamCodec<FriendlyByteBuf, FreecamConfigClientboundPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            FreecamConfigClientboundPayload::forceCollision,
            FreecamConfigClientboundPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
