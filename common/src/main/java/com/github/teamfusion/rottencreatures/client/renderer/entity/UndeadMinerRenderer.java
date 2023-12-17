package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.model.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class UndeadMinerRenderer<T extends UndeadMiner> extends HumanoidMobRenderer<T, UndeadMinerModel<T>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("undead_miner");

    public UndeadMinerRenderer(EntityRendererProvider.Context context) {
        super(context, new UndeadMinerModel<>(context.bakeLayer(LAYER.getMain())), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new UndeadMinerModel<>(context.bakeLayer(LAYER.getInner())), new UndeadMinerModel<>(context.bakeLayer(LAYER.getOuter()))));
    }

    @Override
    public ResourceLocation getTextureLocation(T miner) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/undead_miner/undead_miner_" + miner.getVariant().getName() + ".png");
    }
}