package com.github.teamfusion.rottencreatures.common;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.mixin.access.BuiltInLootTablesAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public record LootBuilder(String key) {
    public static LootBuilder of(String key) {
        return new LootBuilder(key);
    }

    public ResourceLocation build(String type) {
        return BuiltInLootTablesAccessor.callRegister(new ResourceLocation(RottenCreatures.MOD_ID, "entities/" + this.key() + "/" + type));
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
    public static LootItemFunction.Builder looting(int min, int max) {
        return LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(min, max));
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
    public static LootItemCondition.Builder chanceWithLooting(float chance, float loot) {
        return LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(chance, loot);
    }

    /**
     * define if the drops should only generate if the entity is killed by a player
     */
    public static LootItemCondition.Builder killedByPlayer() {
        return LootItemKilledByPlayerCondition.killedByPlayer();
    }
}