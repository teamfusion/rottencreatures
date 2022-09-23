package com.github.teamfusion.rottencreatures.datagen;

import com.github.teamfusion.rottencreatures.datagen.client.LanguageProvider;
import com.github.teamfusion.rottencreatures.datagen.client.ModelProvider;
import com.github.teamfusion.rottencreatures.datagen.common.loot.BlockLootGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.tags.BlockTagGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.loot.EntityLootGenerator;
import com.github.teamfusion.rottencreatures.datagen.common.tags.EntityTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class RottenCreaturesDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        gen.addProvider(ModelProvider::new);
        gen.addProvider(LanguageProvider::new);
        gen.addProvider(BlockLootGenerator::new);
        gen.addProvider(EntityLootGenerator::new);
        gen.addProvider(BlockTagGenerator::new);
        gen.addProvider(EntityTagGenerator::new);
    }
}
