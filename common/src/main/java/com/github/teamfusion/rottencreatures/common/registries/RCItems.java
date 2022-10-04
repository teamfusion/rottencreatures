package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.item.MagmaRottenFleshItem;
import com.github.teamfusion.rottencreatures.common.misc.RCFoodProperties;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class RCItems {
    public static final CoreRegistry<Item> ITEMS = CoreRegistry.create(Registry.ITEM, RottenCreatures.MOD_ID);

    // Spawns
    public static final Supplier<Item> BURNED_SPAWN_EGG = create("burned_spawn_egg", spawnEgg(RCEntityTypes.BURNED, 5969689, 14244138));
    public static final Supplier<Item> FROSTBITTEN_SPAWN_EGG = create("frostbitten_spawn_egg", spawnEgg(RCEntityTypes.FROSTBITTEN, 5864108, 8967167));
    public static final Supplier<Item> SWAMPY_SPAWN_EGG = create("swampy_spawn_egg", spawnEgg(RCEntityTypes.SWAMPY, 3351821, 7227939));

    // Loot
    public static final Supplier<Item> MAGMA_ROTTEN_FLESH = create("magma_rotten_flesh", () -> new MagmaRottenFleshItem(new Item.Properties().food(RCFoodProperties.MAGMA_ROTTEN_FLESH).tab(RottenCreatures.TAB)));
    public static final Supplier<Item> FROZEN_ROTTEN_FLESH = create("frozen_rotten_flesh", () -> new Item(new Item.Properties().food(RCFoodProperties.FROZEN_ROTTEN_FLESH).tab(RottenCreatures.TAB)));

    private static <T extends Item> Supplier<T> create(String key, Supplier<T> item) {
        return ITEMS.register(key, item);
    }

    @ExpectPlatform
    private static Supplier<Item> spawnEgg(Supplier<? extends EntityType<? extends Mob>> entity, int background, int highlight) {
        throw new AssertionError();
    }
}