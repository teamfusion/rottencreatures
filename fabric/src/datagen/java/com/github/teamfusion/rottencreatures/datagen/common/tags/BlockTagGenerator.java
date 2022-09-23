package com.github.teamfusion.rottencreatures.datagen.common.tags;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tags.BlockTags;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateTags() {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(RCBlocks.TNT_BARREL.get());
    }
}
