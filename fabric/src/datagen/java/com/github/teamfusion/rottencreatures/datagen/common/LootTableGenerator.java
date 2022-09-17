package com.github.teamfusion.rottencreatures.datagen.common;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTableGenerator implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> tables = ImmutableList.of(Pair.of(BlockLootGenerator::new, LootContextParamSets.BLOCK), Pair.of(EntityLootGenerator::new, LootContextParamSets.ENTITY));

    public LootTableGenerator(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(HashCache cache) {
        Path output = this.generator.getOutputFolder();
        Map<ResourceLocation, LootTable> tables = Maps.newHashMap();

        this.tables.forEach(table -> table.getFirst().get().accept((location, builder) -> {
            if (tables.put(location, builder.setParamSet(table.getSecond()).build()) != null) {
                throw new IllegalStateException("Duplicate loot table" + location);
            }
        }));

        tables.forEach((location, lootTable) -> {
            Path path = createPath(output, location);
            try {
                DataProvider.save(GSON, cache, GSON.toJsonTree(lootTable), path);
            } catch (IOException exception) {
                RottenCreatures.LOGGER.error("Couldn't save loot table {}", path, exception);
            }
        });
    }

    private static Path createPath(Path path, ResourceLocation location) {
        return path.resolve("data/" + location.getNamespace() + "/loot_tables" + location.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "LootTables";
    }
}