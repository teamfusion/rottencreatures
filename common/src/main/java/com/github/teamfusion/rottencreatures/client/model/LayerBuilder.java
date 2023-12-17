package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.core.platform.client.RenderHandler;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

public record LayerBuilder(String key) {
    public static LayerBuilder of(String key) {
        return new LayerBuilder(key);
    }

    public static ModelLayerLocation build(String key, String type) {
        return new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, key), type);
    }

    public ModelLayerLocation create(String type) {
        return build(this.key(), type);
    }

    public ModelLayerLocation getMain() {
        return build(this.key(), "main");
    }

    public ModelLayerLocation getInner() {
        return build(this.key(), "inner_armor");
    }

    public ModelLayerLocation getOuter() {
        return build(this.key(), "outer_armor");
    }

    public void buildArmor() {
        this.buildArmor(0.0F);
    }

    public void buildArmor(float add) {
        RenderHandler.setModelLayerDefinition(this.getInner(), () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5F + add), 0.0F), 64, 32));
        RenderHandler.setModelLayerDefinition(this.getOuter(), () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.0F + add), 0.0F), 64, 32));
    }
}