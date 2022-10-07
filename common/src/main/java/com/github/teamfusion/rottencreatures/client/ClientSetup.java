package com.github.teamfusion.rottencreatures.client;

import com.github.teamfusion.platform.client.RenderHandler;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.model.FrostbittenModel;
import com.github.teamfusion.rottencreatures.client.model.ModelBuilder;
import com.github.teamfusion.rottencreatures.client.model.SwampyModel;
import com.github.teamfusion.rottencreatures.client.model.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.BurnedRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.FrostbittenRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.SwampyRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.TntBarrelRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.UndeadMinerRenderer;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;

public class ClientSetup {
    public static void client() {
        RenderHandler.setEntityRenderer(RCEntityTypes.TNT_BARREL, TntBarrelRenderer::new);

        RenderHandler.setEntityRenderer(RCEntityTypes.BURNED, BurnedRenderer::new);
        RenderHandler.setModelLayerDefinition(BurnedRenderer.MAIN, () -> BurnedModel.createBodyLayer(0.0F));
        RenderHandler.setModelLayerDefinition(BurnedRenderer.LAVA, () -> BurnedModel.createBodyLayer(0.025F));
        ModelBuilder.createArmor(BurnedRenderer.INNER_ARMOR, BurnedRenderer.OUTER_ARMOR);

        RenderHandler.setEntityRenderer(RCEntityTypes.FROSTBITTEN, FrostbittenRenderer::new);
        RenderHandler.setModelLayerDefinition(FrostbittenRenderer.MAIN, FrostbittenModel::createBodyLayer);
        ModelBuilder.createArmor(FrostbittenRenderer.INNER_ARMOR, FrostbittenRenderer.OUTER_ARMOR);

        RenderHandler.setEntityRenderer(RCEntityTypes.SWAMPY, SwampyRenderer::new);
        RenderHandler.setModelLayerDefinition(SwampyRenderer.MAIN, SwampyModel::createBodyLayer);
        ModelBuilder.createArmor(SwampyRenderer.INNER_ARMOR, SwampyRenderer.OUTER_ARMOR);

        RenderHandler.setEntityRenderer(RCEntityTypes.UNDEAD_MINER, UndeadMinerRenderer::new);
        RenderHandler.setModelLayerDefinition(UndeadMinerRenderer.MAIN, UndeadMinerModel::createBodyLayer);
        ModelBuilder.createArmor(UndeadMinerRenderer.INNER_ARMOR, UndeadMinerRenderer.OUTER_ARMOR);
    }

    public static void postClient() {}
}