package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Supports Freecam 1.3.x on Minecraft 1.21.1. */
@Pseudo
@Mixin(targets = "net.xolt.freecam.config.CollisionBehavior", remap = false)
public class FreecamCollisionBehaviorMixin {
    @Inject(method = "isIgnored", at = @At("HEAD"), cancellable = true, remap = false)
    private static void antiFreecam$forceCollision(Block block, CallbackInfoReturnable<Boolean> cir) {
        if (FreecamIntegration.forceCollision) {
            cir.setReturnValue(false);
        }
    }
}
