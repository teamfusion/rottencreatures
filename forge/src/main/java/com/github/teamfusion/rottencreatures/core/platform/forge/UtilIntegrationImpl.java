package com.github.teamfusion.rottencreatures.core.platform.forge;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.ForgeMod;

import java.util.function.Supplier;

public class UtilIntegrationImpl {
    public static Supplier<Attribute> attackRangeAttribute() {
        return ForgeMod.ATTACK_RANGE;
    }

    public static Supplier<Attribute> reachDistanceAttribute() {
        return ForgeMod.REACH_DISTANCE;
    }
}
