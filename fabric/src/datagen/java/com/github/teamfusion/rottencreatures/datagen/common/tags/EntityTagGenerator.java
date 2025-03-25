package com.github.teamfusion.rottencreatures.datagen.common.tags;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.core.data.tags.RCEntityTypeTags;
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
        this.tag(RCEntityTypeTags.IMMORTAL_IGNORE)
            .add(RCEntityTypes.IMMORTAL.get())
            .add(RCEntityTypes.ZAP.get())
            .add(EntityType.ZOMBIE_VILLAGER);
        this.tag(RCEntityTypeTags.IMMORTAL_CANNOT_CONVERT)
            .add(RCEntityTypes.IMMORTAL.get())
            .add(RCEntityTypes.ZAP.get())
            .add(RCEntityTypes.DEAD_BEARD.get())
            .add(EntityType.ZOMBIE_VILLAGER);
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
            .add(RCEntityTypes.FROSTBITTEN.get());
    }
}