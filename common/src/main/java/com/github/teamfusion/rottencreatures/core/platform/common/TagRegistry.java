package com.github.teamfusion.rottencreatures.core.platform.common;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class TagRegistry<T> {
    protected final ResourceKey<? extends Registry<T>> registry;
    protected final String modId;

    protected TagRegistry(ResourceKey<? extends Registry<T>> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
    }

    public static <T> TagRegistry<T> of(ResourceKey<? extends Registry<T>> registry, String modId) {
        return new TagRegistry<>(registry, modId);
    }

    public TagKey<T> create(String key) {
        return TagKey.create(this.registry, new ResourceLocation(this.modId, key));
    }

    public void register() {
        RottenCreatures.LOGGER.info("Initializing tags of type: " + this.registry.location().getPath());
    }
}