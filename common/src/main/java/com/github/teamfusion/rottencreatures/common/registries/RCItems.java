package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.helper.ItemRegistry;
import com.github.teamfusion.rottencreatures.common.level.items.FrozenRottenFleshItem;
import com.github.teamfusion.rottencreatures.common.level.items.MagmaRottenFleshItem;
import com.github.teamfusion.rottencreatures.common.level.items.RCFoodProperties;
import com.github.teamfusion.rottencreatures.common.level.items.SpearItem;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
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
        new Item.Properties()
    );
    public static final Supplier<Item> FROSTBITTEN_SPAWN_EGG = ITEMS.spawnEgg(
        "frostbitten_spawn_egg",
        RCEntityTypes.FROSTBITTEN,
        5864108,
        8967167,
        new Item.Properties()
    );
    public static final Supplier<Item> SWAMPY_SPAWN_EGG = ITEMS.spawnEgg(
        "swampy_spawn_egg",
        RCEntityTypes.SWAMPY,
        3351821,
        7227939,
        new Item.Properties()
    );
    public static final Supplier<Item> UNDEAD_MINER_SPAWN_EGG = ITEMS.spawnEgg(
        "undead_miner_spawn_egg",
        RCEntityTypes.UNDEAD_MINER,
        6107715,
        7105644,
        new Item.Properties()
    );
    public static final Supplier<Item> MUMMY_SPAWN_EGG = ITEMS.spawnEgg(
        "mummy_spawn_egg",
        RCEntityTypes.MUMMY,
        13333578,
        7956784,
        new Item.Properties()
    );
    public static final Supplier<Item> GLACIAL_HUNTER_SPAWN_EGG = ITEMS.spawnEgg(
        "glacial_hunter_spawn_egg",
        RCEntityTypes.GLACIAL_HUNTER,
        7554081,
        15651988,
        new Item.Properties()
    );
    public static final Supplier<Item> DEAD_BEARD_SPAWN_EGG = ITEMS.spawnEgg(
        "dead_beard_spawn_egg",
        RCEntityTypes.DEAD_BEARD,
        2369569,
        1270579,
        new Item.Properties()
    );
    public static final Supplier<Item> IMMORTAL_SPAWN_EGG = ITEMS.spawnEgg(
        "immortal_spawn_egg",
        RCEntityTypes.IMMORTAL,
        1583154,
        3381149,
        new Item.Properties()
    );
    public static final Supplier<Item> SCARAB_SPAWN_EGG = ITEMS.spawnEgg(
        "scarab_spawn_egg",
        RCEntityTypes.SCARAB,
        6250420,
        16108133,
        new Item.Properties()
    );
    public static final Supplier<Item> HUNTER_WOLF_SPAWN_EGG = ITEMS.spawnEgg(
        "hunter_wolf_spawn_egg",
        RCEntityTypes.HUNTER_WOLF,
        1580850,
        13980981,
        new Item.Properties()
    );
    public static final Supplier<Item> SKELETON_LACKEY_SPAWN_EGG = ITEMS.spawnEgg(
        "skeleton_lackey_spawn_egg",
        RCEntityTypes.SKELETON_LACKEY,
        12900322,
        12204063,
        new Item.Properties()
    );
    public static final Supplier<Item> ZOMBIE_LACKEY_SPAWN_EGG = ITEMS.spawnEgg(
        "zombie_lackey_spawn_egg",
        RCEntityTypes.ZOMBIE_LACKEY,
        5081490,
        12204063,
        new Item.Properties()
    );
    public static final Supplier<Item> ZAP_SPAWN_EGG = ITEMS.spawnEgg(
        "zap_spawn_egg",
        RCEntityTypes.ZAP,
        667170,
        6544568,
        new Item.Properties()
    );

    // Food
    public static final Supplier<Item> MAGMA_ROTTEN_FLESH = ITEMS.register(
        "magma_rotten_flesh",
        () -> new MagmaRottenFleshItem(
            new Item.Properties()
                .food(RCFoodProperties.MAGMA_ROTTEN_FLESH)
        )
    );
    public static final Supplier<Item> FROZEN_ROTTEN_FLESH = ITEMS.register(
        "frozen_rotten_flesh",
        () -> new FrozenRottenFleshItem(
            new Item.Properties()
                .food(RCFoodProperties.FROZEN_ROTTEN_FLESH)
        )
    );

    // MISC
    public static final Supplier<Item> CORRUPTED_WART = ITEMS.register(
        "corrupted_wart",
        new Item.Properties()
    );

    public static final Supplier<Item> SPEAR = ITEMS.register(
        "spear",
        SpearItem::new,
        new Item.Properties()
            .stacksTo(1)
            .durability(200)
    );
}