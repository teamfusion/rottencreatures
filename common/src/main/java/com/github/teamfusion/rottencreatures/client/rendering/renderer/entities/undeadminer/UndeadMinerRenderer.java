package com.github.teamfusion.rottencreatures.client.rendering.renderer.entities.undeadminer;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.rendering.model.entities.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.common.level.entities.living.undeadminer.UndeadMiner;
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