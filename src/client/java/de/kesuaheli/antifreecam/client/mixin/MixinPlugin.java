package de.kesuaheli.antifreecam.client.mixin;

import de.kesuaheli.antifreecam.client.integration.FreecamIntegration;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    private static final String FREECAM_MOD_CONFIG_MIXIN = "FreecamModConfigMixin";

    @Override
    public void onLoad(String mixinPackage) {
        FreecamIntegration.fetchFreecam();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !mixinClassName.endsWith("." + FREECAM_MOD_CONFIG_MIXIN) || FreecamIntegration.isFreecamPresent();
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        if (!FreecamIntegration.isFreecamPresent()) {
            return List.of();
        }

        return List.of(FREECAM_MOD_CONFIG_MIXIN);
    }

    @Override
    public void preApply(String targetClassName, ClassNode classNode, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode classNode, String mixinClassName, IMixinInfo mixinInfo) {
    }
}

