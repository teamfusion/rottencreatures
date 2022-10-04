package com.github.teamfusion.rottencreatures.mixin.client;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow protected abstract void renderTextureOverlay(ResourceLocation resourceLocation, float f);

    @Shadow @Final private static ResourceLocation POWDER_SNOW_OUTLINE_LOCATION;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at = @At("TAIL"))
    private void rc$render(PoseStack matrices, float tickDelta, CallbackInfo ci) {
        if (this.minecraft.player.hasEffect(RCMobEffects.FREEZE.get())) {
            this.renderTextureOverlay(POWDER_SNOW_OUTLINE_LOCATION, this.minecraft.player.getEffect(RCMobEffects.FREEZE.get()).getDuration());
        }
    }
}