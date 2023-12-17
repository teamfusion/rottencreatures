package com.github.teamfusion.rottencreatures.core.platform.common.forge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class SpawnEggImpl {
    public static <T extends Mob> SpawnEggItem of(Supplier<EntityType<T>> type, int backgroundColor, int highlightColor) {
        return new ForgeSpawnEggItem(type, backgroundColor, highlightColor, new SpawnEggItem.Properties());
    }
}