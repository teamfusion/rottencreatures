package com.github.teamfusion.rottencreatures.common.level.blockentities;

import com.github.teamfusion.rottencreatures.common.registries.RCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ZombieSkullBlockEntity extends SkullBlockEntity {
    public ZombieSkullBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return RCBlockEntityTypes.SKULL.get();
    }
}