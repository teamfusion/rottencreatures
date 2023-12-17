package com.github.teamfusion.rottencreatures.datagen.common.tags;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.core.data.RCEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public class EntityTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTagGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(RCEntityTypes.FROSTBITTEN.get());
        this.tag(RCEntityTypeTags.IGNORED_BY_IMMORTAL).add(
                EntityType.ZOMBIE_VILLAGER,
                RCEntityTypes.IMMORTAL.get(),
                RCEntityTypes.ZAP.get()
        );
        this.tag(RCEntityTypeTags.ZAP_UNCONVERTIBLES).add(
                EntityType.ZOMBIE_VILLAGER,
                RCEntityTypes.IMMORTAL.get(),
                RCEntityTypes.ZAP.get(),
                RCEntityTypes.DEAD_BEARD.get(),
                RCEntityTypes.GLACIAL_HUNTER.get()
        );
    }
}