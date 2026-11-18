package de.kesuaheli.antifreecam.client.mixin;

import com.mojang.authlib.GameProfile;
import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import de.kesuaheli.antifreecam.client.integration.RenderDistanceLimiter;
import de.kesuaheli.antifreecam.client.integration.YannisCameraPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.xolt.xcam.util.XCamera", remap = false)
public abstract class YannisFreecamMixin extends AbstractClientPlayer {
    protected YannisFreecamMixin(ClientLevel level, GameProfile profile) {
        super(level, profile);
    }

    @Override
    public void snapTo(double x, double y, double z, float yaw, float pitch) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && (FreecamIntegration.forceCollision || FreecamIntegration.limitToRenderDistance)) {
            Vec3 start = client.getCameraEntity() == this && !YannisFreecamAccess.isFollowPlayerEnabled()
                    ? getEyePosition()
                    : client.player.getEyePosition();
            Vec3 target = YannisCameraPosition.constrain(start, new Vec3(x, y + getEyeHeight(), z), client);
            super.snapTo(target.x, target.y - getEyeHeight(), target.z, yaw, pitch);
            return;
        }
        super.snapTo(x, y, z, yaw, pitch);
    }

    @Inject(method = "tick", at = @At("RETURN"), remap = false)
    private void antiFreecam$limitMovement(CallbackInfo ci) {
        Vec3 requested = position();
        Vec3 limited = RenderDistanceLimiter.limit(requested);
        Minecraft client = Minecraft.getInstance();
        if (!limited.equals(requested) && client.player != null) {
            Vec3 allowed = YannisCameraPosition.constrain(client.player.getEyePosition(),
                    limited.add(0, getEyeHeight(), 0), client);
            setPos(allowed.subtract(0, getEyeHeight(), 0));
            setOldPosAndRot();
        }
    }
}
