package com.github.teamfusion.rottencreatures.datagen.common.tags;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.EntityTypeTags;

public class EntityTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
    public EntityTagGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(RCEntityTypes.FROSTBITTEN.get());
    }
}