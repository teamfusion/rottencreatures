package com.github.teamfusion.rottencreatures.datagen;

import com.github.teamfusion.rottencreatures.datagen.client.LanguageGenerator;
import com.github.teamfusion.rottencreatures.datagen.client.ModelGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.RecipeGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.loot.BlockLootGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.tags.BiomeTagGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.tags.BlockTagGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.loot.EntityLootGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.tags.EntityTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class RottenCreaturesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        gen.addProvider(ModelGenerator::new);
        gen.addProvider(LanguageGenerator::new);
        gen.addProvider(BlockLootGenerator::new);
        gen.addProvider(EntityLootGenerator::new);
        gen.addProvider(BiomeTagGenerator::new);
        gen.addProvider(BlockTagGenerator::new);
        gen.addProvider(EntityTagGenerator::new);
        gen.addProvider(RecipeGenerator::new);
    }
}
