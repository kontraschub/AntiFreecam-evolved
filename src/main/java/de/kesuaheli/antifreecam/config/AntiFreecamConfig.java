package de.kesuaheli.antifreecam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.kesuaheli.antifreecam.AntiFreecam;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public record AntiFreecamConfig(boolean limitFreecamToRenderDistance) {
    private static final String FILE_NAME = "antifreecam-evolved.json";
    private static final String LIMIT_RENDER_DISTANCE_KEY = "limitFreecamToRenderDistance";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final AntiFreecamConfig DEFAULTS = new AntiFreecamConfig(false);

    public static AntiFreecamConfig load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);

        if (Files.notExists(configPath)) {
            createDefaultConfig(configPath);
            return DEFAULTS;
        }

        try (Reader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            JsonElement root = JsonParser.parseReader(reader);
            if (!root.isJsonObject()) {
                throw new IllegalArgumentException("The root element must be a JSON object.");
            }

            JsonElement limitElement = root.getAsJsonObject().get(LIMIT_RENDER_DISTANCE_KEY);
            if (limitElement == null) {
                AntiFreecam.LOGGER.warn(
                        "Config option '{}' is missing in {}. Using the default value false.",
                        LIMIT_RENDER_DISTANCE_KEY,
                        configPath
                );
                return DEFAULTS;
            }

            if (!limitElement.isJsonPrimitive() || !limitElement.getAsJsonPrimitive().isBoolean()) {
                throw new IllegalArgumentException("The option '" + LIMIT_RENDER_DISTANCE_KEY + "' must be a boolean.");
            }

            return new AntiFreecamConfig(limitElement.getAsBoolean());
        } catch (Exception exception) {
            AntiFreecam.LOGGER.error("Failed to read {}. Using default settings.", configPath, exception);
            return DEFAULTS;
        }
    }

    private static void createDefaultConfig(Path configPath) {
        JsonObject root = new JsonObject();
        root.addProperty(LIMIT_RENDER_DISTANCE_KEY, DEFAULTS.limitFreecamToRenderDistance());

        try {
            Files.createDirectories(configPath.getParent());
            Files.writeString(
                    configPath,
                    GSON.toJson(root) + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE_NEW
            );
            AntiFreecam.LOGGER.info("Created default config at {}.", configPath);
        } catch (IOException exception) {
            AntiFreecam.LOGGER.error("Failed to create default config at {}. Using default settings.", configPath, exception);
        }
    }
}
