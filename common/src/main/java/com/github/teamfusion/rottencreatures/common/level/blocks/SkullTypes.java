package com.github.teamfusion.rottencreatures.common.level.blocks;

import net.minecraft.world.level.block.SkullBlock;

import java.util.Locale;

public enum SkullTypes implements SkullBlock.Type {
    BURNED("burned"),
    FROSTBITTEN("frostbitten"),
    SWAMPY("swampy"),
    UNDEAD_MINER("undead_miner"),
    MUMMY("mummy"),
    GLACIAL_HUNTER("glacial_hunter"),
    DEAD_BEARD("dead_beard"),
    IMMORTAL("immortal"),
    ZAP("zap");

    private final String name;

    SkullTypes(String name) {
        this.name = name;
        TYPES.put(name, this);
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}