package com.github.teamfusion.rottencreatures.data.server.loot;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.core.data.loot.LootBuilder;
import com.github.teamfusion.rottencreatures.core.data.loot.RCLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class EntityLootGenerator extends SimpleFabricLootTableProvider {
    private final HolderLookup.Provider registries;

    public EntityLootGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup, LootContextParamSets.ENTITY);
        this.registries = registryLookup.resultNow();
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        this.add(RCEntityTypes.BURNED, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(RCItems.MAGMA_ROTTEN_FLESH.get())
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(0, 2)
                .add(LootBuilder.entry(Items.MAGMA_BLOCK))
                .add(LootBuilder.entry(Items.MAGMA_CREAM)
                    .when(LootBuilder.chance(0.25F)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCLootTables.BURNED_OBSIDIAN, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.OBSIDIAN)
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCEntityTypes.FROSTBITTEN, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(RCItems.FROZEN_ROTTEN_FLESH.get())
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.IRON_INGOT))
                .add(LootBuilder.entry(Items.CARROT))
                .add(LootBuilder.entry(Items.POTATO))
                .when(LootBuilder.killedByPlayer())
                .when(LootBuilder.chanceWithLooting(this.registries, 0.025F, 0.01F))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.SNOWBALL))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
        );

        this.add(RCEntityTypes.SWAMPY, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.BROWN_DYE))
                .add(LootBuilder.entry(Items.POISONOUS_POTATO))
                .when(LootBuilder.chanceWithLooting(this.registries, 0.5F, 0.01F))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCLootTables.UNDEAD_DIAMOND_MINER, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(0, 3)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2)))
                .add(LootBuilder.entry(Items.COBBLESTONE)
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1, 2)
                .add(LootBuilder.entry(Items.RAW_GOLD).
                    apply(LootBuilder.count(1, 5)))
                .add(LootBuilder.entry(Items.DIAMOND)
                    .apply(LootBuilder.count(1)))
                .when(LootBuilder.killedByPlayer())
            )
            .withPool(LootBuilder.rolls(1, 2)
                .add(LootBuilder.entry(Items.COAL)
                    .apply(LootBuilder.count(0, 2)))
                .add(LootBuilder.entry(Items.RAW_IRON)
                    .apply(LootBuilder.count(0, 2)))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCLootTables.UNDEAD_IRON_MINER, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(0, 3)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2)))
                .add(LootBuilder.entry(Items.COBBLESTONE)
                    .apply(LootBuilder.count(0, 2)))
                .add(LootBuilder.entry(Items.COAL)
                    .apply(LootBuilder.count(0, 2))
                    .when(LootBuilder.killedByPlayer()))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1, 2)
                .add(LootBuilder.entry(Items.RAW_IRON))
                .add(LootBuilder.entry(Items.RAW_GOLD))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCLootTables.UNDEAD_STONE_MINER, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(0, 2)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2)))
                .add(LootBuilder.entry(Items.COBBLESTONE)
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.COAL))
                .add(LootBuilder.entry(Items.RAW_IRON))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCLootTables.UNDEAD_GOLD_MINER, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(0, 2)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2)))
                .add(LootBuilder.entry(Items.COBBLESTONE)
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.RAW_GOLD))
                .add(LootBuilder.entry(Items.GOLD_INGOT))
                .apply(LootBuilder.count(0, 2))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCEntityTypes.MUMMY, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(0, 2)
                .add(LootBuilder.entry(Items.PAPER)
                    .when(LootBuilder.chance(0.75F)))
                .add(LootBuilder.entry(Items.GOLD_NUGGET)
                    .when(LootBuilder.chance(0.75F)))
                .add(LootBuilder.entry(Items.RAW_GOLD)
                    .when(LootBuilder.chance(0.25F)))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCEntityTypes.SCARAB, output, LootTable.lootTable());

        this.add(RCEntityTypes.GLACIAL_HUNTER, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 2))
                    .apply(LootBuilder.looting(this.registries, 0, 1)))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.COD))
                .add(LootBuilder.entry(Items.LEATHER))
                .apply(LootBuilder.looting(this.registries, 0, 1))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCEntityTypes.HUNTER_WOLF, output, LootTable.lootTable());

        this.add(RCEntityTypes.DEAD_BEARD, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 1)))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.IRON_INGOT)
                    .apply(LootBuilder.count(1, 5))
                    .when(LootBuilder.chance(0.9F)))
                .add(LootBuilder.entry(Items.EMERALD)
                    .apply(LootBuilder.count(1, 5))
                    .when(LootBuilder.chance(0.15F)))
                .add(LootBuilder.entry(Items.DIAMOND)
                    .apply(LootBuilder.count(1))
                    .when(LootBuilder.chance(0.05F)))
                .add(LootBuilder.entry(Items.EXPERIENCE_BOTTLE)
                    .apply(LootBuilder.count(1)))
                .when(LootBuilder.killedByPlayer())
            )
            .withPool(LootBuilder.rolls(1, 10)
                .add(LootBuilder.entry(Items.IRON_NUGGET)
                    .apply(LootBuilder.count(1, 10))
                    .when(LootBuilder.chance(0.5F)))
                .add(LootBuilder.entry(Items.LAPIS_LAZULI)
                    .apply(LootBuilder.count(1, 10))
                    .when(LootBuilder.chance(0.2F)))
                .add(LootBuilder.entry(Items.GOLD_NUGGET)
                    .apply(LootBuilder.count(1, 10))
                    .when(LootBuilder.chance(0.2F)))
                .when(LootBuilder.killedByPlayer())
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.PAPER)
                    .apply(LootBuilder.count(1, 10))
                    .when(LootBuilder.chance(0.2F)))
                .add(LootBuilder.entry(Items.FEATHER)
                    .apply(LootBuilder.count(1, 5))
                    .when(LootBuilder.chance(0.1F)))
                .add(LootBuilder.entry(Items.BOOK)
                    .apply(LootBuilder.count(1, 5))
                    .when(LootBuilder.chance(0.05F)))
                .add(LootBuilder.entry(Items.CLOCK)
                    .when(LootBuilder.chance(0.01F)))
                .add(LootBuilder.entry(Items.COMPASS)
                    .when(LootBuilder.chance(0.01F)))
                .add(LootBuilder.entry(Items.MAP)
                    .when(LootBuilder.chance(0.01F)))
                .when(LootBuilder.killedByPlayer())
            )
        );

        this.add(RCLootTables.TREASURE_CHEST, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.TOTEM_OF_UNDYING)
                    .when(LootBuilder.chanceWithLooting(this.registries, 0.2F, 0.05F)))
            )
            .withPool(LootBuilder.rolls(2, 10)
                .add(LootBuilder.entry(Items.DIAMOND)
                    .apply(LootBuilder.count(1, 3))
                    .setWeight(15))
                .add(LootBuilder.entry(Items.EMERALD)
                    .apply(LootBuilder.count(1, 3))
                    .setWeight(25))
                .add(LootBuilder.entry(Items.GOLD_INGOT)
                    .apply(LootBuilder.count(2, 5))
                    .setWeight(30))
                .add(LootBuilder.entry(Items.IRON_INGOT)
                    .apply(LootBuilder.count(2, 5))
                    .setWeight(30))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(2, 10)
                .add(LootBuilder.entry(Items.GOLD_NUGGET)
                    .apply(LootBuilder.count(3, 5))
                    .setWeight(40))
                .add(LootBuilder.entry(Items.IRON_NUGGET)
                    .apply(LootBuilder.count(3, 5))
                    .setWeight(60))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
        );

        this.add(RCEntityTypes.ZOMBIE_LACKEY, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH))
                .apply(LootBuilder.count(0, 2))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.COPPER_INGOT))
                .when(LootBuilder.killedByPlayer())
                .when(LootBuilder.chanceWithLooting(this.registries, 0.11F, 0.02F))
            )
        );

        this.add(RCEntityTypes.SKELETON_LACKEY, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.BONE))
                .apply(LootBuilder.count(0, 2))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
        );

        this.add(RCEntityTypes.IMMORTAL, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(0, 2)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH))
                .add(LootBuilder.entry(Items.BONE))
                .apply(LootBuilder.count(0, 2))
                .apply(LootBuilder.looting(this.registries, 0, 1))
            )
        );

        this.add(RCEntityTypes.ZAP, output, LootTable.lootTable()
            .withPool(LootBuilder.rolls(1)
                .add(LootBuilder.entry(Items.ROTTEN_FLESH)
                    .apply(LootBuilder.count(0, 1))
                    .apply(LootBuilder.looting(this.registries, 0, 1)))
            )
            .withPool(LootBuilder.rolls(0, 2)
                .add(LootBuilder.entry(Items.IRON_INGOT))
                .add(LootBuilder.entry(Items.CARROT))
                .add(LootBuilder.entry(Items.POTATO)
                    .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot())))
                .when(LootBuilder.killedByPlayer())
                .when(LootBuilder.chanceWithLooting(this.registries,0.025F, 0.01F))
            )
        );

        this.add(RCEntityTypes.TNT_BARREL, output, LootTable.lootTable());
    }

    private <T extends Entity> void add(Supplier<EntityType<T>> type, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer, LootTable.Builder builder) {
        consumer.accept(type.get().getDefaultLootTable(), builder);
    }

    private void add(ResourceKey<LootTable> type, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer, LootTable.Builder builder) {
        consumer.accept(type, builder);
    }


    private AnyOfCondition.Builder shouldSmeltLoot() {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return AnyOfCondition.anyOf(
            LootItemEntityPropertyCondition.hasProperties(
                LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))
            ),
            LootItemEntityPropertyCondition.hasProperties(
                LootContext.EntityTarget.DIRECT_ATTACKER,
                EntityPredicate.Builder.entity()
                    .equipment(
                        EntityEquipmentPredicate.Builder.equipment()
                            .mainhand(
                                ItemPredicate.Builder.item()
                                    .withSubPredicate(
                                        ItemSubPredicates.ENCHANTMENTS,
                                        ItemEnchantmentsPredicate.enchantments(
                                            List.of(new EnchantmentPredicate(registryLookup.getOrThrow(EnchantmentTags.SMELTS_LOOT), MinMaxBounds.Ints.ANY))
                                        )
                                    )
                            )
                    )
            )
        );
    }
}