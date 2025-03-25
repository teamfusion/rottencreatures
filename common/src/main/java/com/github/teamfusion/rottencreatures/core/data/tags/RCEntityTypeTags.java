package com.github.teamfusion.rottencreatures.core.data.tags;

import com.blackgear.platform.common.data.TagRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class RCEntityTypeTags {
    public static final TagRegistry<EntityType<?>> TAGS = TagRegistry.create(Registry.ENTITY_TYPE_REGISTRY, RottenCreatures.MOD_ID);

    public static final TagKey<EntityType<?>> IMMORTAL_IGNORE = TAGS.register("immortal_ignore");
    public static final TagKey<EntityType<?>> IMMORTAL_CANNOT_CONVERT = TAGS.register("immortal_cannot_convert");
}