package com.github.teamfusion.rottencreatures.core.platform.common.fabric;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class SpawnEggImpl {
    public static <T extends Mob> SpawnEggItem of(Supplier<EntityType<T>> type, int backgroundColor, int highlightColor) {
        return new SpawnEggItem(type.get(), backgroundColor, highlightColor, new SpawnEggItem.Properties());
    }
}