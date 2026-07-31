package de.kesuaheli.antifreecam.client.integration;

import de.kesuaheli.antifreecam.AntiFreecam;
import net.fabricmc.loader.api.FabricLoader;

public final class FreecamIntegration {
    public static final String MOD_ID = "freecam";
    private static final String MODERN_CONFIG_CLASS = "net/xolt/freecam/config/model/ModConfigImpl.class";

    private static boolean fetched;
    private static boolean freecamPresent;

    public static boolean forceCollision;

    private FreecamIntegration() {
    }

    public static void enable() {
        if (isFreecamPresent()) {
            AntiFreecam.LOGGER.info("Freecam mod installed on client.");
        } else {
            AntiFreecam.LOGGER.info("Freecam mod not installed on client.");
        }
    }

    public static boolean fetchFreecam() {
        freecamPresent = FabricLoader.getInstance().isModLoaded(MOD_ID);
        fetched = true;
        return freecamPresent;
    }

    public static boolean isFreecamPresent() {
        if (!fetched) {
            return fetchFreecam();
        }

        return freecamPresent;
    }

    public static boolean hasModernFreecamConfig() {
        return isFreecamPresent() && FabricLoader.getInstance()
                .getModContainer(MOD_ID)
                .flatMap(modContainer -> modContainer.findPath(MODERN_CONFIG_CLASS))
                .isPresent();
    }
}
