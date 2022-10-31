package com.github.teamfusion.rottencreatures.datagen.common.tags;


import com.github.teamfusion.rottencreatures.data.RCBiomeTags;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeTagGenerator extends TagsProvider<Biome> {
    public BiomeTagGenerator(DataGenerator generator) {
        super(generator, BuiltinRegistries.BIOME);
    }

    @Override
    protected void addTags() {
        this.tag(RCBiomeTags.BURNED.whitelist()).add(Biomes.NETHER_WASTES);
        this.tag(RCBiomeTags.BURNED.blacklist());
        this.tag(RCBiomeTags.FROSTBITTEN.whitelist()).add(Biomes.ICE_SPIKES).addOptionalTag(BiomeTags.HAS_IGLOO.location());
        this.tag(RCBiomeTags.FROSTBITTEN.blacklist());
        this.tag(RCBiomeTags.GLACIAL_HUNTER.whitelist()).add(Biomes.ICE_SPIKES).addOptionalTag(BiomeTags.HAS_IGLOO.location());
        this.tag(RCBiomeTags.GLACIAL_HUNTER.blacklist());
        this.tag(RCBiomeTags.SWAMPY.whitelist()).addOptionalTag(BiomeTags.HAS_RUINED_PORTAL_SWAMP.location());
        this.tag(RCBiomeTags.SWAMPY.blacklist());

        this.tag(RCBiomeTags.UNDEAD_MINER.whitelist()).addOptionalTag(BiomeTags.IS_OVERWORLD.location());
        this.tag(RCBiomeTags.UNDEAD_MINER.blacklist()).add(Biomes.MUSHROOM_FIELDS, Biomes.DEEP_DARK);

        this.tag(RCBiomeTags.MUMMY.whitelist()).addOptionalTag(BiomeTags.HAS_DESERT_PYRAMID.location());
        this.tag(RCBiomeTags.MUMMY.blacklist());
        this.tag(RCBiomeTags.DEAD_BEARD.whitelist()).addOptionalTag(BiomeTags.HAS_SHIPWRECK_BEACHED.location());
        this.tag(RCBiomeTags.DEAD_BEARD.blacklist());
    }
}