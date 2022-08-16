package com.github.teamfusion.platform.fabric;

import com.github.teamfusion.platform.ModInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class ModInstanceBuilderImpl {
    public static ModInstance builder(String modId, Runnable common, Runnable postCommon, Runnable client, Runnable postClient) {
        return new ModInstance(modId, common, postCommon, client, postClient) {
            @Override
            public void bootstrap() {
                this.onCommon.run();
                this.onPostCommon.run();
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                    this.onClient.run();
                    this.onPostClient.run();
                }
            }
        };
    }
}