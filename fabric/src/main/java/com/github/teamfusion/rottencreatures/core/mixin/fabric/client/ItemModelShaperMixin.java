package com.github.teamfusion.rottencreatures.core.mixin.fabric.client;

import com.github.teamfusion.rottencreatures.client.renderer.item.SpearItemRenderer;
import com.github.teamfusion.rottencreatures.common.item.SpearItem;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelShaper.class)
public abstract class ItemModelShaperMixin {
    @Shadow public abstract ModelManager getModelManager();

    @Inject(method = "getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;", at = @At("HEAD"), cancellable = true)
    private void rc$getItemModel(ItemStack itemStack, CallbackInfoReturnable<BakedModel> cir) {
        ModelManager manager = this.getModelManager();
        if (itemStack.getItem() instanceof SpearItem) cir.setReturnValue(manager.getModel(SpearItemRenderer.INVENTORY_HANDHELD_MODEL));
    }
}