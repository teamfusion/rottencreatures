package com.github.teamfusion.rottencreatures.datagen.common.loot;

import com.github.teamfusion.rottencreatures.common.LootBuilder;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class EntityLootGenerator extends SimpleFabricLootTableProvider {
    private static final EntityPredicate.Builder ENTITY_ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true).build());
    public EntityLootGenerator(FabricDataGenerator gen) {
        super(gen, LootContextParamSets.ENTITY);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.add(RCEntityTypes.BURNED, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(RCItems.MAGMA_ROTTEN_FLESH.get()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(0, 2)
                        .add(LootBuilder.entry(Items.MAGMA_BLOCK))
                        .add(LootBuilder.entry(Items.MAGMA_CREAM).when(LootBuilder.chance(0.25F)))
                        .apply(LootBuilder.looting(0, 1))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(Burned.OBSIDIAN_LOOT, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.OBSIDIAN).apply(LootBuilder.count(0, 2)))
                        .apply(LootBuilder.looting(0, 1))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(RCEntityTypes.FROSTBITTEN, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(RCItems.FROZEN_ROTTEN_FLESH.get()).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.IRON_INGOT))
                        .add(LootBuilder.entry(Items.CARROT))
                        .add(LootBuilder.entry(Items.POTATO))
                        .when(LootBuilder.killedByPlayer())
                        .when(LootBuilder.chanceWithLooting(0.025F, 0.01F))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.SNOWBALL).apply(LootBuilder.looting(0, 1)))
                )
        );

        this.add(RCEntityTypes.SWAMPY, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.BROWN_DYE))
                        .add(LootBuilder.entry(Items.POISONOUS_POTATO))
                        .when(LootBuilder.killedByPlayer())
                        .when(LootBuilder.chanceWithLooting(0.5F, 0.01F))
                )
        );

        this.add(UndeadMiner.DIAMOND_LOOT, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(0, 3)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)))
                        .add(LootBuilder.entry(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)))
                        .apply(LootBuilder.looting(0, 1))
                )
                .withPool(LootBuilder.rolls(1, 2)
                        .add(LootBuilder.entry(Items.RAW_GOLD).apply(LootBuilder.count(1, 5)))
                        .add(LootBuilder.entry(Items.DIAMOND).apply(LootBuilder.count(1)))
                        .when(LootBuilder.killedByPlayer())
                )
                .withPool(LootBuilder.rolls(1, 2)
                        .add(LootBuilder.entry(Items.COAL).apply(LootBuilder.count(0, 2)))
                        .add(LootBuilder.entry(Items.RAW_IRON).apply(LootBuilder.count(0, 2)))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(UndeadMiner.IRON_LOOT, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(0, 3)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)))
                        .add(LootBuilder.entry(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)))
                        .add(LootBuilder.entry(Items.COAL).apply(LootBuilder.count(0, 2)).when(LootBuilder.killedByPlayer()))
                        .apply(LootBuilder.looting(0, 1))
                )
                .withPool(LootBuilder.rolls(1, 2)
                        .add(LootBuilder.entry(Items.RAW_IRON))
                        .add(LootBuilder.entry(Items.RAW_GOLD))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(UndeadMiner.STONE_LOOT, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(0, 2)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)))
                        .add(LootBuilder.entry(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)))
                        .apply(LootBuilder.looting(0, 1))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.COAL))
                        .add(LootBuilder.entry(Items.RAW_IRON))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(UndeadMiner.GOLD_LOOT, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(0, 2)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)))
                        .add(LootBuilder.entry(Items.COBBLESTONE).apply(LootBuilder.count(0, 2)))
                        .apply(LootBuilder.looting(0, 1))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.RAW_GOLD))
                        .add(LootBuilder.entry(Items.GOLD_INGOT))
                        .apply(LootBuilder.count(0, 2))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(RCEntityTypes.MUMMY, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(0, 2)
                        .add(LootBuilder.entry(Items.PAPER).when(LootBuilder.chance(0.75F)))
                        .add(LootBuilder.entry(Items.GOLD_NUGGET).when(LootBuilder.chance(0.75F)))
                        .add(LootBuilder.entry(Items.RAW_GOLD).when(LootBuilder.chance(0.25F)))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(RCEntityTypes.SCARAB, consumer, LootTable.lootTable());
        this.add(RCEntityTypes.FLYING_SCARAB, consumer, LootTable.lootTable());

        this.add(RCEntityTypes.GLACIAL_HUNTER, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 2)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.COD))
                        .add(LootBuilder.entry(Items.LEATHER))
                        .apply(LootBuilder.looting(0, 1))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(RCEntityTypes.HUNTER_WOLF, consumer, LootTable.lootTable());

        this.add(RCEntityTypes.DEAD_BEARD, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 1)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(RCItems.TREASURE_CHEST.get()).apply(LootBuilder.count(1))).when(LootBuilder.killedByPlayer())
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.IRON_INGOT).apply(LootBuilder.count(1, 5)).when(LootBuilder.chance(0.9F)))
                        .add(LootBuilder.entry(Items.EMERALD).apply(LootBuilder.count(1, 5)).when(LootBuilder.chance(0.15F)))
                        .add(LootBuilder.entry(Items.DIAMOND).apply(LootBuilder.count(1)).when(LootBuilder.chance(0.05F)))
                        .add(LootBuilder.entry(Items.EXPERIENCE_BOTTLE).apply(LootBuilder.count(1)))
                        .when(LootBuilder.killedByPlayer())
                )
                .withPool(LootBuilder.rolls(1, 10)
                        .add(LootBuilder.entry(Items.IRON_NUGGET).apply(LootBuilder.count(1, 10)).when(LootBuilder.chance(0.5F)))
                        .add(LootBuilder.entry(Items.LAPIS_LAZULI).apply(LootBuilder.count(1, 10)).when(LootBuilder.chance(0.2F)))
                        .add(LootBuilder.entry(Items.GOLD_NUGGET).apply(LootBuilder.count(1, 10)).when(LootBuilder.chance(0.2F)))
                        .when(LootBuilder.killedByPlayer())
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.PAPER).apply(LootBuilder.count(1, 10)).when(LootBuilder.chance(0.2F)))
                        .add(LootBuilder.entry(Items.FEATHER).apply(LootBuilder.count(1, 5)).when(LootBuilder.chance(0.1F)))
                        .add(LootBuilder.entry(Items.BOOK).apply(LootBuilder.count(1, 5)).when(LootBuilder.chance(0.05F)))
                        .add(LootBuilder.entry(Items.CLOCK).when(LootBuilder.chance(0.01F)))
                        .add(LootBuilder.entry(Items.COMPASS).when(LootBuilder.chance(0.01F)))
                        .add(LootBuilder.entry(Items.MAP).when(LootBuilder.chance(0.01F)))
                        .when(LootBuilder.killedByPlayer())
                )
        );

        this.add(RCEntityTypes.ZOMBIE_LACKEY, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH))
                        .apply(LootBuilder.count(0, 2))
                        .apply(LootBuilder.looting(0, 1))
                )
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.COPPER_INGOT))
                        .when(LootBuilder.killedByPlayer())
                        .when(LootBuilder.chanceWithLooting(0.11F, 0.02F))
                )
        );

        this.add(RCEntityTypes.SKELETON_LACKEY, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.BONE))
                        .apply(LootBuilder.count(0, 2))
                        .apply(LootBuilder.looting(0, 1))
                )
        );

        this.add(RCEntityTypes.IMMORTAL, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(0, 2)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH))
                        .add(LootBuilder.entry(Items.BONE))
                        .apply(LootBuilder.count(0, 2))
                        .apply(LootBuilder.looting(0, 1))
                )
        );

        this.add(RCEntityTypes.ZAP, consumer, LootTable.lootTable()
                .withPool(LootBuilder.rolls(1)
                        .add(LootBuilder.entry(Items.ROTTEN_FLESH).apply(LootBuilder.count(0, 1)).apply(LootBuilder.looting(0, 1)))
                )
                .withPool(LootBuilder.rolls(0, 2)
                        .add(LootBuilder.entry(Items.IRON_INGOT))
                        .add(LootBuilder.entry(Items.CARROT))
                        .add(LootBuilder.entry(Items.POTATO).apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
                        .when(LootBuilder.killedByPlayer())
                        .when(LootBuilder.chanceWithLooting(0.025F, 0.01F))
                )
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
