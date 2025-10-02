package com.github.teamfusion.rottencreatures.data.client;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class ModelGenerator extends FabricModelProvider {
    private static final ModelTemplate SPAWN_EGG = createItem("template_spawn_egg");

    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        gen.createTrivialBlock(RCBlocks.TNT_BARREL.get(), TexturedModel.CUBE_TOP_BOTTOM);
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        gen.generateFlatItem(RCItems.BURNED_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.FROSTBITTEN_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.SWAMPY_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.UNDEAD_MINER_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.MUMMY_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.GLACIAL_HUNTER_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.DEAD_BEARD_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.IMMORTAL_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.SCARAB_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.HUNTER_WOLF_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.SKELETON_LACKEY_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.ZOMBIE_LACKEY_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.ZAP_SPAWN_EGG.get(), SPAWN_EGG);
        gen.generateFlatItem(RCItems.MAGMA_ROTTEN_FLESH.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RCItems.FROZEN_ROTTEN_FLESH.get(), ModelTemplates.FLAT_ITEM);
        gen.generateFlatItem(RCItems.CORRUPTED_WART.get(), ModelTemplates.FLAT_ITEM);
    }

    private static ModelTemplate createItem(String key) {
        return new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("item/" + key)), Optional.empty());
    }

    private static ModelTemplate create(String key) {
        return new ModelTemplate(Optional.of(RottenCreatures.resource("block/" + key)), Optional.empty());
    }

    private static ModelTemplate create(Block block) {
        return new ModelTemplate(Optional.of(RottenCreatures.resource("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath())), Optional.empty());
    }
}