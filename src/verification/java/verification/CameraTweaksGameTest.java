package verification;

import cameratweaks.Freecam;
import cameratweaks.Keybinds;
import cameratweaks.Util;
import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public final class CameraTweaksGameTest implements FabricClientGameTest {
    @Override
    public void runTest(ClientGameTestContext context) {
        if (!FreecamIntegration.isCameraTweaksPresent()) {
            System.out.println("CAMERA_TWEAKS_ABSENT_PASS");
            return;
        }
        try (var world = context.worldBuilder().create()) {
            world.getConnection().waitForChunksDownload();
            context.runOnClient(client -> {
                Vec3 start = new Vec3(Math.floor(client.player.getX()) + 0.5, 200.5,
                        Math.floor(client.player.getZ()) + 0.5);
                var dimension = client.level.dimension();
                Keybinds.freecam.setEnabled(true);
                Keybinds.playerMovement.setEnabled(false);
                var camerasField = Freecam.class.getDeclaredField("cameras");
                camerasField.setAccessible(true);
                var cameras = (Util.Pos[]) camerasField.get(null);
                BlockPos wall = BlockPos.containing(start.add(1, 0, 0));
                for (var block : new net.minecraft.world.level.block.Block[]{Blocks.STONE, Blocks.GLASS, Blocks.BEDROCK}) {
                    client.level.setBlock(wall, block.defaultBlockState(), 3);
                    Freecam.pos = new Util.LerpedPos(dimension, start, 0, 0, 70);
                    cameras[0] = new Util.Pos(dimension, start.add(4, 0, 0), 0, 0, 70);
                    FreecamIntegration.forceCollision = true;
                    Freecam.loadCamera(0);
                    check(Freecam.pos.pos.x <= start.x + 0.400001, "Saved camera crossed " + block);
                    check(Freecam.pos.prevPos.equals(Freecam.pos.pos), "Teleport interpolation bypass");
                    FreecamIntegration.forceCollision = false;
                    Freecam.loadCamera(0);
                    check(Freecam.pos.pos.equals(cameras[0].pos), "Enforcement did not reset");
                }
                client.player.setPos(start.x, start.y - client.player.getEyeHeight(), start.z);
                Keybinds.freecam.setEnabled(false);
                var setCameraPosition = net.minecraft.client.Camera.class.getDeclaredMethod("setPosition", Vec3.class);
                setCameraPosition.setAccessible(true);
                setCameraPosition.invoke(client.gameRenderer.mainCamera(), start.add(4, 0, 0));
                FreecamIntegration.forceCollision = true;
                Keybinds.freecam.setEnabled(true);
                check(Freecam.pos.pos.x <= start.x + 0.400001, "Activation copied a camera behind a wall");
                check(Freecam.pos.prevPos.equals(Freecam.pos.pos), "Activation interpolation bypass");
                Keybinds.freecam.setEnabled(false);
                Freecam.loadCamera(0);
                check(Freecam.pos.pos.x <= start.x + 0.400001, "Saved camera activation crossed a wall");

                client.level.setBlock(wall, Blocks.AIR.defaultBlockState(), 3);
                client.level.setBlock(BlockPos.containing(start.add(0, 1, 0)), Blocks.STONE.defaultBlockState(), 3);
                Freecam.pos = new Util.LerpedPos(dimension, start, 0, 0, 70);
                Util.input.keyPresses = new Input(false, false, false, false, true, false, false);
                Freecam.speed = 1;
                FreecamIntegration.forceCollision = true;
                Freecam.update(10);
                check(Freecam.pos.pos.y <= start.y + 0.400001, "High-speed movement crossed ceiling");
                check(Freecam.pos.prevPos.equals(start), "Movement interpolation start changed");
                FreecamIntegration.forceCollision = false;
                Freecam.update(10);
                check(Freecam.pos.pos.y > start.y + 5, "Movement remains blocked after reset");
                Util.input.keyPresses = Input.EMPTY;

                FreecamIntegration.limitToRenderDistance = true;
                cameras[0] = new Util.Pos(dimension, start.add(100000, 0, 100000), 0, 0, 70);
                Freecam.loadCamera(0);
                double maxX = (client.player.chunkPosition().x() + (long) client.options.getEffectiveRenderDistance() + 1) * 16.0;
                check(Freecam.pos.pos.x < maxX, "Saved camera escaped render distance");
                check(Freecam.pos.prevPos.equals(Freecam.pos.pos), "Range interpolation bypass");
                Keybinds.playerMovement.setEnabled(true);
                Freecam.pos = cameras[0].toLerped();
                Freecam.update(0);
                check(Freecam.pos.pos.x < maxX && Freecam.pos.prevPos.x < maxX,
                        "Idle camera escaped render distance while player movement is enabled");

                FreecamIntegration.limitToRenderDistance = false;
                Freecam.reset();
                System.out.println("CAMERA_TWEAKS_GAME_TEST_PASS: stone/glass/bedrock, saved cameras, activation, interpolation, high-speed movement, reset, render distance, player movement");
            });
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Camera Tweaks API changed", e);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
