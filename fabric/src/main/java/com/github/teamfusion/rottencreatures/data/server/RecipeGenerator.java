package com.github.teamfusion.rottencreatures.data.server;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, RCItems.CORRUPTED_WART.get())
            .define('W', Items.NETHER_WART)
            .define('F', RCItems.FROZEN_ROTTEN_FLESH.get())
            .pattern(" W ")
            .pattern("WFW")
            .pattern(" W ")
            .unlockedBy("has_nether_wart", has(Items.NETHER_WART))
            .unlockedBy("has_frozen_rotten_flesh", has(RCItems.FROZEN_ROTTEN_FLESH.get()))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RCBlocks.TNT_BARREL.get())
            .define('G', Items.GUNPOWDER)
            .define('B', Items.BARREL)
            .pattern("GGG")
            .pattern("GBG")
            .pattern("GGG")
            .unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
            .unlockedBy("has_barrel", has(Items.BARREL))
            .save(output);

        simpleCookingRecipe(output, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, RCItems.FROZEN_ROTTEN_FLESH.get(), Items.ROTTEN_FLESH, 0.1F);
        simpleCookingRecipe(output, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, RCItems.FROZEN_ROTTEN_FLESH.get(), Items.ROTTEN_FLESH, 0.1F);
    }
}