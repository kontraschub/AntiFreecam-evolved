package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.AntiFreecam;
import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    private static final String FREECAM_MOD_CONFIG_MIXIN = "FreecamModConfigMixin";
    private static final String WI_FREECAM_MIXIN = "WiFreecamMixin";

    @Override
    public void onLoad(String mixinPackage) {
        FreecamIntegration.fetchFreecams();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.endsWith("." + FREECAM_MOD_CONFIG_MIXIN)) {
            return FreecamIntegration.isFreecamPresent();
        }

        if (mixinClassName.endsWith("." + WI_FREECAM_MIXIN)) {
            return FreecamIntegration.isWiFreecamPresent();
        }

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        List<String> mixins = new ArrayList<>();

        if (FreecamIntegration.isFreecamPresent()) {
            mixins.add(FREECAM_MOD_CONFIG_MIXIN);
        }

        if (FreecamIntegration.isWiFreecamPresent()) {
            mixins.add(WI_FREECAM_MIXIN);
        }

        return mixins;
    }

    @Override
    public void preApply(String targetClassName, ClassNode classNode, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode classNode, String mixinClassName, IMixinInfo mixinInfo) {
        if (mixinClassName.endsWith("." + WI_FREECAM_MIXIN)) {
            AntiFreecam.LOGGER.info("WI Freecam collision integration applied.");
        }
    }
}
