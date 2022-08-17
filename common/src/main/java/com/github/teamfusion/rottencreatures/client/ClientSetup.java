package com.github.teamfusion.rottencreatures.client;

import com.github.teamfusion.platform.client.RenderHandler;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.BurnedRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.TntBarrelRenderer;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class ClientSetup {
    public static void client() {
        RenderHandler.setEntityRenderer(RCEntityTypes.TNT_BARREL, TntBarrelRenderer::new);

        LayerDefinition outerArmor = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(1.0F), 0.0F), 64, 32);
        LayerDefinition innerArmor = LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F), 64, 32);
        RenderHandler.setEntityRenderer(RCEntityTypes.BURNED, BurnedRenderer::new);
        RenderHandler.setModelLayerDefinition(BurnedRenderer.BURNED, () -> BurnedModel.createBodyLayer(0.0F));
        RenderHandler.setModelLayerDefinition(BurnedRenderer.BURNED_INNER_ARMOR, () -> innerArmor);
        RenderHandler.setModelLayerDefinition(BurnedRenderer.BURNED_OUTER_ARMOR, () -> outerArmor);
        RenderHandler.setModelLayerDefinition(BurnedRenderer.BURNED_LAVA_LAYER, () -> BurnedModel.createBodyLayer(0.025F));
    }

    public static void postClient() {}
}