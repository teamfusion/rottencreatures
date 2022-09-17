package com.github.teamfusion.rottencreatures.datagen.common;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class EntityLootGenerator extends EntityLoot {
    private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER);
    private final Map<ResourceLocation, LootTable.Builder> table = Maps.newHashMap();

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
//        this.add(RCEntityTypes.BURNED.get(), LootTable.lootTable().apply(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add((LootPoolEntryContainer.Builder<?>)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add(LootItem.lootTableItem(Items.IRON_INGOT)).add(LootItem.lootTableItem(Items.CARROT)).add((LootPoolEntryContainer.Builder<?>)LootItem.lootTableItem(Items.POTATO).apply((LootItemFunction.Builder) SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025f, 0.01f))));
        LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add(LootItem.lootTableItem(Items.ROTTEN_FLESH).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f)).add(LootItem.lootTableItem(Items.IRON_INGOT)).add(LootItem.lootTableItem(Items.CARROT)).when(LootItemKilledByPlayerCondition.killedByPlayer()).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025f, 0.01f)));
        this.add(RCEntityTypes.FROSTBITTEN.get(), LootTable.lootTable());
        this.add(RCEntityTypes.SWAMPY.get(), LootTable.lootTable());

        HashSet<ResourceLocation> lootTables = Sets.newHashSet();
        for (EntityType<?> entity : Registry.ENTITY_TYPE) {
            ResourceLocation loot = entity.getDefaultLootTable();
            if (!loot.getNamespace().equals(RottenCreatures.MOD_ID)) return;
            if (SPECIAL_LOOT_TABLE_TYPES.contains(entity) || entity.getCategory() != MobCategory.MISC) {
                if (loot == BuiltInLootTables.EMPTY || !lootTables.add(loot)) continue;
                LootTable.Builder builder = this.table.remove(loot);
                if (builder == null) throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", loot, Registry.ENTITY_TYPE.getKey(entity)));
                consumer.accept(loot, builder);
                continue;
            }

            if (loot == BuiltInLootTables.EMPTY || this.table.remove(loot) == null) continue;
            throw new IllegalStateException(String.format("Weird loottable '%s' for '%s', not a LivingEntity so should not have loot", loot, Registry.ENTITY_TYPE.getKey(entity)));
        }
        this.table.forEach(consumer);
    }

    private void add(EntityType<?> type, LootTable.Builder builder) {
        this.add(type.getDefaultLootTable(), builder);
    }

    private void add(ResourceLocation location, LootTable.Builder builder) {
        this.table.put(location, builder);
    }
}