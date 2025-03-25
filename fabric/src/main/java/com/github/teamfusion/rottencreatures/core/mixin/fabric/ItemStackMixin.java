package com.github.teamfusion.rottencreatures.core.mixin.fabric;

import com.github.teamfusion.rottencreatures.common.level.item.TreasureChestBlockItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Inject(
        method = "getTooltipLines",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/Item;appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V",
            shift = At.Shift.AFTER
        )
    )
    private void appendTooltip(@Nullable Player player, TooltipFlag isAdvanced, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> components, @Local int hideFlags) {
        if (this.getItem() instanceof TreasureChestBlockItem chest) {
            Level level = player == null ? null : player.level;
            chest.displayTreasureChestContents((ItemStack) (Object) this, player, level, components, isAdvanced);
        }
    }
}