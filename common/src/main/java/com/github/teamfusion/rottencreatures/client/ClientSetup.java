package com.github.teamfusion.rottencreatures.client;

import com.github.teamfusion.platform.client.RenderHandler;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.model.DeadBeardModel;
import com.github.teamfusion.rottencreatures.client.model.FlyingScarabModel;
import com.github.teamfusion.rottencreatures.client.model.FrostbittenModel;
import com.github.teamfusion.rottencreatures.client.model.GlacialHunterModel;
import com.github.teamfusion.rottencreatures.client.model.ImmortalModel;
import com.github.teamfusion.rottencreatures.client.model.ImmortalOverlayModel;
import com.github.teamfusion.rottencreatures.client.model.MummyModel;
import com.github.teamfusion.rottencreatures.client.model.ScarabModel;
import com.github.teamfusion.rottencreatures.client.model.SwampyModel;
import com.github.teamfusion.rottencreatures.client.model.TreasureChestModel;
import com.github.teamfusion.rottencreatures.client.model.UndeadMinerModel;
import com.github.teamfusion.rottencreatures.client.model.ZapModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.BurnedRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.DeadBeardRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.FlyingScarabRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.FrostbittenRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.GlacialHunterRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.HunterWolfRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.ImmortalRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.MummyRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.ScarabRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.SkeletonLackeyRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.SwampyRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.TntBarrelRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.TreasureChestRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.UndeadMinerRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.ZapRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.ZombieLackeyRenderer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.DashAttackLayer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.ImmortalOverlayLayer;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;

public class ClientSetup {
    public static void client() {
        RenderHandler.setEntityRenderer(RCEntityTypes.TNT_BARREL, TntBarrelRenderer::new);
        RenderHandler.setEntityRenderer(RCEntityTypes.TREASURE_CHEST, TreasureChestRenderer::new);
        RenderHandler.setModelLayerDefinition(TreasureChestRenderer.LAYER.getMain(), TreasureChestModel::createBodyLayer);

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

        RenderHandler.setEntityRenderer(RCEntityTypes.MUMMY, MummyRenderer::new);
        RenderHandler.setModelLayerDefinition(MummyRenderer.LAYER.getMain(), MummyModel::createBodyLayer);
        MummyRenderer.LAYER.buildArmor(0.25F);

        RenderHandler.setEntityRenderer(RCEntityTypes.GLACIAL_HUNTER, GlacialHunterRenderer::new);
        RenderHandler.setModelLayerDefinition(GlacialHunterRenderer.LAYER.getMain(), GlacialHunterModel::createBodyLayer);

        RenderHandler.setEntityRenderer(RCEntityTypes.HUNTER_WOLF, HunterWolfRenderer::new);

        RenderHandler.setEntityRenderer(RCEntityTypes.SCARAB, ScarabRenderer::new);
        RenderHandler.setModelLayerDefinition(ScarabRenderer.LAYER.getMain(), ScarabModel::createBodyLayer);

        RenderHandler.setEntityRenderer(RCEntityTypes.FLYING_SCARAB, FlyingScarabRenderer::new);
        RenderHandler.setModelLayerDefinition(FlyingScarabRenderer.LAYER.getMain(), FlyingScarabModel::createBodyLayer);

        RenderHandler.setEntityRenderer(RCEntityTypes.DEAD_BEARD, DeadBeardRenderer::new);
        RenderHandler.setModelLayerDefinition(DeadBeardRenderer.LAYER.getMain(), DeadBeardModel::createBodyLayer);

        RenderHandler.setEntityRenderer(RCEntityTypes.ZOMBIE_LACKEY, ZombieLackeyRenderer::new);
        RenderHandler.setModelLayerDefinition(ZombieLackeyRenderer.LAYER.getMain(), () -> DrownedModel.createBodyLayer(CubeDeformation.NONE));
        RenderHandler.setModelLayerDefinition(ZombieLackeyRenderer.OUTER_LAYER, () -> DrownedModel.createBodyLayer(new CubeDeformation(0.25F)));
        ZombieLackeyRenderer.LAYER.buildArmor();

        RenderHandler.setEntityRenderer(RCEntityTypes.SKELETON_LACKEY, SkeletonLackeyRenderer::new);
        RenderHandler.setModelLayerDefinition(SkeletonLackeyRenderer.LAYER.getMain(), SkeletonModel::createBodyLayer);
        SkeletonLackeyRenderer.LAYER.buildArmor();

        RenderHandler.setEntityRenderer(RCEntityTypes.IMMORTAL, ImmortalRenderer::new);
        RenderHandler.setModelLayerDefinition(ImmortalRenderer.LAYER.getMain(), ImmortalModel::createBodyLayer);
        ImmortalRenderer.LAYER.buildArmor();

        RenderHandler.setModelLayerDefinition(DashAttackLayer.LAYER.getMain(), DashAttackLayer::createLayer);
        RenderHandler.setModelLayerDefinition(ImmortalOverlayLayer.LAYER.getMain(), ImmortalOverlayModel::createLayer);

        RenderHandler.setEntityRenderer(RCEntityTypes.ZAP, ZapRenderer::new);
        RenderHandler.setModelLayerDefinition(ZapRenderer.LAYER.getMain(), ZapModel::createBodyLayer);
        ZapRenderer.LAYER.buildArmor();
    }

    public static void postClient() {}
}