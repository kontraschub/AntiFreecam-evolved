package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.xolt.xcam.config.model.ModConfigDTO", remap = false)
public abstract class YannisFreecamConfigMixin {
    @Inject(method = {"ignoreCollisionWith", "ignoreAllCollision"}, at = @At("HEAD"), cancellable = true, require = 2)
    private void antiFreecam$forceCollision(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamIntegration.forceCollision) {
            cir.setReturnValue(false);
        }
    }

    // Some releases can hide blocks in the follow camera independently of its position.
    @Inject(method = "shouldHideFollowCameraObstructions", at = @At("HEAD"), cancellable = true, require = 0)
    private void antiFreecam$showFollowCameraObstructions(CallbackInfoReturnable<Boolean> cir) {
        if (FreecamIntegration.forceCollision) {
            cir.setReturnValue(false);
        }
    }
}
