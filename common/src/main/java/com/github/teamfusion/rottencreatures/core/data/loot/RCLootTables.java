package com.github.teamfusion.rottencreatures.core.data.loot;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class RCLootTables {
    public static final ResourceKey<LootTable> BURNED_OBSIDIAN = LootBuilder.of("burned_obsidian").build();

    public static final ResourceKey<LootTable> UNDEAD_DIAMOND_MINER = LootBuilder.of("undead_diamond_miner").build();
    public static final ResourceKey<LootTable> UNDEAD_IRON_MINER = LootBuilder.of("undead_iron_miner").build();
    public static final ResourceKey<LootTable> UNDEAD_STONE_MINER = LootBuilder.of("undead_stone_miner").build();
    public static final ResourceKey<LootTable> UNDEAD_GOLD_MINER = LootBuilder.of("undead_gold_miner").build();

    public static final ResourceKey<LootTable> TREASURE_CHEST = LootBuilder.of("treasure_chest").build();
}