package com.github.teamfusion.rottencreatures;

import com.blackgear.platform.core.util.config.ConfigBuilder;

public class CommonConfig {
    public final ConfigBuilder.ConfigValue<Integer> burnedWeight;
    public final ConfigBuilder.ConfigValue<Integer> frostbittenWeight;
    public final ConfigBuilder.ConfigValue<Integer> glacialHunterWeight;
    public final ConfigBuilder.ConfigValue<Integer> swampyWeight;
    public final ConfigBuilder.ConfigValue<Integer> undeadMinerWeight;
    public final ConfigBuilder.ConfigValue<Integer> undeadMinerDepth;
    public final ConfigBuilder.ConfigValue<Integer> mummyWeight;
    public final ConfigBuilder.ConfigValue<Integer> deadBeardWeight;
    public final ConfigBuilder.ConfigValue<Float> immortalChance;

    public CommonConfig(ConfigBuilder builder) {
        this.burnedWeight = builder.comment("determine how often will Burneds spawn").define("burnedWeight", 20);
        this.frostbittenWeight = builder.comment("determine how often will Frostbittens spawn").define("frostbittenWeight", 80);
        this.glacialHunterWeight = builder.comment("determine how often will Glacial Hunters spawn").define("glacialHunterWeight", 20);
        this.swampyWeight = builder.comment("determine how often will Swampies spawn").define("swampyWeight", 80);
        this.undeadMinerWeight = builder.comment("determine how often will Undead Miners spawn").define("undeadMinerWeight", 20);
        this.undeadMinerDepth = builder.comment("determine the max y height where Undead Miners can spawn").define("undeadMinerDepth", 63);
        this.mummyWeight = builder.comment("determine how often will Mummies spawn").define("mummyWeight", 20);
        this.deadBeardWeight = builder.comment("determine how often will Dead Beard spawn").define("deadBeardWeight", 1);
        this.immortalChance = builder.comment("determine the chance of spawning for Immortals").define("immortalChance", 0.025F);
    }
}