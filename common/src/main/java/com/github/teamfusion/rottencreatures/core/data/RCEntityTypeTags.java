package com.github.teamfusion.rottencreatures.core.data;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.platform.common.TagRegistry;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class RCEntityTypeTags {
    public static final TagRegistry<EntityType<?>> TAGS = TagRegistry.of(Registry.ENTITY_TYPE_REGISTRY, RottenCreatures.MOD_ID);

    public static final TagKey<EntityType<?>> IGNORED_BY_IMMORTAL = TAGS.create("ignored_by_immortal");
    public static final TagKey<EntityType<?>> ZAP_UNCONVERTIBLES = TAGS.create("zap_unconvertibles");
}