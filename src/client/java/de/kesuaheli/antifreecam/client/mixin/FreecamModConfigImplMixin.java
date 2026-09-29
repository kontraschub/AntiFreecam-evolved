package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Supports Freecam versions that keep the runtime configuration separate from its DTO.
 */
@Pseudo
@Mixin(targets = "net.xolt.freecam.config.model.ModConfigImpl", remap = false)
public class FreecamModConfigImplMixin {
    @Inject(method = "ignoreCollisionWith", at = @At("HEAD"), cancellable = true, remap = false)
    private void antiFreecam$forceCollision(Block block, CallbackInfoReturnable<Boolean> cir) {
        if (FreecamIntegration.forceCollision) {
            cir.setReturnValue(false);
        }
    }
}
