package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.entities.Burned;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Inject(method = "entityInside", at = @At("TAIL"))
    private void rc$isEntityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!level.isClientSide) {
            // Check if a Burned is inside the Powder Snow and if it's not Obsidian.
            if (entity instanceof Burned burned && !burned.isObsidian()) {
                // Destroy the Powder Snow block and turn the Burned into Obsidian.
                level.destroyBlock(pos, false);
                burned.setObsidian(true);
            }
        }
    }
}