package com.github.teamfusion.rottencreatures.core.forge;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid = RottenCreatures.MOD_ID)
public class ConfigEntriesImpl {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final ConfigEntriesImpl.Common COMMON;

    public static int getBurnedSpawnWeight() {
        return COMMON.burnedSpawnWeight.get();
    }

    public static int getFrostbittenSpawnWeight() {
        return COMMON.frostbittenSpawnWeight.get();
    }

    public static int getGlacialHunterSpawnWeight() {
        return COMMON.glacialHunterSpawnWeight.get();
    }

    public static int getSwampySpawnWeight() {
        return COMMON.swampySpawnWeight.get();
    }

    public static int getUndeadMinerSpawnWeight() {
        return COMMON.undeadMinerSpawnWeight.get();
    }

    public static int getUndeadMinerSpawnDepth() {
        return COMMON.undeadMinerSpawnDepth.get();
    }

    public static int getMummySpawnWeight() {
        return COMMON.mummySpawnWeight.get();
    }

    public static int getDeadBeardSpawnWeight() {
        return COMMON.deadBeardSpawnWeight.get();
    }

    public static double getImmortalSpawnChance() {
        return COMMON.immortalSpawnChance.get();
    }

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> burnedSpawnWeight;
        public final ForgeConfigSpec.ConfigValue<Integer> frostbittenSpawnWeight;
        public final ForgeConfigSpec.ConfigValue<Integer> glacialHunterSpawnWeight;
        public final ForgeConfigSpec.ConfigValue<Integer> swampySpawnWeight;
        public final ForgeConfigSpec.ConfigValue<Integer> undeadMinerSpawnWeight;
        public final ForgeConfigSpec.ConfigValue<Integer> undeadMinerSpawnDepth;
        public final ForgeConfigSpec.ConfigValue<Integer> mummySpawnWeight;
        public final ForgeConfigSpec.ConfigValue<Integer> deadBeardSpawnWeight;
        public final ForgeConfigSpec.ConfigValue<Double> immortalSpawnChance;

        protected Common(ForgeConfigSpec.Builder builder) {
            builder.push("spawns");
            burnedSpawnWeight = builder.comment("Determine how often do Burned Zombies spawn").defineInRange("Burned Spawn Weight", 20, 0, 100);
            frostbittenSpawnWeight = builder.comment("Determine how often do Frostbitten Zombies spawn").defineInRange("Frostbitten Spawn Weight", 80, 0, 100);
            glacialHunterSpawnWeight = builder.comment("Determine how often do Glacial Hunter Zombies spawn").defineInRange("Glacial Hunter Spawn Weight", 20, 0, 100);
            swampySpawnWeight = builder.comment("Determine how often do Swampy Zombies spawn").defineInRange("Swampy Spawn Weight", 80, 0, 100);
            undeadMinerSpawnWeight = builder.comment("Determine how often do Undead Miner Zombies spawn").defineInRange("Undead Miner Spawn Weight", 20, 0, 100);
            undeadMinerSpawnDepth = builder.comment("Determine the max spawn height for Undead Miner Zombies").defineInRange("Undead Miner Spawn Depth", 63, -64, 320);
            mummySpawnWeight = builder.comment("Determine how often do Mummies spawn").defineInRange("Mummy Spawn Weight", 20, 0, 100);
            deadBeardSpawnWeight = builder.comment("Determine how often does Dead Beard spawn").defineInRange("Dead Beard Spawn Weight", 10, 0, 100);
            immortalSpawnChance = builder.comment("Determine the chance of spawning for Immortal Zombies").defineInRange("Immortal Spawn Chance", 0.025D, 0, 1);
            builder.pop();
        }
    }

    static {
        final Pair<Common, ForgeConfigSpec> specs = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specs.getRight();
        COMMON = specs.getLeft();
    }
}