package com.github.teamfusion.rottencreatures.datagen.common.tags;

import com.github.teamfusion.rottencreatures.data.RCBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeTagGenerator extends TagsProvider<Biome> {
    public BiomeTagGenerator(FabricDataGenerator generator) {
        super(generator, BuiltinRegistries.BIOME);
    }

    @Override
    protected void addTags() {
        this.tag(RCBiomeTags.CAN_BURNED_SPAWN_ON).add(Biomes.NETHER_WASTES);
        this.tag(RCBiomeTags.CAN_FROSTBITTEN_SPAWN_ON).addTag(BiomeTags.HAS_IGLOO).add(Biomes.ICE_SPIKES);
        this.tag(RCBiomeTags.CAN_GLACIAL_HUNTER_SPAWN_ON).addTag(BiomeTags.HAS_IGLOO).add(Biomes.ICE_SPIKES);
        this.tag(RCBiomeTags.CAN_SWAMPY_SPAWN_ON).addTag(BiomeTags.HAS_RUINED_PORTAL_SWAMP);
        this.tag(RCBiomeTags.CAN_UNDEAD_MINER_SPAWN_ON).addTag(BiomeTags.IS_OVERWORLD);
        this.tag(RCBiomeTags.CAN_MUMMY_SPAWN_ON).addTag(BiomeTags.HAS_DESERT_PYRAMID);
        this.tag(RCBiomeTags.CAN_DEAD_BEARD_SPAWN_ON).addTag(BiomeTags.HAS_SHIPWRECK_BEACHED);
    }
}