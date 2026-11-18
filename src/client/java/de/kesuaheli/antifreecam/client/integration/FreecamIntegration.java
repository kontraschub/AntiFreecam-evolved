package de.kesuaheli.antifreecam.client.integration;

import de.kesuaheli.antifreecam.AntiFreecam;
import net.fabricmc.loader.api.FabricLoader;

public final class FreecamIntegration {
    public static final String MOD_ID = "freecam";
    private static final String MODERN_CONFIG_CLASS = "net/xolt/freecam/config/model/ModConfigImpl.class";
    private static final String YANNIS_CAMERA_CLASS = "net/xolt/xcam/util/XCamera.class";

    private static boolean fetched;
    private static boolean freecamPresent;
    private static boolean yannisFreecamPresent;

    public static boolean forceCollision;

    private FreecamIntegration() {
    }

    public static void enable() {
        AntiFreecam.LOGGER.info("Yannis' FreeCam installed on client: {}.", isYannisFreecamPresent());
        AntiFreecam.LOGGER.info("Camera Tweaks installed on client: {}.", isCameraTweaksPresent());
        if (isFreecamPresent()) {
            AntiFreecam.LOGGER.info("Freecam mod installed on client.");
        } else {
            AntiFreecam.LOGGER.info("Freecam mod not installed on client.");
        }
    }

    public static boolean fetchFreecam() {
        FabricLoader loader = FabricLoader.getInstance();
        yannisFreecamPresent = loader.getModContainer(MOD_ID)
                .flatMap(mod -> mod.findPath(YANNIS_CAMERA_CLASS))
                .isPresent();
        freecamPresent = loader.isModLoaded(MOD_ID) && !yannisFreecamPresent;
        fetched = true;
        return freecamPresent;
    }

    public static boolean isFreecamPresent() {
        if (!fetched) {
            return fetchFreecam();
        }

        return freecamPresent;
    }

    public static boolean isYannisFreecamPresent() {
        if (!fetched) {
            fetchFreecam();
        }
        return yannisFreecamPresent;
    }

    public static boolean isCameraTweaksPresent() {
        return FabricLoader.getInstance().isModLoaded("cameratweaks");
    }

    public static boolean hasModernFreecamConfig() {
        return isFreecamPresent() && FabricLoader.getInstance()
                .getModContainer(MOD_ID)
                .flatMap(modContainer -> modContainer.findPath(MODERN_CONFIG_CLASS))
                .isPresent();
    }
}
