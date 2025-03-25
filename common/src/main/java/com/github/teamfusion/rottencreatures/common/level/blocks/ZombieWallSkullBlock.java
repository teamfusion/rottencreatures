package com.github.teamfusion.rottencreatures.common.level.blocks;

import com.github.teamfusion.rottencreatures.common.level.blockentities.ZombieSkullBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ZombieWallSkullBlock extends WallSkullBlock {
    public ZombieWallSkullBlock(SkullBlock.Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ZombieSkullBlockEntity(pos, state);
    }
}