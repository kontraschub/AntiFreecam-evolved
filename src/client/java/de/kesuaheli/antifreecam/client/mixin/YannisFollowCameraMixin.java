package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import de.kesuaheli.antifreecam.client.integration.YannisCameraPosition;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class YannisFollowCameraMixin {
    @Shadow
    protected abstract void setPosition(Vec3 position);

    @Shadow
    public abstract Vec3 position();

    // Yannis' own follow-camera injection runs at the default order (1000).
    @Inject(method = "update", at = @At("TAIL"), order = 1100)
    private void antiFreecam$constrainFollowCamera(DeltaTracker deltaTracker, CallbackInfo ci) {
        if ((!FreecamIntegration.forceCollision && !FreecamIntegration.limitToRenderDistance)
                || !YannisFreecamAccess.isFollowPlayerEnabled()) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            Vec3 start = client.player.getEyePosition(deltaTracker.getGameTimeDeltaPartialTick(true));
            setPosition(YannisCameraPosition.constrain(start, position(), client));
        }
    }
}
