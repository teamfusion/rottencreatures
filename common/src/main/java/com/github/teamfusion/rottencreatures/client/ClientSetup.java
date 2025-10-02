package com.github.teamfusion.rottencreatures.client;

import com.blackgear.platform.client.GameRendering;
import com.blackgear.platform.core.ParallelDispatch;

public class ClientSetup {
    public static void setup() {
        GameRendering.registerModelLayers(Rendering::modelLayerRegistry);
        GameRendering.registerEntityRenderers(Rendering::entityRendererRegistry);
        GameRendering.registerBlockEntityRenderers(Rendering::blockEntityRendererRegistry);
        VanillaCreativeTabIntegration.modifyVanillaCreativeTabs();
    }

    public static void asyncSetup(ParallelDispatch dispatch) {
        GameRendering.registerHandHeldModels(Rendering::handHeldModels);
        GameRendering.registerSkullRenderers(Rendering::skullRendererRegistry);
        GameRendering.registerBlockRenderers(Rendering::blockRendererRegistry);
    }
}