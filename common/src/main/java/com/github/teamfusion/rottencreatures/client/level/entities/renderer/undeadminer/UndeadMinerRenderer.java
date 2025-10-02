package com.github.teamfusion.rottencreatures.client.level.entities.renderer.undeadminer;

import com.github.teamfusion.rottencreatures.client.level.entities.model.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.undeadminer.UndeadMiner;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
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
                new UndeadMinerModel<>(context.bakeLayer(RCModelLayers.UNDEAD_MINER_OUTER_ARMOR)),
                context.getModelManager()
            )
        );
    }

    @Override
    public ResourceLocation getTextureLocation(T miner) {
        return RottenCreatures.resource("textures/entity/undead_miner/undead_miner_" + miner.getVariant().getName() + ".png");
    }
}