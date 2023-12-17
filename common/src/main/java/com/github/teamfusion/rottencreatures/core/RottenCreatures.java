package com.github.teamfusion.rottencreatures.core;

import com.github.teamfusion.rottencreatures.core.platform.Environment;
import com.github.teamfusion.rottencreatures.core.platform.ModInstance;
import com.github.teamfusion.rottencreatures.client.ClientSetup;
import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
import com.github.teamfusion.rottencreatures.common.CommonSetup;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import com.github.teamfusion.rottencreatures.common.registries.RCPotions;
import com.github.teamfusion.rottencreatures.core.data.RCBiomeTags;
import com.github.teamfusion.rottencreatures.core.data.RCEntityTypeTags;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;

public class RottenCreatures {
    public static final String MOD_ID = "rottencreatures";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID)
            .common(CommonSetup::common)
            .postCommon(CommonSetup::postCommon)
            .client(ClientSetup::client)
            .postClient(ClientSetup::postClient)
            .build();
    public static final CreativeModeTab TAB = Environment.createTab(
            new ResourceLocation(MOD_ID, MOD_ID),
            () -> new ItemStack(Items.ROTTEN_FLESH)
    );

    public static void bootstrap() {
        // ========== MOD INITIALIZATION ===============================================================================
        INSTANCE.bootstrap();

        // ========== MISCELLANEOUS REGISTRY ===========================================================================
        RCBlocks.BLOCKS.register();
        RCItems.ITEMS.register();
        RCMobEffects.EFFECTS.register();
        RCPotions.POTIONS.register();
        RCSoundEvents.SOUNDS.register();

        // ========== ENTITY REGISTRY ==================================================================================
        RCEntityTypes.ENTITIES.register();

        // ========== TAG REGISTRY =====================================================================================
        RCBiomeTags.TAGS.register();
        RCEntityTypeTags.TAGS.register();
    }
}