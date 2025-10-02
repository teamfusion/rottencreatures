package com.github.teamfusion.rottencreatures.core.data.loot;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.mixin.access.BuiltInLootTablesAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public record LootBuilder(String key) {
    public static LootBuilder of(String key) {
        return new LootBuilder(key);
    }

    public ResourceKey<LootTable> build(String type) {
        return BuiltInLootTablesAccessor.callRegister(ResourceKey.create(Registries.LOOT_TABLE, RottenCreatures.resource("entities/" + this.key() + "/" + type)));
    }

    public ResourceKey<LootTable> build() {
        return BuiltInLootTablesAccessor.callRegister(ResourceKey.create(Registries.LOOT_TABLE, RottenCreatures.resource("entities/" + this.key())));
    }

    /**
     * the rolls define the amount of tries that the loot goes into before dropping
     */
    public static LootPool.Builder rolls(int rolls) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(rolls));
    }

    public static LootPool.Builder rolls(int min, int max) {
        return LootPool.lootPool().setRolls(UniformGenerator.between(min, max));
    }

    /**
     * define the item or block that should be dropped
     */
    public static LootPoolSingletonContainer.Builder<?> entry(ItemLike entry) {
        return LootItem.lootTableItem(entry);
    }

    /**
     * define the amount of entries that should be dropped
     */
    public static LootItemFunction.Builder count(int count) {
        return count(count, count);
    }

    public static LootItemFunction.Builder count(int min, int max) {
        return SetItemCountFunction.setCount(UniformGenerator.between(min, max));
    }

    /**
     * define the multiplier applied when looting is used
     */
    public static LootItemFunction.Builder looting(HolderLookup.Provider registries, int min, int max) {
        return EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(min, max));
    }

    /**
     * define the chance of dropping
     */
    public static LootItemCondition.Builder chance(float chance) {
        return LootItemRandomChanceCondition.randomChance(chance);
    }

    /**
     * define the chance of dropping along with applying a looting multiplier
     */
    public static LootItemCondition.Builder chanceWithLooting(HolderLookup.Provider registries, float chance, float loot) {
        return LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, chance, loot);
    }

    /**
     * define if the drops should only generate if the entity is killed by a player
     */
    public static LootItemCondition.Builder killedByPlayer() {
        return LootItemKilledByPlayerCondition.killedByPlayer();
    }
}