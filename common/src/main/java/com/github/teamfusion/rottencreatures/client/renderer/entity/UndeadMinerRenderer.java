package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class UndeadMinerRenderer<T extends UndeadMiner> extends HumanoidMobRenderer<T, UndeadMinerModel<T>> {
    public UndeadMinerRenderer(EntityRendererProvider.Context context) {
        super(context, new UndeadMinerModel<>(context.bakeLayer(RCModelLayers.UNDEAD_MINER)), 0.5F);
        this.addLayer(
            new HumanoidArmorLayer<>(
                this,
                new UndeadMinerModel<>(context.bakeLayer(RCModelLayers.UNDEAD_MINER_INNER_ARMOR)),
                new UndeadMinerModel<>(context.bakeLayer(RCModelLayers.UNDEAD_MINER_OUTER_ARMOR))
            )
        );
    }

    @Override
    public ResourceLocation getTextureLocation(T miner) {
        return RottenCreatures.resource("textures/entity/undead_miner/undead_miner_" + miner.getVariant().getName() + ".png");
    }
}