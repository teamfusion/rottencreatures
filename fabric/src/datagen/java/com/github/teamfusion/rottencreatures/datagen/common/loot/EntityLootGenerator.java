package com.github.teamfusion.rottencreatures.datagen.common.loot;

import com.github.teamfusion.rottencreatures.common.LootBuilder;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class EntityLootGenerator extends SimpleFabricLootTableProvider {
    public EntityLootGenerator(FabricDataGenerator gen) {
        super(gen, LootContextParamSets.ENTITY);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.add(RCEntityTypes.BURNED, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(RCItems.MAGMA_ROTTEN_FLESH.get()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.MAGMA_BLOCK)).apply(LootBuilder.count(0, 1)).apply(LootBuilder.looting(0, 1)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.MAGMA_CREAM)).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)))
        );

        this.add(Burned.OBSIDIAN_LOOT, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.OBSIDIAN).apply(LootBuilder.count(0, 2))).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 0.01F)))
        );

        this.add(RCEntityTypes.FROSTBITTEN, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(RCItems.FROZEN_ROTTEN_FLESH.get()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.IRON_INGOT)).add(LootItem.lootTableItem(Items.CARROT)).add(LootItem.lootTableItem(Items.POTATO)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.SNOWBALL)).apply(LootBuilder.count(0, 1)).apply(LootBuilder.looting(0, 1)))
        );

        this.add(RCEntityTypes.SWAMPY, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.BROWN_DYE)).add(LootItem.lootTableItem(Items.POISONOUS_POTATO)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 0.01F)))
        );

        this.add(UndeadMiner.DIAMOND_LOOT, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RAW_GOLD)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(1, 5)).apply(LootBuilder.looting(0, 1)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.DIAMOND)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(1)).apply(LootBuilder.looting(0, 1)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COAL)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RAW_IRON)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
        );

        this.add(UndeadMiner.IRON_LOOT, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RAW_IRON)).add(LootItem.lootTableItem(Items.RAW_GOLD)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(0, 1)).apply(LootBuilder.looting(0, 1)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COAL)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
        );

        this.add(UndeadMiner.STONE_LOOT, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COAL)).add(LootItem.lootTableItem(Items.RAW_IRON)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(0, 1)).apply(LootBuilder.looting(0, 1)))
        );

        this.add(UndeadMiner.GOLD_LOOT, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COAL)).add(LootItem.lootTableItem(Items.GOLD_INGOT)).when(LootItemKilledByPlayerCondition.killedByPlayer()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
        );

        this.add(RCEntityTypes.MUMMY, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.PAPER)).add(LootItem.lootTableItem(Items.GOLD_NUGGET)).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.75F, 0.1F)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.RAW_GOLD).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 0.1F))))
        );

        this.add(RCEntityTypes.GLACIAL_HUNTER, consumer, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.COD)).add(LootItem.lootTableItem(Items.LEATHER)).apply(LootBuilder.count(0, 1)).apply(LootBuilder.looting(0, 1)))
        );

        this.add(RCEntityTypes.TNT_BARREL, consumer, LootTable.lootTable());
    }

    private <T extends Entity> void add(Supplier<EntityType<T>> type, BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTable.Builder builder) {
        consumer.accept(type.get().getDefaultLootTable(), builder);
    }

    private void add(ResourceLocation type, BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTable.Builder builder) {
        consumer.accept(type, builder);
    }
}
