package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.helper.BlockRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.blocks.TntBarrelBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Supplier;

public class RCBlocks {
    public static final BlockRegistry BLOCKS = BlockRegistry.create(RottenCreatures.MOD_ID);

    public static final Supplier<Block> TNT_BARREL = BLOCKS.register(
        "tnt_barrel",
        TntBarrelBlock::new,
        BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2.0F, 5.0F)
            .sound(SoundType.WOOD),
        BlockItem::new,
        new Item.Properties().tab(RottenCreatures.TAB)
    );
}