package com.github.teamfusion.platform.common.worldgen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class BiomeModifier {
    private static final Map<Consumer<BiomeWriter>, ResourceKey<Biome>[]> BIOME_FEATURES = new ConcurrentHashMap<>();
    private static final Map<Consumer<BiomeWriter>, Biome.BiomeCategory[]> CATEGORY_FEATURES = new ConcurrentHashMap<>();
    public static final BiomeModifier INSTANCE = new BiomeModifier();

    @ExpectPlatform
    public static void setup() {
        throw new AssertionError();
    }

    public void register(BiomeWriter writer) {
        BIOME_FEATURES.forEach(writer::add);
        CATEGORY_FEATURES.forEach(writer::add);
    }

    @SafeVarargs
    public static void add(Consumer<BiomeWriter> writer, ResourceKey<Biome>... biomes) {
        BIOME_FEATURES.put(writer, biomes);
    }

    public static void add(Consumer<BiomeWriter> writer, Biome.BiomeCategory... categories) {
        CATEGORY_FEATURES.put(writer, categories);
    }
}