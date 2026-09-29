package de.kesuaheli.antifreecam.client.integration;

import de.kesuaheli.antifreecam.AntiFreecam;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;

public final class RenderDistanceLimiter {
    private static final String FREECAM_CAMERA_CLASS = "net.xolt.freecam.util.FreeCamera";
    private static final double CHUNK_SIZE = 16.0;
    private static final double MAX_BOUNDARY_OFFSET = 1.0E-7;
    private static boolean limitLogged;

    private RenderDistanceLimiter() {
    }

    public static void enforceForFreecam(Minecraft client) {
        if (!FreecamIntegration.limitToRenderDistance || client.player == null) {
            resetLog();
            return;
        }

        Entity cameraEntity = client.getCameraEntity();
        if (cameraEntity == null || !FREECAM_CAMERA_CLASS.equals(cameraEntity.getClass().getName())) {
            return;
        }

        Vec3 limitedPosition = limit(cameraEntity.position(), client);
        if (!limitedPosition.equals(cameraEntity.position())) {
            cameraEntity.setPos(limitedPosition);
        }
    }

    public static Vec3 limit(Vec3 cameraPosition) {
        return limit(cameraPosition, Minecraft.getInstance());
    }

    public static void resetLog() {
        limitLogged = false;
    }

    private static Vec3 limit(Vec3 cameraPosition, Minecraft client) {
        if (!FreecamIntegration.limitToRenderDistance || client.player == null) {
            return cameraPosition;
        }

        int renderDistance = client.options.getEffectiveRenderDistance();
        ChunkPos playerChunk = client.player.chunkPosition();
        double minX = (playerChunk.x - (long) renderDistance) * CHUNK_SIZE;
        double maxX = (playerChunk.x + (long) renderDistance + 1L) * CHUNK_SIZE - MAX_BOUNDARY_OFFSET;
        double minZ = (playerChunk.z - (long) renderDistance) * CHUNK_SIZE;
        double maxZ = (playerChunk.z + (long) renderDistance + 1L) * CHUNK_SIZE - MAX_BOUNDARY_OFFSET;

        double limitedX = Math.clamp(cameraPosition.x, minX, maxX);
        double limitedZ = Math.clamp(cameraPosition.z, minZ, maxZ);
        if (limitedX == cameraPosition.x && limitedZ == cameraPosition.z) {
            return cameraPosition;
        }

        if (!limitLogged) {
            limitLogged = true;
            AntiFreecam.LOGGER.info("Freecam camera movement limited to the player's render distance.");
        }

        return new Vec3(limitedX, cameraPosition.y, limitedZ);
    }
}
