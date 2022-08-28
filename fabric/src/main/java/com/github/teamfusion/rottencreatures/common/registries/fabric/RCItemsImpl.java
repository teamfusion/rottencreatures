package com.github.teamfusion.rottencreatures.common.registries.fabric;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class RCItemsImpl {
    public static Supplier<Item> spawnEgg(Supplier<? extends EntityType<? extends Mob>> entity, int background, int highlight) {
        return () -> new SpawnEggItem(entity.get(), background, highlight, new Item.Properties().tab(RottenCreatures.TAB));
    }
}