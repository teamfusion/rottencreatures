package com.github.teamfusion.rottencreatures.common;

import com.github.teamfusion.platform.common.MobHandler;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;

public class CommonSetup {
    public static void common() {
        MobHandler.registerAttributes(RCEntityTypes.BURNED, Burned::createAttributes);
    }

    public static void postCommon() {}
}