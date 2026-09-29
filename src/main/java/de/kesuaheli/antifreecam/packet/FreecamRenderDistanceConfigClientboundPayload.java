package de.kesuaheli.antifreecam.packet;

import de.kesuaheli.antifreecam.AntiFreecam;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record FreecamRenderDistanceConfigClientboundPayload(boolean enabled) implements CustomPacketPayload {
    public static final Type<FreecamRenderDistanceConfigClientboundPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(AntiFreecam.MOD_ID, "freecam_render_distance_config")
    );

    public static final StreamCodec<FriendlyByteBuf, FreecamRenderDistanceConfigClientboundPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    FreecamRenderDistanceConfigClientboundPayload::enabled,
                    FreecamRenderDistanceConfigClientboundPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
