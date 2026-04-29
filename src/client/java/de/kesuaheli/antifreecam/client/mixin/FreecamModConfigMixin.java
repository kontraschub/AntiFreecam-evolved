package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.xolt.freecam.config.model.ModConfigDTO", remap = false)
public class FreecamModConfigMixin {
    @Inject(method = "ignoreCollisionWith", at = @At("HEAD"), cancellable = true, remap = false)
    private void antiFreecam$forceCollision(Block block, CallbackInfoReturnable<Boolean> cir) {
        if (FreecamIntegration.forceCollision) {
            cir.setReturnValue(false);
        }
    }
}

