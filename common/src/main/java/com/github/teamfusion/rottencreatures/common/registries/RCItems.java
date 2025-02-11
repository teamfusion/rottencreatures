package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.helper.ItemRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.item.FrozenRottenFleshItem;
import com.github.teamfusion.rottencreatures.common.item.MagmaRottenFleshItem;
import com.github.teamfusion.rottencreatures.common.item.SpearItem;
import com.github.teamfusion.rottencreatures.common.item.TreasureChestItem;
import com.github.teamfusion.rottencreatures.common.misc.RCFoodProperties;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class RCItems {
    public static final ItemRegistry ITEMS = ItemRegistry.create(RottenCreatures.MOD_ID);

    // Spawns
    public static final Supplier<Item> BURNED_SPAWN_EGG = ITEMS.spawnEgg(
        "burned_spawn_egg",
        RCEntityTypes.BURNED,
        5969689,
        14244138,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
    public static final Supplier<Item> FROSTBITTEN_SPAWN_EGG = ITEMS.spawnEgg(
        "frostbitten_spawn_egg",
        RCEntityTypes.FROSTBITTEN,
        5864108,
        8967167,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
    public static final Supplier<Item> SWAMPY_SPAWN_EGG = ITEMS.spawnEgg(
        "swampy_spawn_egg",
        RCEntityTypes.SWAMPY,
        3351821,
        7227939,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
    public static final Supplier<Item> UNDEAD_MINER_SPAWN_EGG = ITEMS.spawnEgg(
        "undead_miner_spawn_egg",
        RCEntityTypes.UNDEAD_MINER,
        6107715,
        7105644,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
    public static final Supplier<Item> MUMMY_SPAWN_EGG = ITEMS.spawnEgg(
        "mummy_spawn_egg",
        RCEntityTypes.MUMMY,
        13333578,
        7956784,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
    public static final Supplier<Item> GLACIAL_HUNTER_SPAWN_EGG = ITEMS.spawnEgg(
        "glacial_hunter_spawn_egg",
        RCEntityTypes.GLACIAL_HUNTER,
        7554081,
        15651988,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
    public static final Supplier<Item> DEAD_BEARD_SPAWN_EGG = ITEMS.spawnEgg(
        "dead_beard_spawn_egg",
        RCEntityTypes.DEAD_BEARD,
        2369569,
        1270579,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
    public static final Supplier<Item> IMMORTAL_SPAWN_EGG = ITEMS.spawnEgg(
        "immortal_spawn_egg",
        RCEntityTypes.IMMORTAL,
        1583154,
        3381149,
        new Item.Properties().tab(RottenCreatures.TAB)
    );

    // Food
    public static final Supplier<Item> MAGMA_ROTTEN_FLESH = ITEMS.register(
        "magma_rotten_flesh",
        () -> new MagmaRottenFleshItem(
            new Item.Properties()
                .food(RCFoodProperties.MAGMA_ROTTEN_FLESH)
                .tab(RottenCreatures.TAB)
        )
    );
    public static final Supplier<Item> FROZEN_ROTTEN_FLESH = ITEMS.register(
        "frozen_rotten_flesh",
        () -> new FrozenRottenFleshItem(
            new Item.Properties()
                .food(RCFoodProperties.FROZEN_ROTTEN_FLESH)
                .tab(RottenCreatures.TAB)
        )
    );

    // MISC
    public static final Supplier<Item> CORRUPTED_WART = ITEMS.register(
        "corrupted_wart",
        () -> new Item(
            new Item.Properties().tab(RottenCreatures.TAB)
        )
    );
    public static final Supplier<Item> TREASURE_CHEST = ITEMS.register(
        "treasure_chest",
        () -> new TreasureChestItem(
            new Item.Properties()
                .stacksTo(1)
                .tab(RottenCreatures.TAB)
        )
    );
    public static final Supplier<Item> SPEAR = ITEMS.register(
        "spear",
        () -> new SpearItem(
            new Item.Properties()
                .stacksTo(1)
                .durability(200)
                .tab(RottenCreatures.TAB)
        )
    );
}