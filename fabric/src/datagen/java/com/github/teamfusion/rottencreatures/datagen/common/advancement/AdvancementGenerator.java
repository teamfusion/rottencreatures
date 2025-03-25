package com.github.teamfusion.rottencreatures.datagen.common.advancement;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.registries.RCPotions;
import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import com.google.common.collect.Sets;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.BrewedPotionTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class AdvancementGenerator implements DataProvider {
    private final DataGenerator generator;
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

    public AdvancementGenerator(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(CachedOutput output) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> ids = Sets.newHashSet();
        Consumer<Advancement> consumer = advancement -> {
            ResourceLocation id = advancement.getId();
            if (!ids.add(id)) {
                throw new IllegalStateException("Duplicate advancement " + id);
            }

            Path outputPath = path.resolve("data/" + id.getNamespace() + "/advancements/" + id.getPath() + ".json");
            try {
                DataProvider.saveStable(output, advancement.deconstruct().serializeToJson(), outputPath);
            } catch (Exception e) {
                RottenCreatures.LOGGER.error("Couldn't save advancement {}", outputPath, e);
            }
        };

        accept(consumer);
    }

    private void accept(Consumer<Advancement> exporter) {
        Advancement root = this.add(exporter, RottenCreatures.resource("root"),
            this.addMobsToKill(Advancement.Builder.advancement())
                .display(
                    Items.ROTTEN_FLESH,
                    Component.translatable(LangConstants.ADVANCEMENT_ROOT_TITLE),
                    Component.translatable(LangConstants.ADVANCEMENT_ROOT_DESCRIPTION),
                    new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                    FrameType.TASK,
                    false,
                    false,
                    false
                )
                .requirements(RequirementsStrategy.OR)
        );

        this.add(exporter, RottenCreatures.resource("kill_all_rot"),
            this.addMobsToKill(Advancement.Builder.advancement())
                .parent(root)
                .display(
                    Items.ROTTEN_FLESH,
                    Component.translatable(LangConstants.ADVANCEMENT_KILL_ALL_ROT_TITLE),
                    Component.translatable(LangConstants.ADVANCEMENT_KILL_ALL_ROT_DESCRIPTION),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .requirements(RequirementsStrategy.AND)
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
                    FrameType.CHALLENGE,
                    true,
                    true,
                    false
                )
                .requirements(RequirementsStrategy.AND)
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
                    FrameType.CHALLENGE,
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
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("freeze_potion", new BrewedPotionTrigger.TriggerInstance(EntityPredicate.Composite.ANY, RCPotions.FREEZE.get()))
                .rewards(AdvancementRewards.Builder.experience(20))
        );
    }

    private Advancement.Builder addMobsToKill(Advancement.Builder builder) {
        for (EntityType<?> mob : MOBS_TO_KILL) {
            builder.addCriterion(Registry.ENTITY_TYPE.getKey(mob).toString(), KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(mob)));
        }

        return builder;
    }

    private Advancement add(Consumer<Advancement> exporter, ResourceLocation id, Advancement.Builder builder) {
        Advancement advancement = builder.build(id);
        exporter.accept(advancement);
        return advancement;
    }

    @Override
    public String getName() {
        return "Advancements";
    }
}