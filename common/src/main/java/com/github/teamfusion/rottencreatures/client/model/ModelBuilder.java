package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.platform.client.RenderHandler;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class ModelBuilder {
    private static final LayerDefinition INNER_ARMOR = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.0F), 0.0F), 64, 32);
    private static final LayerDefinition OUTER_ARMOR = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F), 64, 32);

    public static ModelLayerLocation createLayer(EntityType<?> type) {
        return createLayer(type, "main");
    }

    public static ModelLayerLocation createLayer(EntityType<?> type, String name) {
        return new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, type.getDescriptionId()), name);
    }

    public static ModelLayerLocation createLayer(String key) {
        return createLayer(key, "main");
    }

    public static ModelLayerLocation createLayer(String key, String name) {
        return new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, key), name);
    }

    public static void createArmor(ModelLayerLocation inner, ModelLayerLocation outer) {
        RenderHandler.setModelLayerDefinition(inner, () -> INNER_ARMOR);
        RenderHandler.setModelLayerDefinition(outer, () -> OUTER_ARMOR);
    }
}