package com.github.teamfusion.rottencreatures.datagen.common.tags;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class DeferredTagsProvider<T> implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected final DataGenerator.PathProvider pathProvider;
    protected final Registry<T> registry;
    private final Map<ResourceLocation, TagBuilder> builders = Maps.newLinkedHashMap();

    protected DeferredTagsProvider(DataGenerator dataGenerator, Registry<T> registry) {
        this.pathProvider = dataGenerator.createPathProvider(DataGenerator.Target.DATA_PACK, TagManager.getTagDir(registry.key()));
        this.registry = registry;
    }

    @Override
    public final String getName() {
        return "Tags for " + this.registry.key().location();
    }

    protected abstract void addTags();

    @Override
    public void run(CachedOutput output) {
        this.builders.clear();
        this.addTags();
        this.builders.forEach((location, builder) -> {
            List<TagEntry> list = builder.build();
            DataResult<JsonElement> result = TagFile.CODEC.encodeStart(JsonOps.INSTANCE, new TagFile(list, false));
            JsonElement jsonElement = result.getOrThrow(false, LOGGER::error);
            Path path = this.pathProvider.json(location);

            try {
                DataProvider.saveStable(output, jsonElement, path);
            } catch (IOException e) {
                LOGGER.error("Couldn't save tags to {}", path, e);
            }
        });
    }

    protected TagAppender<T> tag(TagKey<T> tagKey) {
        TagBuilder tagBuilder = this.getOrCreateRawBuilder(tagKey);
        return new TagAppender<>(tagBuilder, this.registry);
    }

    protected TagBuilder getOrCreateRawBuilder(TagKey<T> key) {
        return this.builders.computeIfAbsent(key.location(), k -> TagBuilder.create());
    }

    public static class TagAppender<T> {
        private final TagBuilder builder;
        private final Registry<T> registry;

        TagAppender(TagBuilder tagBuilder, Registry<T> registry) {
            this.builder = tagBuilder;
            this.registry = registry;
        }

        public TagAppender<T> add(T object) {
            this.builder.addElement(this.registry.getKey(object));
            return this;
        }

        @SafeVarargs
        public final TagAppender<T> add(ResourceKey<T>... keys) {
            for (ResourceKey<T> key : keys) {
                this.builder.addElement(key.location());
            }

            return this;
        }

        public TagAppender<T> addOptional(ResourceLocation path) {
            this.builder.addOptionalElement(path);
            return this;
        }

        public TagAppender<T> addTag(TagKey<T> tagKey) {
            this.builder.addTag(tagKey.location());
            return this;
        }

        public TagAppender<T> addOptionalTag(ResourceLocation resourceLocation) {
            this.builder.addOptionalTag(resourceLocation);
            return this;
        }

        @SafeVarargs
        public final TagAppender<T> add(T... objects) {
            Stream.of(objects)
                .map(this.registry::getKey)
                .forEach(this.builder::addElement);
            return this;
        }
    }
}