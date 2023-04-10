package com.github.teamfusion.rottencreatures;

import com.github.teamfusion.platform.config.Config;

public class ConfigEntries {
    public static final Config.Entry<Integer> BURNED_WEIGHT = Config.create("Burned Weight", 20, "determines how often will Burneds spawn");
    public static final Config.Entry<Integer> FROSTBITTEN_WEIGHT = Config.create("Frostbitten Weight", 80, "determines how often will Frostbittens spawn");
    public static final Config.Entry<Integer> GLACIAL_HUNTER_WEIGHT = Config.create("Glacial Hunter Weight", 20, "determines how often will Glacial Hunters spawn");
    public static final Config.Entry<Integer> SWAMPY_WEIGHT = Config.create("Swampy Weight", 80, "determines how often will Swampies spawn");
    public static final Config.Entry<Integer> UNDEAD_MINER_WEIGHT = Config.create("Undead Miner Weight", 20, "determines how often will Undead Miners spawn");
    public static final Config.Entry<Integer> UNDEAD_MINER_DEPTH = Config.create("Undead Miner Depth", 63, "determines the max y height where Undead Miners can spawn");
    public static final Config.Entry<Integer> MUMMY_WEIGHT = Config.create("Mummy Weight", 20, "determines how often will Mummies spawn");
    public static final Config.Entry<Integer> DEAD_BEARD_WEIGHT = Config.create("Dead Beard Weight", 1, "determines how often will Dead Beard spawn");
    public static final Config.Entry<Float> IMMORTAL_CHANCE = Config.create("Immortal Chance", 0.025F, "determines the chance of spawning for Immortals");
}