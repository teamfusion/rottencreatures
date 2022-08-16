package com.github.teamfusion.rottencreatures.client;

import com.github.teamfusion.platform.client.RenderHandler;
import com.github.teamfusion.rottencreatures.client.renderer.entity.TntBarrelRenderer;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;

public class ClientSetup {
    public static void client() {
        RenderHandler.setEntityRenderer(RCEntityTypes.TNT_BARREL, TntBarrelRenderer::new);
    }

    public static void postClient() {}
}