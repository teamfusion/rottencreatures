package com.github.teamfusion.rottencreatures.datagen.common;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class EntityLootGenerator extends SimpleFabricLootTableProvider {
    public EntityLootGenerator(FabricDataGenerator gen) {
        super(gen, LootContextParamSets.ENTITY);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.add(RCEntityTypes.FROSTBITTEN, consumer, LootTable.lootTable());
        this.add(RCEntityTypes.SWAMPY, consumer, LootTable.lootTable());
    }

    private <T extends Entity> void add(Supplier<EntityType<T>> type, BiConsumer<ResourceLocation, LootTable.Builder> consumer, LootTable.Builder builder) {
        consumer.accept(type.get().getDefaultLootTable(), builder);
    }
}
