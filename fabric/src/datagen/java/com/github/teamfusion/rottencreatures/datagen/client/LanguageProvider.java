package com.github.teamfusion.rottencreatures.datagen.client;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public final class LanguageProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<String, String> data = new TreeMap<>();
    private final DataGenerator generator;

    public LanguageProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        this.addTranslations();
        Path path = this.generator.getOutputFolder().resolve("assets/rottencreatures/lang/en_us.json");
        DataProvider.save(GSON, cache, GSON.toJsonTree(this.data), path);
    }

    @Override
    public String getName() {
        return "Language: en_us";
    }

    private void addTranslations() {
        // Entities
        this.add(RCEntityTypes.BURNED.get(), "Burned");
        this.add(RCEntityTypes.FROSTBITTEN.get(), "Frostbitten");
        this.add(RCEntityTypes.SWAMPY.get(), "Swampy");
        this.add(RCEntityTypes.TNT_BARREL.get(), "Primed TNT Barrel");

        // Blocks
        this.add(RCBlocks.TNT_BARREL.get(), "TNT Barrel");

        // Items
        this.add(RCItems.BURNED_SPAWN_EGG.get(), "Burned Spawn Egg");
        this.add(RCItems.FROSTBITTEN_SPAWN_EGG.get(), "Frostbitten Spawn Egg");
        this.add(RCItems.SWAMPY_SPAWN_EGG.get(), "Swampy Spawn Egg");
        this.add(RCItems.MAGMA_ROTTEN_FLESH.get(), "Magma Rotten Flesh");
        this.add(RCItems.FROZEN_ROTTEN_FLESH.get(), "Frozen Rotten Flesh");

        // Misc
        this.add("itemGroup.rottencreatures.rottencreatures", "Rotten Creatures");
    }

    private void add(Block entry, String name) {
        this.add(entry.getDescriptionId(), name);
    }

    private void add(Item entry, String name) {
        this.add(entry.getDescriptionId(), name);
    }

    private void add(EntityType<?> entry, String name) {
        this.add(entry.getDescriptionId(), name);
    }

    private void add(MobEffect entry, String name) {
        this.add(entry.getDescriptionId(), name);
    }

    private void add(String key, String value) {
        if (this.data.put(key, value) != null) throw new IllegalStateException("Duplicate translation key " + key);
    }
}
