package com.github.teamfusion.rottencreatures.client;

import com.blackgear.platform.client.GameRendering;
import com.github.teamfusion.rottencreatures.client.level.blockentities.model.*;
import com.github.teamfusion.rottencreatures.client.level.blockentities.renderer.TreasureChestBlockRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.model.*;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.TntBarrelRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.burned.BurnedRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.deadbeard.DeadBeardRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.frostbitten.FrostbittenRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.glacialhunter.GlacialHunterRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.glacialhunter.HunterWolfRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.immortal.DashAttackLayer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.immortal.ImmortalRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.lackey.SkeletonLackeyRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.lackey.ZombieLackeyRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.mummy.MummyRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.scarab.ScarabRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.swampy.SwampyRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.undeadminer.UndeadMinerRenderer;
import com.github.teamfusion.rottencreatures.client.level.entities.renderer.zap.ZapRenderer;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.blocks.SkullTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCBlockEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;

public class Rendering {
    public static final ResourceLocation SPEAR_HANDHELD_MODEL = RottenCreatures.resource("spear_in_hand");
    public static final ResourceLocation SPEAR_BASE_MODEL = RottenCreatures.resource("spear");

    public static void modelLayerRegistry(GameRendering.ModelLayerEvent event) {
        CubeDeformation none = CubeDeformation.NONE;
        CubeDeformation outerLayer = new CubeDeformation(0.25F);
        CubeDeformation innerArmor = new CubeDeformation(0.5F);
        CubeDeformation outerArmor = new CubeDeformation(1.0F);

        LayerDefinition innerArmorLayer = LayerDefinition.create(HumanoidModel.createMesh(innerArmor, 0.0F), 64, 32);
        LayerDefinition outerArmorLayer = LayerDefinition.create(HumanoidModel.createMesh(outerArmor, 0.0F), 64, 32);

        event.register(RCModelLayers.TREASURE_CHEST, TreasureChestBlockModel::createBodyLayer);

        event.register(RCModelLayers.BURNED, BurnedModel::createBodyLayer);
        event.register(RCModelLayers.BURNED_HEAD, BurnedSkullModel::createMobHeadLayer);
        event.register(RCModelLayers.BURNED_INNER_ARMOR, () -> BurnedModel.createArmorLayer(innerArmor));
        event.register(RCModelLayers.BURNED_OUTER_ARMOR, () -> BurnedModel.createArmorLayer(new CubeDeformation(0.95F)));

        event.register(RCModelLayers.FROSTBITTEN, FrostbittenModel::createBodyLayer);
        event.register(RCModelLayers.FROSTBITTEN_HEAD, FrostbittenSkullModel::createMobHeadLayer);
        event.register(RCModelLayers.FROSTBITTEN_INNER_ARMOR, () -> innerArmorLayer);
        event.register(RCModelLayers.FROSTBITTEN_OUTER_ARMOR, () -> outerArmorLayer);

        event.register(RCModelLayers.SWAMPY, SwampyModel::createBodyLayer);
        event.register(RCModelLayers.SWAMPY_HEAD, SwampySkullModel::createMobHeadLayer);
        event.register(RCModelLayers.SWAMPY_INNER_ARMOR, () -> innerArmorLayer);
        event.register(RCModelLayers.SWAMPY_OUTER_ARMOR, () -> outerArmorLayer);

        event.register(RCModelLayers.UNDEAD_MINER, UndeadMinerModel::createBodyLayer);
        event.register(RCModelLayers.UNDEAD_MINER_HEAD, UndeadMinerSkullModel::createMobHeadLayer);
        event.register(RCModelLayers.UNDEAD_MINER_INNER_ARMOR, () -> innerArmorLayer);
        event.register(RCModelLayers.UNDEAD_MINER_OUTER_ARMOR, () -> outerArmorLayer);

        event.register(RCModelLayers.MUMMY, MummyModel::createBodyLayer);
        event.register(RCModelLayers.MUMMY_HEAD, MummySkullModel::createMobHeadLayer);
        event.register(RCModelLayers.MUMMY_INNER_ARMOR, () -> LayerDefinition.create(HumanoidModel.createMesh(innerArmor.extend(0.25F), 0.0F), 64, 32));
        event.register(RCModelLayers.MUMMY_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidModel.createMesh(outerArmor.extend(0.25F), 0.0F), 64, 32));

        event.register(RCModelLayers.GLACIAL_HUNTER, GlacialHunterModel::createBodyLayer);
        event.register(RCModelLayers.GLACIAL_HUNTER_HEAD, GlacialHunterSkullModel::createMobHeadLayer);
        event.register(RCModelLayers.HUNTER_WOLF, () -> LayerDefinition.create(WolfModel.createMeshDefinition(CubeDeformation.NONE), 64, 32));

        event.register(RCModelLayers.DEAD_BEARD, DeadBeardModel::createBodyLayer);
        event.register(RCModelLayers.DEAD_BEARD_HEAD, DeadBeardSkullModel::createMobHeadLayer);

        event.register(RCModelLayers.ZOMBIE_LACKEY, () -> DrownedModel.createBodyLayer(none));
        event.register(RCModelLayers.ZOMBIE_LACKEY_OUTER_LAYER, () -> DrownedModel.createBodyLayer(outerLayer));
        event.register(RCModelLayers.ZOMBIE_LACKEY_INNER_ARMOR, () -> innerArmorLayer);
        event.register(RCModelLayers.ZOMBIE_LACKEY_OUTER_ARMOR, () -> outerArmorLayer);

        event.register(RCModelLayers.SKELETON_LACKEY, SkeletonModel::createBodyLayer);
        event.register(RCModelLayers.SKELETON_LACKEY_INNER_ARMOR, () -> innerArmorLayer);
        event.register(RCModelLayers.SKELETON_LACKEY_OUTER_ARMOR, () -> outerArmorLayer);

        event.register(RCModelLayers.IMMORTAL, ImmortalModel::createBodyLayer);
        event.register(RCModelLayers.IMMORTAL_HEAD, ImmortalSkullModel::createMobHeadLayer);
        event.register(RCModelLayers.IMMORTAL_INNER_ARMOR, () -> innerArmorLayer);
        event.register(RCModelLayers.IMMORTAL_OUTER_ARMOR, () -> outerArmorLayer);
        event.register(RCModelLayers.IMMORTAL_OVERLAY, ImmortalOverlayModel::createLayer);
        event.register(RCModelLayers.IMMORTAL_DASH, DashAttackLayer::createLayer);

        event.register(RCModelLayers.ZAP, ZapModel::createBodyLayer);
        event.register(RCModelLayers.ZAP_HEAD, ZapSkullModel::createMobHeadLayer);
        event.register(RCModelLayers.ZAP_INNER_ARMOR, () -> innerArmorLayer);
        event.register(RCModelLayers.ZAP_OUTER_ARMOR, () -> outerArmorLayer);

        event.register(RCModelLayers.SCARAB, ScarabModel::createBodyLayer);
    }

    public static void entityRendererRegistry(GameRendering.EntityRendererEvent event) {
        event.register(RCEntityTypes.TNT_BARREL.get(), TntBarrelRenderer::new);

        event.register(RCEntityTypes.BURNED.get(), BurnedRenderer::new);
        event.register(RCEntityTypes.FROSTBITTEN.get(), FrostbittenRenderer::new);
        event.register(RCEntityTypes.SWAMPY.get(), SwampyRenderer::new);
        event.register(RCEntityTypes.UNDEAD_MINER.get(), UndeadMinerRenderer::new);
        event.register(RCEntityTypes.MUMMY.get(), MummyRenderer::new);
        event.register(RCEntityTypes.GLACIAL_HUNTER.get(), GlacialHunterRenderer::new);
        event.register(RCEntityTypes.HUNTER_WOLF.get(), HunterWolfRenderer::new);
        event.register(RCEntityTypes.DEAD_BEARD.get(), DeadBeardRenderer::new);
        event.register(RCEntityTypes.ZOMBIE_LACKEY.get(), ZombieLackeyRenderer::new);
        event.register(RCEntityTypes.SKELETON_LACKEY.get(), SkeletonLackeyRenderer::new);
        event.register(RCEntityTypes.IMMORTAL.get(), ImmortalRenderer::new);
        event.register(RCEntityTypes.ZAP.get(), ZapRenderer::new);
        event.register(RCEntityTypes.SCARAB.get(), ScarabRenderer::new);
    }

    public static void blockEntityRendererRegistry(GameRendering.BlockEntityRendererEvent event) {
        event.register(RCBlockEntityTypes.TREASURE_CHEST.get(), TreasureChestBlockRenderer::new);
        event.register(RCBlockEntityTypes.SKULL.get(), SkullBlockRenderer::new);
    }

    public static void blockRendererRegistry(GameRendering.BlockRendererEvent event) {
        event.register(RenderType.cutout(), RCBlocks.TREASURE_CHEST.get());
    }

    public static void skullRendererRegistry(GameRendering.SkullRendererEvent event) {
        event.registerSkullModel(SkullTypes.BURNED, BurnedSkullModel::new, RCModelLayers.BURNED_HEAD);
        event.registerSkullTexture(SkullTypes.BURNED, RottenCreatures.resource("textures/entity/burned/burned.png"));

        event.registerSkullModel(SkullTypes.FROSTBITTEN, FrostbittenSkullModel::new, RCModelLayers.FROSTBITTEN_HEAD);
        event.registerSkullTexture(SkullTypes.FROSTBITTEN, RottenCreatures.resource("textures/entity/frostbitten.png"));

        event.registerSkullModel(SkullTypes.SWAMPY, SwampySkullModel::new, RCModelLayers.SWAMPY_HEAD);
        event.registerSkullTexture(SkullTypes.SWAMPY, RottenCreatures.resource("textures/entity/swampy.png"));

        event.registerSkullModel(SkullTypes.UNDEAD_MINER, UndeadMinerSkullModel::new, RCModelLayers.UNDEAD_MINER_HEAD);
        event.registerSkullTexture(SkullTypes.UNDEAD_MINER, RottenCreatures.resource("textures/entity/undead_miner/undead_miner_stone.png"));

        event.registerSkullModel(SkullTypes.MUMMY, MummySkullModel::new, RCModelLayers.MUMMY_HEAD);
        event.registerSkullTexture(SkullTypes.MUMMY, RottenCreatures.resource("textures/entity/mummy/mummy.png"));

        event.registerSkullModel(SkullTypes.GLACIAL_HUNTER, GlacialHunterSkullModel::new, RCModelLayers.GLACIAL_HUNTER_HEAD);
        event.registerSkullTexture(SkullTypes.GLACIAL_HUNTER, RottenCreatures.resource("textures/entity/glacial_hunter.png"));

        event.registerSkullModel(SkullTypes.DEAD_BEARD, DeadBeardSkullModel::new, RCModelLayers.DEAD_BEARD_HEAD);
        event.registerSkullTexture(SkullTypes.DEAD_BEARD, RottenCreatures.resource("textures/entity/dead_beard.png"));

        event.registerSkullModel(SkullTypes.IMMORTAL, ImmortalSkullModel::new, RCModelLayers.IMMORTAL_HEAD);
        event.registerSkullTexture(SkullTypes.IMMORTAL, RottenCreatures.resource("textures/entity/immortal/immortal.png"));

        event.registerSkullModel(SkullTypes.ZAP, ZapSkullModel::new, RCModelLayers.ZAP_HEAD);
        event.registerSkullTexture(SkullTypes.ZAP, RottenCreatures.resource("textures/entity/zap.png"));
    }

    public static void handHeldModelRegistry(GameRendering.HandHeldModelEvent event) {
        event.register(RCItems.SPEAR.get(), SPEAR_BASE_MODEL, SPEAR_HANDHELD_MODEL);
    }
}