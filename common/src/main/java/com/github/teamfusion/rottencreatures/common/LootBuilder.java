package com.github.teamfusion.rottencreatures.common;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.mixin.access.BuiltInLootTablesAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public record LootBuilder(String key) {
    public static LootBuilder of(String key) {
        return new LootBuilder(key);
    }

    public ResourceLocation build(String type) {
        return BuiltInLootTablesAccessor.callRegister(new ResourceLocation(RottenCreatures.MOD_ID, "entities/" + this.key() + "/" + type));
    }

    public static LootItemFunction.Builder count(int min, int max) {
        return SetItemCountFunction.setCount(UniformGenerator.between(min, max));
    }

    public static LootItemFunction.Builder count(int max) {
        return count(max, max);
    }

    public static LootItemFunction.Builder looting(int min, int max) {
        return LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(min, max));
    }
}