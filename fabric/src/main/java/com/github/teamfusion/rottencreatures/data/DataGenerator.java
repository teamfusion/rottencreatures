package com.github.teamfusion.rottencreatures.data;

import com.github.teamfusion.rottencreatures.data.client.LanguageGenerator;
import com.github.teamfusion.rottencreatures.data.client.ModelGenerator;
import com.github.teamfusion.rottencreatures.data.server.RecipeGenerator;
import com.github.teamfusion.rottencreatures.data.server.advancement.AdvancementGenerator;
import com.github.teamfusion.rottencreatures.data.server.loot.BlockLootGenerator;
import com.github.teamfusion.rottencreatures.data.server.loot.EntityLootGenerator;
import com.github.teamfusion.rottencreatures.data.server.tags.BiomeTagGenerator;
import com.github.teamfusion.rottencreatures.data.server.tags.BlockTagGenerator;
import com.github.teamfusion.rottencreatures.data.server.tags.EntityTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        // CLIENT SIDE
        pack.addProvider(ModelGenerator::new);
        pack.addProvider(LanguageGenerator::new);

        // SERVER SIDE
        pack.addProvider(BlockLootGenerator::new);
        pack.addProvider(EntityLootGenerator::new);
        pack.addProvider(BiomeTagGenerator::new);
        pack.addProvider(BlockTagGenerator::new);
        pack.addProvider(EntityTagGenerator::new);
        pack.addProvider(AdvancementGenerator::new);
        pack.addProvider(RecipeGenerator::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {

    }
}