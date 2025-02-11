package com.github.teamfusion.rottencreatures.client;

import com.blackgear.platform.client.RendererRegistry;
import com.github.teamfusion.rottencreatures.client.model.*;
import com.github.teamfusion.rottencreatures.client.renderer.entity.*;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.DashAttackLayer;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class EntityRenderers {
    public static void registerEntityRenderers() {
        RendererRegistry.addEntityRenderer(RCEntityTypes.TNT_BARREL, TntBarrelRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.TREASURE_CHEST, TreasureChestRenderer::new);

        RendererRegistry.addEntityRenderer(RCEntityTypes.BURNED, BurnedRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.FROSTBITTEN, FrostbittenRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.SWAMPY, SwampyRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.UNDEAD_MINER, UndeadMinerRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.MUMMY, MummyRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.GLACIAL_HUNTER, GlacialHunterRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.HUNTER_WOLF, HunterWolfRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.DEAD_BEARD, DeadBeardRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.ZOMBIE_LACKEY, ZombieLackeyRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.SKELETON_LACKEY, SkeletonLackeyRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.IMMORTAL, ImmortalRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.ZAP, ZapRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.SCARAB, ScarabRenderer::new);
        RendererRegistry.addEntityRenderer(RCEntityTypes.FLYING_SCARAB, FlyingScarabRenderer::new);
    }

    public static void registerModelLayers() {
        CubeDeformation none = CubeDeformation.NONE;
        CubeDeformation outerLayer = new CubeDeformation(0.25F);
        CubeDeformation innerArmor = new CubeDeformation(0.5F);
        CubeDeformation outerArmor = new CubeDeformation(1.0F);

        LayerDefinition innerArmorLayer = LayerDefinition.create(HumanoidModel.createMesh(innerArmor, 0.0F), 64, 32);
        LayerDefinition outerArmorLayer = LayerDefinition.create(HumanoidModel.createMesh(outerArmor, 0.0F), 64, 32);

        RendererRegistry.addLayerDefinition(RCModelLayers.TREASURE_CHEST, TreasureChestModel::createBodyLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.BURNED, BurnedModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.BURNED_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.BURNED_OUTER_ARMOR, () -> outerArmorLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.FROSTBITTEN, FrostbittenModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.FROSTBITTEN_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.FROSTBITTEN_OUTER_ARMOR, () -> outerArmorLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.SWAMPY, SwampyModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.SWAMPY_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.SWAMPY_OUTER_ARMOR, () -> outerArmorLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.UNDEAD_MINER, UndeadMinerModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.UNDEAD_MINER_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.UNDEAD_MINER_OUTER_ARMOR, () -> outerArmorLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.MUMMY, MummyModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.MUMMY_INNER_ARMOR, () -> LayerDefinition.create(HumanoidModel.createMesh(innerArmor.extend(0.25F), 0.0F), 64, 32));
        RendererRegistry.addLayerDefinition(RCModelLayers.MUMMY_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidModel.createMesh(outerArmor.extend(0.25F), 0.0F), 64, 32));

        RendererRegistry.addLayerDefinition(RCModelLayers.GLACIAL_HUNTER, GlacialHunterModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.HUNTER_WOLF, WolfModel::createBodyLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.DEAD_BEARD, DeadBeardModel::createBodyLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.ZOMBIE_LACKEY, () -> DrownedModel.createBodyLayer(none));
        RendererRegistry.addLayerDefinition(RCModelLayers.ZOMBIE_LACKEY_OUTER_LAYER, () -> DrownedModel.createBodyLayer(outerLayer));
        RendererRegistry.addLayerDefinition(RCModelLayers.ZOMBIE_LACKEY_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.ZOMBIE_LACKEY_OUTER_ARMOR, () -> outerArmorLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.SKELETON_LACKEY, SkeletonModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.SKELETON_LACKEY_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.SKELETON_LACKEY_OUTER_ARMOR, () -> outerArmorLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.IMMORTAL, ImmortalModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.IMMORTAL_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.IMMORTAL_OUTER_ARMOR, () -> outerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.IMMORTAL_OVERLAY, ImmortalOverlayModel::createLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.IMMORTAL_DASH, DashAttackLayer::createLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.ZAP, ZapModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.ZAP_INNER_ARMOR, () -> innerArmorLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.ZAP_OUTER_ARMOR, () -> outerArmorLayer);

        RendererRegistry.addLayerDefinition(RCModelLayers.SCARAB, ScarabModel::createBodyLayer);
        RendererRegistry.addLayerDefinition(RCModelLayers.FLYING_SCARAB, FlyingScarabModel::createBodyLayer);
    }
}