package com.github.teamfusion.rottencreatures.data.server.advancement;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.registries.RCPotions;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementGenerator extends FabricAdvancementProvider {
    private static final EntityType<?>[] MOBS_TO_KILL = {
        RCEntityTypes.BURNED.get(),
        RCEntityTypes.FROSTBITTEN.get(),
        RCEntityTypes.SWAMPY.get(),
        RCEntityTypes.UNDEAD_MINER.get(),
        RCEntityTypes.MUMMY.get(),
        RCEntityTypes.GLACIAL_HUNTER.get(),
        RCEntityTypes.DEAD_BEARD.get(),
        RCEntityTypes.ZOMBIE_LACKEY.get(),
        RCEntityTypes.SKELETON_LACKEY.get(),
        RCEntityTypes.IMMORTAL.get(),
        RCEntityTypes.ZAP.get()
    };

    public AdvancementGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider provider, Consumer<AdvancementHolder> exporter) {
        AdvancementHolder root = this.add(exporter, RottenCreatures.resource("root"),
            this.addMobsToKill(Advancement.Builder.advancement())
                .display(
                    Items.ROTTEN_FLESH,
                    Component.translatable(LangConstants.ADVANCEMENT_ROOT_TITLE),
                    Component.translatable(LangConstants.ADVANCEMENT_ROOT_DESCRIPTION),
                    ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/stone.png"),
                    AdvancementType.TASK,
                    false,
                    false,
                    false
                )
                .requirements(AdvancementRequirements.Strategy.OR)
        );

        this.add(exporter, RottenCreatures.resource("kill_all_rot"),
            this.addMobsToKill(Advancement.Builder.advancement())
                .parent(root)
                .display(
                    Items.ROTTEN_FLESH,
                    Component.translatable(LangConstants.ADVANCEMENT_KILL_ALL_ROT_TITLE),
                    Component.translatable(LangConstants.ADVANCEMENT_KILL_ALL_ROT_DESCRIPTION),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
                )
                .requirements(AdvancementRequirements.Strategy.AND)
                .rewards(AdvancementRewards.Builder.experience(50))
        );
        this.add(exporter, RottenCreatures.resource("he_is_a_pirate"),
            Advancement.Builder.advancement()
                .parent(root)
                .display(
                    RCBlocks.TREASURE_CHEST.get(),
                    Component.translatable(LangConstants.ADVANCEMENT_HE_IS_A_PIRATE_TITLE),
                    Component.translatable(LangConstants.ADVANCEMENT_HE_IS_A_PIRATE_DESCRIPTION),
                    null,
                    AdvancementType.CHALLENGE,
                    true,
                    true,
                    false
                )
                .requirements(AdvancementRequirements.Strategy.AND)
                .addCriterion("killed_dead_beard", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(RCEntityTypes.DEAD_BEARD.get())))
                .addCriterion("get_treasure_chest", InventoryChangeTrigger.TriggerInstance.hasItems(RCBlocks.TREASURE_CHEST.get()))
                .rewards(AdvancementRewards.Builder.experience(20))
        );
        this.add(exporter, RottenCreatures.resource("shocking_encounter"),
            Advancement.Builder.advancement()
                .parent(root)
                .display(
                    Blocks.LIGHTNING_ROD,
                    Component.translatable(LangConstants.ADVANCEMENT_HIGH_TENSION_TITLE),
                    Component.translatable(LangConstants.ADVANCEMENT_HIGH_TENSION_DESCRIPTION),
                    null,
                    AdvancementType.CHALLENGE,
                    true,
                    true,
                    false
                )
                .addCriterion("killed_immortal", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(RCEntityTypes.IMMORTAL.get())))
                .rewards(AdvancementRewards.Builder.experience(20))
        );
        this.add(exporter, RottenCreatures.resource("mr_freeze"),
            Advancement.Builder.advancement()
                .parent(root)
                .display(
                    RCItems.FROZEN_ROTTEN_FLESH.get(),
                    Component.translatable(LangConstants.ADVANCEMENT_MR_FREEZE_TITLE),
                    Component.translatable(LangConstants.ADVANCEMENT_MR_FREEZE_DESCRIPTION),
                    null,
                    AdvancementType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("freeze_potion", CriteriaTriggers.BREWED_POTION.createCriterion(new BrewedPotionTrigger.TriggerInstance(Optional.empty(), Optional.of(RCPotions.FREEZE.getHolder().get()))))
                .rewards(AdvancementRewards.Builder.experience(20))
        );
    }

    private Advancement.Builder addMobsToKill(Advancement.Builder builder) {
        for (EntityType<?> mob : MOBS_TO_KILL) {
            builder.addCriterion(BuiltInRegistries.ENTITY_TYPE.getKey(mob).toString(), KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(mob)));
        }

        return builder;
    }

    private AdvancementHolder add(Consumer<AdvancementHolder> exporter, ResourceLocation id, Advancement.Builder builder) {
        AdvancementHolder advancement = builder.build(id);
        exporter.accept(advancement);
        return advancement;
    }
}