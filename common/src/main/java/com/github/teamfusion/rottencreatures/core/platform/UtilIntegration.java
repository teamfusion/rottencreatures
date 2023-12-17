package com.github.teamfusion.rottencreatures.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public class UtilIntegration {
    @ExpectPlatform
    public static Supplier<Attribute> attackRangeAttribute() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Attribute> reachDistanceAttribute() {
        throw new AssertionError();
    }
}