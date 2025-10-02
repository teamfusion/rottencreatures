package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.level.entities.burned.Burned;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronBlockMixin extends AbstractCauldronBlock {
    @Shadow protected abstract void handleEntityOnFireInside(BlockState blockState, Level level, BlockPos blockPos);

    public LayeredCauldronBlockMixin(Properties properties, CauldronInteraction.InteractionMap interactions) {
        super(properties, interactions);
    }

    /**
     * if a burned falls inside a cauldron it will be turned into an obsidian variant
     * and a layer of the content will be consumed
     */
    @Inject(method = "entityInside", at = @At("TAIL"))
    private void rc$isEntityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!level.isClientSide && this.isEntityInsideContent(state, pos, entity)) {
            // Check if a Burned it's inside the Cauldron, and it's not Obsidian.
            if (entity instanceof Burned burned && !burned.isObsidian()) {
                // Set the Burned into Obsidian and decrease a layer of liquid on the Cauldron if possible.
                burned.setObsidian(true);

                if (entity.mayInteract(level, pos)) {
                    this.handleEntityOnFireInside(state, level, pos);
                }
            }
        }
    }
}