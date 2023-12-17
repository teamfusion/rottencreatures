package com.github.teamfusion.rottencreatures.core.data;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class RCEntityTypeTags {
    public static void init() {}

    public static final TagKey<EntityType<?>> IGNORED_BY_IMMORTAL = create("ignored_by_immortal");
    public static final TagKey<EntityType<?>> ZAP_UNCONVERTIBLES = create("zap_unconvertibles");

    private static TagKey<EntityType<?>> create(String key) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(RottenCreatures.MOD_ID, key));
    }
}