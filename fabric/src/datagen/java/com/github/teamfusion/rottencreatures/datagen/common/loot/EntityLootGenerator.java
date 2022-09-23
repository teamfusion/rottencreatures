package com.github.teamfusion.rottencreatures.datagen.common.loot;

import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class EntityLootGenerator extends SimpleFabricLootTableProvider {
    public EntityLootGenerator(FabricDataGenerator gen) {
        super(gen, LootContextParamSets.ENTITY);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.add(RCEntityTypes.BURNED, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(RCItems.MAGMA_ROTTEN_FLESH.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.MAGMA_BLOCK)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.MAGMA_CREAM)).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)))
        );

        this.add(Burned.BURNED_OBSIDIAN, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.OBSIDIAN).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 0.01F)))
        );

        this.add(RCEntityTypes.FROSTBITTEN, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(RCItems.FROZEN_ROTTEN_FLESH.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.IRON_INGOT)).add(LootItem.lootTableItem(Items.CARROT)).add(LootItem.lootTableItem(Items.POTATO)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.SNOWBALL)).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))
        );

        this.add(RCEntityTypes.SWAMPY, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BROWN_DYE)).add(LootItem.lootTableItem(Items.POISONOUS_POTATO)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 0.01F)))
        );

        this.add(RCEntityTypes.TNT_BARREL, consumer, LootTable.lootTable());
    }

    private <T extends Entity> void add(Supplier<EntityType<T>> type, BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTable.Builder builder) {
        consumer.accept(type.get().getDefaultLootTable(), builder);
    }

    private <T extends Entity> void add(ResourceLocation type, BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTable.Builder builder) {
        consumer.accept(type, builder);
    }
}
