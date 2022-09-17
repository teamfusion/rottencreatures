package com.github.teamfusion.rottencreatures.datagen.common;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

public class BlockLootGenerator extends BlockLoot {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.dropSelf(RCBlocks.TNT_BARREL.get());
    }
}