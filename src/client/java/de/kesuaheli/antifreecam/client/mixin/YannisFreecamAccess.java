package de.kesuaheli.antifreecam.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Invoker;

@Pseudo
@Mixin(targets = "net.xolt.xcam.Xcam", remap = false)
public interface YannisFreecamAccess {
    @Invoker(value = "isFollowPlayerEnabled", remap = false)
    static boolean isFollowPlayerEnabled() {
        throw new AssertionError();
    }
}
