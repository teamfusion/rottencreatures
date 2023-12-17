package com.github.teamfusion.rottencreatures.core.platform.common;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class SpawnEgg {
    @ExpectPlatform
    public static <T extends Mob> SpawnEggItem of(Supplier<EntityType<T>> type, int backgroundColor, int highlightColor) {
        throw new AssertionError();
    }
}