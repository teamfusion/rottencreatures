package com.github.teamfusion.rottencreatures.data.server.tags;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.core.data.tags.RCEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class EntityTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(RCEntityTypeTags.IMMORTAL_IGNORE)
            .add(RCEntityTypes.IMMORTAL.get())
            .add(RCEntityTypes.ZAP.get())
            .add(EntityType.ZOMBIE_VILLAGER);
        this.getOrCreateTagBuilder(RCEntityTypeTags.IMMORTAL_CANNOT_CONVERT)
            .add(RCEntityTypes.IMMORTAL.get())
            .add(RCEntityTypes.ZAP.get())
            .add(RCEntityTypes.DEAD_BEARD.get())
            .add(EntityType.ZOMBIE_VILLAGER);
        this.getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
            .add(RCEntityTypes.FROSTBITTEN.get());
        this.getOrCreateTagBuilder(RCEntityTypeTags.CAN_CONVERT_TO_BURNED)
            .add(EntityType.ZOMBIE)
            .add(EntityType.ZOMBIE_VILLAGER)
            .add(EntityType.DROWNED)
            .add(EntityType.HUSK)
            .add(RCEntityTypes.FROSTBITTEN.get())
            .add(RCEntityTypes.SWAMPY.get())
            .add(RCEntityTypes.UNDEAD_MINER.get())
            .add(RCEntityTypes.MUMMY.get())
            .add(RCEntityTypes.ZOMBIE_LACKEY.get())
            .add(RCEntityTypes.GLACIAL_HUNTER.get());
        this.getOrCreateTagBuilder(RCEntityTypeTags.CAN_CONVERT_TO_FROSTBITTEN)
            .add(EntityType.ZOMBIE)
            .add(EntityType.ZOMBIE_VILLAGER)
            .add(EntityType.DROWNED)
            .add(EntityType.HUSK)
            .add(RCEntityTypes.SWAMPY.get())
            .add(RCEntityTypes.UNDEAD_MINER.get())
            .add(RCEntityTypes.MUMMY.get())
            .add(RCEntityTypes.ZOMBIE_LACKEY.get());
    }
}