package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.ModelBuilder;
import com.github.teamfusion.rottencreatures.client.model.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class UndeadMinerRenderer<T extends UndeadMiner> extends HumanoidMobRenderer<T, UndeadMinerModel<T>> {
    public static final ModelLayerLocation MAIN = ModelBuilder.createLayer("undead_miner");
    public static final ModelLayerLocation INNER_ARMOR = ModelBuilder.createLayer("undead_miner", "inner_armor");
    public static final ModelLayerLocation OUTER_ARMOR = ModelBuilder.createLayer("undead_miner", "outer_armor");

    public UndeadMinerRenderer(EntityRendererProvider.Context context) {
        super(context, new UndeadMinerModel<>(context.bakeLayer(MAIN)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new UndeadMinerModel<>(context.bakeLayer(INNER_ARMOR)), new UndeadMinerModel<>(context.bakeLayer(OUTER_ARMOR))));
    }

    @Override
    public ResourceLocation getTextureLocation(T miner) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/undead_miner/undead_miner_" + miner.getVariant().getName() + ".png");
    }
}