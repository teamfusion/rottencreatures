package com.github.teamfusion.rottencreatures;

import com.blackgear.platform.common.CreativeTabs;
import com.blackgear.platform.core.Environment;
import com.blackgear.platform.core.ModInstance;
import com.blackgear.platform.core.util.config.ModConfig;
import com.github.teamfusion.rottencreatures.client.ClientSetup;
import com.github.teamfusion.rottencreatures.common.CommonSetup;
import com.github.teamfusion.rottencreatures.common.misc.RCFoodProperties;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import com.github.teamfusion.rottencreatures.common.registries.RCPotions;
import com.github.teamfusion.rottencreatures.data.RCBiomeTags;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;

/**
 * Mob Properties
 * <a href="https://docs.google.com/spreadsheets/d/10VWOjZ1Z_yEU101hk-EzwyDLUDU6RKzs/edit#gid=1618430498">...</a>
 */
public class RottenCreatures {
    public static final String MOD_ID = "rottencreatures";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CommonConfig CONFIG = Environment.registerSafeConfig(MOD_ID, ModConfig.Type.COMMON, CommonConfig::new);
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID)
        .common(CommonSetup::common)
        .postCommon(CommonSetup::postCommon)
        .client(ClientSetup::client)
        .postClient(ClientSetup::postClient)
        .build();
    public static final CreativeModeTab TAB = CreativeTabs.create(resource(MOD_ID), Items.ROTTEN_FLESH::getDefaultInstance);

    public static void bootstrap() {
        INSTANCE.bootstrap();

        RCMobEffects.EFFECTS.register();
        RCEntityTypes.ENTITIES.register();

        RCBlocks.BLOCKS.register();
        RCItems.ITEMS.register();

        RCPotions.POTIONS.register();
        RCBiomeTags.TAGS.register();
    }

    public static ResourceLocation resource(String path) {
        return new ResourceLocation(RottenCreatures.MOD_ID, path);
    }
}