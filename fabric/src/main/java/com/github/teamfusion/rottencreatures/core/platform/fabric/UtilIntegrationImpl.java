package com.github.teamfusion.rottencreatures.core.platform.fabric;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public class UtilIntegrationImpl {
    public static Supplier<Attribute> attackRangeAttribute() {
        return () -> ReachEntityAttributes.ATTACK_RANGE;
    }

    public static Supplier<Attribute> reachDistanceAttribute() {
        return () -> ReachEntityAttributes.REACH;
    }
}
