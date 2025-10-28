package com.github.teamfusion.rottencreatures.common.level.blockentities;

import com.github.teamfusion.rottencreatures.common.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class ZombieSkullBlockEntity extends SkullBlockEntity {
    private static final List<Block> VALIDBLOCKS = List.of(
            RCBlocks.BURNED_HEAD.get(), RCBlocks.BURNED_WALL_HEAD.get(),
            RCBlocks.FROSTBITTEN_HEAD.get(), RCBlocks.FROSTBITTEN_WALL_HEAD.get(),
            RCBlocks.SWAMPY_HEAD.get(), RCBlocks.SWAMPY_WALL_HEAD.get(),
            RCBlocks.UNDEAD_MINER_HEAD.get(), RCBlocks.UNDEAD_MINER_WALL_HEAD.get(),
            RCBlocks.MUMMY_HEAD.get(), RCBlocks.MUMMY_WALL_HEAD.get(),
            RCBlocks.GLACIAL_HUNTER_HEAD.get(), RCBlocks.GLACIAL_HUNTER_WALL_HEAD.get(),
            RCBlocks.DEAD_BEARD_HEAD.get(), RCBlocks.DEAD_BEARD_WALL_HEAD.get(),
            RCBlocks.IMMORTAL_HEAD.get(), RCBlocks.IMMORTAL_WALL_HEAD.get(),
            RCBlocks.ZAP_HEAD.get(), RCBlocks.ZAP_WALL_HEAD.get()
    );

    public ZombieSkullBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public boolean isValidBlockState(BlockState blockState) {
        return VALIDBLOCKS.contains(blockState.getBlock());
    }

    @Override
    public BlockEntityType<?> getType() {
        return RCBlockEntityTypes.SKULL.get();
    }
}