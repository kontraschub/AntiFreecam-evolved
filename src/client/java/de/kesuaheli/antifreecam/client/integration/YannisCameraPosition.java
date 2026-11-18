package de.kesuaheli.antifreecam.client.integration;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class YannisCameraPosition {
    private static final double CAMERA_RADIUS = 0.1;
    private static final double MAX_SWEEP_STEP = 8.0;
    private static final int MAX_SWEEP_STEPS = 64;
    private static final double WALL_CLEARANCE = 1.0E-7;

    private YannisCameraPosition() {
    }

    public static Vec3 constrain(Vec3 start, Vec3 requested, Minecraft client) {
        Vec3 target = RenderDistanceLimiter.limit(requested);
        // A player teleport can leave the previous camera position outside the new bounds.
        if (client.player != null && !RenderDistanceLimiter.limit(start).equals(start)) {
            start = client.player.getEyePosition();
        }
        if (!FreecamIntegration.forceCollision || client.level == null || start.equals(target)) {
            return target;
        }

        // Sweep along the intended line so a diagonal camera jump cannot slide around a corner.
        Vec3 movement = target.subtract(start);
        double distance = movement.length();
        if (!Double.isFinite(distance) || distance == 0) {
            return start;
        }
        Vec3 direction = movement.scale(1.0 / distance);
        double checkedDistance = Math.min(distance, MAX_SWEEP_STEPS * MAX_SWEEP_STEP);
        for (double offset = 0; offset < checkedDistance; offset += MAX_SWEEP_STEP) {
            // AABB.clip excludes segment endpoints. Overlap the joins by a small amount.
            Vec3 from = start.add(direction.scale(offset - WALL_CLEARANCE));
            Vec3 to = start.add(direction.scale(Math.min(offset + MAX_SWEEP_STEP, checkedDistance) + WALL_CLEARANCE));
            AABB area = AABB.ofSize(from, 2 * CAMERA_RADIUS, 2 * CAMERA_RADIUS, 2 * CAMERA_RADIUS)
                    .expandTowards(to.subtract(from));
            Vec3 closest = null;
            double closestDistance = Double.POSITIVE_INFINITY;
            for (VoxelShape shape : client.level.getBlockCollisions(null, area)) {
                for (AABB box : shape.toAabbs()) {
                    Vec3 hit = box.inflate(CAMERA_RADIUS).clip(from, to).orElse(null);
                    if (hit != null && from.distanceToSqr(hit) < closestDistance) {
                        closestDistance = from.distanceToSqr(hit);
                        closest = hit;
                    }
                }
            }
            if (closest != null) {
                double allowed = closest.subtract(start).dot(direction) - WALL_CLEARANCE;
                return start.add(direction.scale(Math.clamp(allowed, 0, checkedDistance)));
            }
        }
        return checkedDistance < distance ? start.add(direction.scale(checkedDistance)) : target;
    }
}
