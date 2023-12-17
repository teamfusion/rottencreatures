package com.github.teamfusion.rottencreatures.core;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ConfigEntries {
    public static final int BURNED_SPAWN_WEIGHT = getBurnedSpawnWeight();
    public static final int FROSTBITTEN_SPAWN_WEIGHT = getFrostbittenSpawnWeight();
    public static final int GLACIAL_HUNTER_SPAWN_WEIGHT = getGlacialHunterSpawnWeight();
    public static final int SWAMPY_SPAWN_WEIGHT = getSwampySpawnWeight();
    public static final int UNDEAD_MINER_SPAWN_WEIGHT = getUndeadMinerSpawnWeight();
    public static final int UNDEAD_MINER_SPAWN_DEPTH = getUndeadMinerSpawnDepth();
    public static final int MUMMY_SPAWN_WEIGHT = getMummySpawnWeight();
    public static final int DEAD_BEARD_SPAWN_WEIGHT = getDeadBeardSpawnWeight();
    public static final double IMMORTAL_SPAWN_CHANCE = getImmortalSpawnChance();

    @ExpectPlatform
    public static int getBurnedSpawnWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getFrostbittenSpawnWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getGlacialHunterSpawnWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getSwampySpawnWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getUndeadMinerSpawnWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getUndeadMinerSpawnDepth() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getMummySpawnWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getDeadBeardSpawnWeight() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static double getImmortalSpawnChance() {
        throw new AssertionError();
    }
}