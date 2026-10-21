package de.kesuaheli.antifreecam.client.mixin;

import cameratweaks.Util;
import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Pseudo
@Mixin(targets = "cameratweaks.Freecam", remap = false)
public class CameraTweaksFreecamMixin {
    @Shadow(remap = false)
    public static Util.LerpedPos pos;

    @Unique
    private static Vec3 antiFreecam$movementStart;
    @Unique
    private static Vec3 antiFreecam$loadStart;

    @Inject(method = "update", at = @At("HEAD"), remap = false)
    private static void antiFreecam$beforeUpdate(float delta, CallbackInfo ci) {
        antiFreecam$movementStart = pos == null ? null : pos.pos;
    }

    // RETURN also covers the early return while player movement is enabled.
    @Inject(method = "update", at = @At("RETURN"), remap = false)
    private static void antiFreecam$afterUpdate(float delta, CallbackInfo ci) {
        antiFreecam$constrain(antiFreecam$movementStart, false);
    }

    @Inject(method = "loadCamera", at = @At("HEAD"), remap = false)
    private static void antiFreecam$beforeLoad(int slot, CallbackInfo ci) {
        antiFreecam$loadStart = pos == null ? antiFreecam$playerEye() : pos.pos;
    }

    @Inject(method = "loadCamera", at = @At("RETURN"), remap = false)
    private static void antiFreecam$afterLoad(int slot, CallbackInfo ci) {
        antiFreecam$constrain(antiFreecam$loadStart, true);
    }

    // Activation and saving can copy a third-person camera position beyond a wall.
    @Inject(method = "setPosition", at = @At("RETURN"), remap = false)
    private static void antiFreecam$afterSetPosition(CallbackInfo ci) {
        antiFreecam$constrain(antiFreecam$playerEye(), true);
    }

    @Unique
    private static Vec3 antiFreecam$playerEye() {
        Minecraft client = Minecraft.getInstance();
        return client.player == null ? null : client.player.getEyePosition();
    }

    @Unique
    private static void antiFreecam$constrain(Vec3 start, boolean teleport) {
        if (!FreecamIntegration.forceCollision) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (pos == null || start == null || client.level == null || client.player == null) {
            return;
        }

        Vec3 requested = pos.pos;
        Vec3 allowed = requested;
        if (FreecamIntegration.forceCollision) {
            Vec3 movement = allowed.subtract(start);
            allowed = start.add(Entity.collideBoundingBox(
                    client.player, movement,
                    AABB.ofSize(start, 0.2, 0.2, 0.2), client.level, List.of()));
        }

        pos.pos = allowed;
        // A saved camera initializes BOTH interpolation endpoints at its destination.
        if (teleport && !allowed.equals(requested)) {
            pos.prevPos = allowed;
        }
    }
}
