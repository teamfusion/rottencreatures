package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.level.entities.burned.Burned;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaCauldronBlock.class)
public abstract class LavaCauldronBlockMixin extends AbstractCauldronBlock {
    public LavaCauldronBlockMixin(Properties properties, CauldronInteraction.InteractionMap interactions) {
        super(properties, interactions);
    }

    /**
     * if a burned is inside a lava cauldron, it will remove the obsidian variant
     */
    @Inject(method = "entityInside", at = @At("TAIL"))
    private void rc$isEntityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        // Check if a Burned is inside the Cauldron and if it's Obsidian, then turn it back to Normal or Crazy.
        if (this.isEntityInsideContent(state, pos, entity) && entity instanceof Burned burned && !burned.isObsidian()) {
            burned.setObsidian(false);
        }
    }
}