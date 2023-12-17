package com.github.teamfusion.rottencreatures.core.platform.common.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.BiConsumer;

public abstract class BiomeWriter {
    public void add(BiConsumer<BiomeWriter, BiomeContext> modifier) {
        modifier.accept(this, this.context());
    }

    public abstract ResourceLocation name();

    public abstract BiomeContext context();

    public abstract void addFeature(GenerationStep.Decoration step, Holder<PlacedFeature> feature);

    public abstract void addSpawn(MobCategory category, EntityType<?> type, int weight, int minGroup, int maxGroup);
}