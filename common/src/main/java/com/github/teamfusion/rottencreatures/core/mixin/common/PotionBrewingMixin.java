package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.registries.RCPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
    @Inject(
        method = "addVanillaMixes",
        at = @At("TAIL")
    )
    private static void addVanillaMixes(PotionBrewing.Builder builder, CallbackInfo ci) {
        builder.addMix(Potions.WATER, RCItems.CORRUPTED_WART.get(), RCPotions.CORRUPTED.getHolder().get());
        builder.addMix(RCPotions.CORRUPTED.getHolder().get(), RCItems.FROZEN_ROTTEN_FLESH.get(), RCPotions.FREEZE.getHolder().get());
        builder.addMix(RCPotions.FREEZE.getHolder().get(), Items.REDSTONE, RCPotions.LONG_FREEZE.getHolder().get());
        builder.addMix(RCPotions.FREEZE.getHolder().get(), Items.GLOWSTONE_DUST, RCPotions.STRONG_FREEZE.getHolder().get());
    }
}