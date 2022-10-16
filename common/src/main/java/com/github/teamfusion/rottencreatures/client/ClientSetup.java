package com.github.teamfusion.rottencreatures.client;

import com.github.teamfusion.platform.client.RenderHandler;
import com.github.teamfusion.rottencreatures.client.model.GlacialHunterModel;
import com.github.teamfusion.rottencreatures.client.model.MummyModel;
import com.github.teamfusion.rottencreatures.client.model.ScarabModel;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.model.FrostbittenModel;
import com.github.teamfusion.rottencreatures.client.model.SwampyModel;
import com.github.teamfusion.rottencreatures.client.model.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.GlacialHunterRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.MummyRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.ScarabRenderer;
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
        RenderHandler.setModelLayerDefinition(BurnedRenderer.LAYER.getMain(), () -> BurnedModel.createBodyLayer(0.0F));
        BurnedRenderer.LAYER.buildArmor();

        RenderHandler.setEntityRenderer(RCEntityTypes.FROSTBITTEN, FrostbittenRenderer::new);
        RenderHandler.setModelLayerDefinition(FrostbittenRenderer.LAYER.getMain(), FrostbittenModel::createBodyLayer);
        FrostbittenRenderer.LAYER.buildArmor();

        RenderHandler.setEntityRenderer(RCEntityTypes.SWAMPY, SwampyRenderer::new);
        RenderHandler.setModelLayerDefinition(SwampyRenderer.LAYER.getMain(), SwampyModel::createBodyLayer);
        SwampyRenderer.LAYER.buildArmor();

        RenderHandler.setEntityRenderer(RCEntityTypes.UNDEAD_MINER, UndeadMinerRenderer::new);
        RenderHandler.setModelLayerDefinition(UndeadMinerRenderer.LAYER.getMain(), UndeadMinerModel::createBodyLayer);
        UndeadMinerRenderer.LAYER.buildArmor();

//        RenderHandler.setEntityRenderer(RCEntityTypes.MUMMY, MummyRenderer::new);
//        RenderHandler.setModelLayerDefinition(MummyRenderer.LAYER.getMain(), MummyModel::createBodyLayer);
//        MummyRenderer.LAYER.buildArmor(0.25F);

//        RenderHandler.setEntityRenderer(RCEntityTypes.GLACIAL_HUNTER, GlacialHunterRenderer::new);
//        RenderHandler.setModelLayerDefinition(GlacialHunterRenderer.LAYER.getMain(), GlacialHunterModel::createBodyLayer);

//        RenderHandler.setEntityRenderer(RCEntityTypes.SCARAB, ScarabRenderer::new);
//        RenderHandler.setModelLayerDefinition(ScarabRenderer.LAYER.getMain(), ScarabModel::createBodyLayer);
    }

    public static void postClient() {}
}