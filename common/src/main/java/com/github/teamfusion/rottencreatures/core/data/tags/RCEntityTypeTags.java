package com.github.teamfusion.rottencreatures.core.data.tags;

import com.blackgear.platform.common.data.TagRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class RCEntityTypeTags {
    public static final TagRegistry<EntityType<?>> TAGS = TagRegistry.create(Registries.ENTITY_TYPE, RottenCreatures.MOD_ID);

    public static final TagKey<EntityType<?>> IMMORTAL_IGNORE = TAGS.register("immortal_ignore");
    public static final TagKey<EntityType<?>> IMMORTAL_CANNOT_CONVERT = TAGS.register("immortal_cannot_convert");
    public static final TagKey<EntityType<?>> CAN_CONVERT_TO_BURNED = TAGS.register("can_convert_to_burden");
    public static final TagKey<EntityType<?>> CAN_CONVERT_TO_FROSTBITTEN = TAGS.register("can_convert_to_frostbitten");
}