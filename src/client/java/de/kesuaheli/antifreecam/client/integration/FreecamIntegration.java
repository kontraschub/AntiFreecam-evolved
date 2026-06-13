package de.kesuaheli.antifreecam.client.integration;

import de.kesuaheli.antifreecam.AntiFreecam;
import net.fabricmc.loader.api.FabricLoader;

public final class FreecamIntegration {
    public static final String MOD_ID = "freecam";
    public static final String WI_FREECAM_MOD_ID = "wi_freecam";

    private static boolean fetched;
    private static boolean freecamPresent;
    private static boolean wiFreecamPresent;

    public static boolean forceCollision;

    private FreecamIntegration() {
    }

    public static void enable() {
        if (isFreecamPresent()) {
            AntiFreecam.LOGGER.info("Freecam mod installed on client.");
        } else {
            AntiFreecam.LOGGER.info("Freecam mod not installed on client.");
        }

        if (isWiFreecamPresent()) {
            AntiFreecam.LOGGER.info("WI Freecam mod installed on client.");
        } else {
            AntiFreecam.LOGGER.info("WI Freecam mod not installed on client.");
        }
    }

    public static void fetchFreecams() {
        freecamPresent = FabricLoader.getInstance().isModLoaded(MOD_ID);
        wiFreecamPresent = FabricLoader.getInstance().isModLoaded(WI_FREECAM_MOD_ID);
        fetched = true;
    }

    public static boolean isFreecamPresent() {
        if (!fetched) {
            fetchFreecams();
        }

        return freecamPresent;
    }

    public static boolean isWiFreecamPresent() {
        if (!fetched) {
            fetchFreecams();
        }

        return wiFreecamPresent;
    }
}
