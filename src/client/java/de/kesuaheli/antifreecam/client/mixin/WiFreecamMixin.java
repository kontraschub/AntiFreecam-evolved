package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.AntiFreecam;
import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.wimods.freecam.WiFreecam", remap = false)
public class WiFreecamMixin {
    private static final double CAMERA_SIZE = 0.2;
    private static boolean collisionLogged;

    @Shadow(remap = false)
    private Vec3 camPos;

    @Shadow(remap = false)
    private Vec3 prevCamPos;

    @Inject(
            method = "onUpdate",
            at = @At("TAIL"),
            remap = false
    )
    private void antiFreecam$applyCollision(CallbackInfo ci) {
        if (!FreecamIntegration.forceCollision || camPos == null || prevCamPos == null) {
            return;
        }

        Vec3 movement = camPos.subtract(prevCamPos);
        if (movement.lengthSqr() == 0) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) {
            return;
        }

        AABB cameraBox = AABB.ofSize(prevCamPos, CAMERA_SIZE, CAMERA_SIZE, CAMERA_SIZE);
        Vec3 allowedMovement = Entity.collideBoundingBox(
                CollisionContext.empty(),
                movement,
                cameraBox,
                client.level,
                java.util.List.of()
        );

        if (allowedMovement.equals(movement)) {
            return;
        }

        camPos = prevCamPos.add(allowedMovement);
        if (!collisionLogged) {
            collisionLogged = true;
            AntiFreecam.LOGGER.info("WI Freecam camera movement blocked by collision.");
        }
    }
}
