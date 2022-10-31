package com.github.teamfusion.rottencreatures.datagen.common;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    private final DataGenerator data;

    public RecipeGenerator(DataGenerator generator) {
        super(generator);
        this.data = generator;
    }

    @Override
    public void run(CachedOutput cache) {
        Path path = this.data.getOutputFolder();
        Set<ResourceLocation> recipes = Sets.newHashSet();
        buildCraftingRecipes(recipe -> {
            if (!recipes.add(recipe.getId())) {
                throw new IllegalStateException("Duplicate recipe " + recipe.getId());
            } else {
                saveRecipe(cache, recipe.serializeRecipe(), path.resolve("data/" + recipe.getId().getNamespace() + "/recipes/" + recipe.getId().getPath() + ".json"));
                JsonObject json = recipe.serializeAdvancement();
                if (json != null) {
                    saveAdvancement(cache, json, path.resolve("data/" + recipe.getId().getNamespace() + "/advancements/" + recipe.getAdvancementId().getPath() + ".json"));
                }
            }
        });
    }

    public static void saveRecipe(CachedOutput cache, JsonObject json, Path recipe) {
        try {
            DataProvider.saveStable(cache, json, recipe);
        } catch (IOException exception) {
            RottenCreatures.LOGGER.error("Couldn't save recipe {}", recipe, exception);
        }
    }

    public static void saveAdvancement(CachedOutput cache, JsonObject json, Path advancement) {
        try {
            DataProvider.saveStable(cache, json, advancement);
        } catch (IOException exception) {
            RottenCreatures.LOGGER.error("Couldn't save recipe advancement {}", advancement, exception);
        }
    }

    public static void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RCItems.CORRUPTED_WART.get()).define('W', Items.NETHER_WART).define('F', RCItems.FROZEN_ROTTEN_FLESH.get())
                .pattern(" W ")
                .pattern("WFW")
                .pattern(" W ")
                .unlockedBy("has_nether_wart", has(Items.NETHER_WART)).unlockedBy("has_frozen_rotten_flesh", has(RCItems.FROZEN_ROTTEN_FLESH.get())).save(consumer);
        ShapedRecipeBuilder.shaped(RCBlocks.TNT_BARREL.get()).define('G', Items.GUNPOWDER).define('B', Items.BARREL)
                .pattern("GGG")
                .pattern("GBG")
                .pattern("GGG")
                .unlockedBy("has_gunpowder", has(Items.GUNPOWDER)).unlockedBy("has_barrel", has(Items.BARREL)).save(consumer);
        simpleCookingRecipe(consumer, "smoking", RecipeSerializer.SMOKING_RECIPE, 100, RCItems.FROZEN_ROTTEN_FLESH.get(), Items.ROTTEN_FLESH, 0.1F);
        simpleCookingRecipe(consumer, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600, RCItems.FROZEN_ROTTEN_FLESH.get(), Items.ROTTEN_FLESH, 0.1F);
    }
}