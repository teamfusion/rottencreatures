package com.github.teamfusion.platform.config.fabric;

import com.github.teamfusion.rottencreatures.ConfigEntries;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = RottenCreatures.MOD_ID)
public class ConfigImpl implements ConfigData {
    public static void bootstrap() {
        AutoConfig.register(ConfigImpl.class, GsonConfigSerializer::new);
    }

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public MobSpawns mobSpawns = new MobSpawns();

    public static class MobSpawns {
        @Comment("determines how often will Burneds spawn")
        public int burned_weight = ConfigEntries.BURNED_WEIGHT.value();

        @Comment("determines how often will Frostbittens spawn")
        public int frostbitten_weight = ConfigEntries.FROSTBITTEN_WEIGHT.value();

        @Comment("determines how often will Glacial Hunters spawn")
        public int glacial_hunter_weight = ConfigEntries.GLACIAL_HUNTER_WEIGHT.value();

        @Comment("determines how often will Swampies spawn")
        public int swampy_weight = ConfigEntries.SWAMPY_WEIGHT.value();

        @Comment("determines how often will Undead Miners spawn")
        public int undead_miner_weight = ConfigEntries.UNDEAD_MINER_WEIGHT.value();

        @Comment("determines how often will Mummies spawn")
        public int mummy_weight = ConfigEntries.MUMMY_WEIGHT.value();

        @Comment("determines how often will Dead Beard spawn")
        public int dead_beard_weight = ConfigEntries.DEAD_BEARD_WEIGHT.value();

        @Comment("determines the chance of spawning for Immortals")
        public float immortal_chance = ConfigEntries.IMMORTAL_CHANCE.value();
    }
}