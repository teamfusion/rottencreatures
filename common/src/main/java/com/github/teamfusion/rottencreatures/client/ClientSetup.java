package com.github.teamfusion.rottencreatures.client;

import com.blackgear.platform.core.ParallelDispatch;

public class ClientSetup {
    public static void client() {
        EntityRenderers.registerModelLayers();
        EntityRenderers.registerEntityRenderers();
    }

    public static void postClient(ParallelDispatch dispatch) {}
}