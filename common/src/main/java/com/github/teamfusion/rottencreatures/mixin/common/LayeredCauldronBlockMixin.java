package com.github.teamfusion.rottencreatures.mixin.common;

import com.github.teamfusion.rottencreatures.common.entities.Burned;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LayeredCauldronBlock.class)
public abstract class LayeredCauldronBlockMixin extends AbstractCauldronBlock {
    @Shadow protected abstract void handleEntityOnFireInside(BlockState blockState, Level level, BlockPos blockPos);

    public LayeredCauldronBlockMixin(Properties properties, Map<Item, CauldronInteraction> map) {
        super(properties, map);
    }

    /**
     * if a burned falls inside a cauldron it will be turned into an obsidian variant
     * and a layer of the content will be consumed
     */
    @Inject(method = "entityInside", at = @At("TAIL"))
    private void rc$isEntityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (!level.isClientSide && entity instanceof Burned burned && !burned.isObsidian() && this.isEntityInsideContent(state, pos, entity)) {
            burned.setObsidian(true);
            if (entity.mayInteract(level, pos)) this.handleEntityOnFireInside(state, level, pos);
        }
    }
}