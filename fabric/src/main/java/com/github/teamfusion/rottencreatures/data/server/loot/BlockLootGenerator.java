package com.github.teamfusion.rottencreatures.data.server.loot;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class BlockLootGenerator extends FabricBlockLootTableProvider {
    public BlockLootGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.dropSelf(RCBlocks.TNT_BARREL.get());
        this.dropSelf(RCBlocks.BURNED_HEAD.get());
        this.dropOther(RCBlocks.BURNED_WALL_HEAD.get(), RCBlocks.BURNED_HEAD.get());
        this.dropSelf(RCBlocks.FROSTBITTEN_HEAD.get());
        this.dropOther(RCBlocks.FROSTBITTEN_WALL_HEAD.get(), RCBlocks.FROSTBITTEN_HEAD.get());
        this.dropSelf(RCBlocks.SWAMPY_HEAD.get());
        this.dropOther(RCBlocks.SWAMPY_WALL_HEAD.get(), RCBlocks.SWAMPY_HEAD.get());
        this.dropSelf(RCBlocks.UNDEAD_MINER_HEAD.get());
        this.dropOther(RCBlocks.UNDEAD_MINER_WALL_HEAD.get(), RCBlocks.UNDEAD_MINER_HEAD.get());
        this.dropSelf(RCBlocks.MUMMY_HEAD.get());
        this.dropOther(RCBlocks.MUMMY_WALL_HEAD.get(), RCBlocks.MUMMY_HEAD.get());
        this.dropSelf(RCBlocks.GLACIAL_HUNTER_HEAD.get());
        this.dropOther(RCBlocks.GLACIAL_HUNTER_WALL_HEAD.get(), RCBlocks.GLACIAL_HUNTER_HEAD.get());
        this.dropSelf(RCBlocks.DEAD_BEARD_HEAD.get());
        this.dropOther(RCBlocks.DEAD_BEARD_WALL_HEAD.get(), RCBlocks.DEAD_BEARD_HEAD.get());
        this.dropSelf(RCBlocks.IMMORTAL_HEAD.get());
        this.dropOther(RCBlocks.IMMORTAL_WALL_HEAD.get(), RCBlocks.IMMORTAL_HEAD.get());
        this.dropSelf(RCBlocks.ZAP_HEAD.get());
        this.dropOther(RCBlocks.ZAP_WALL_HEAD.get(), RCBlocks.ZAP_HEAD.get());
    }
}