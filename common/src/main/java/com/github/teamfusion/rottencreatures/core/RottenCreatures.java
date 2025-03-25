package com.github.teamfusion.rottencreatures.core;

import com.blackgear.platform.core.Environment;
import com.blackgear.platform.core.ModInstance;
import com.blackgear.platform.core.util.config.ModConfig;
import com.github.teamfusion.rottencreatures.client.ClientSetup;
import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
import com.github.teamfusion.rottencreatures.common.CommonSetup;
import com.github.teamfusion.rottencreatures.common.registries.*;
import com.github.teamfusion.rottencreatures.core.data.tags.RCBiomeTags;
import com.github.teamfusion.rottencreatures.core.data.tags.RCEntityTypeTags;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class RottenCreatures {
    public static final String MOD_ID = "rottencreatures";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final CommonConfig CONFIG = Environment.registerSafeConfig(MOD_ID, ModConfig.Type.COMMON, CommonConfig::new);
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID)
        .common(CommonSetup::setup)
        .postCommon(CommonSetup::asyncSetup)
        .client(ClientSetup::setup)
        .postClient(ClientSetup::asyncSetup)
        .build();

    public static void bootstrap() {
        INSTANCE.bootstrap();

        RCSoundEvents.SOUNDS.register();

        RCItems.ITEMS.register();
        RCBlocks.BLOCKS.register();
        RCBlockEntityTypes.BLOCK_ENTITIES.register();

        RCMobEffects.EFFECTS.register();
        RCEntityTypes.ENTITIES.register();

        RCPotions.POTIONS.register();

        RCBiomeTags.TAGS.register();
        RCEntityTypeTags.TAGS.register();
    }

    public static ResourceLocation resource(String path) {
        return new ResourceLocation(RottenCreatures.MOD_ID, path);
    }
}