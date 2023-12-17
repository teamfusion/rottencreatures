package com.github.teamfusion.rottencreatures.core.fabric;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = RottenCreatures.MOD_ID)
public class ConfigEntriesImpl implements ConfigData {
    public static int getBurnedSpawnWeight() {
        return RottenCreaturesFabric.CONFIG.spawns.burned_spawn_weight;
    }

    public static int getFrostbittenSpawnWeight() {
        return RottenCreaturesFabric.CONFIG.spawns.frostbitten_spawn_weight;
    }

    public static int getGlacialHunterSpawnWeight() {
        return RottenCreaturesFabric.CONFIG.spawns.glacial_hunter_spawn_weight;
    }

    public static int getSwampySpawnWeight() {
        return RottenCreaturesFabric.CONFIG.spawns.swampy_spawn_weight;
    }

    public static int getUndeadMinerSpawnWeight() {
        return RottenCreaturesFabric.CONFIG.spawns.undead_miner_spawn_weight;
    }

    public static int getUndeadMinerSpawnDepth() {
        return RottenCreaturesFabric.CONFIG.spawns.undead_miner_spawn_depth;
    }

    public static int getMummySpawnWeight() {
        return RottenCreaturesFabric.CONFIG.spawns.mummy_spawn_weight;
    }

    public static int getDeadBeardSpawnWeight() {
        return RottenCreaturesFabric.CONFIG.spawns.dead_beard_spawn_weight;
    }

    public static double getImmortalSpawnChance() {
        return RottenCreaturesFabric.CONFIG.spawns.immortal_spawn_chance;
    }

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public Spawns spawns = new Spawns();

    public static class Spawns {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int burned_spawn_weight = 20;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int frostbitten_spawn_weight = 80;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int glacial_hunter_spawn_weight = 20;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int swampy_spawn_weight = 80;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int undead_miner_spawn_weight = 20;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = -64, max = 320)
        public int undead_miner_spawn_depth = 63;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int mummy_spawn_weight = 20;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int dead_beard_spawn_weight = 10;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double immortal_spawn_chance = 0.025D;
    }
}