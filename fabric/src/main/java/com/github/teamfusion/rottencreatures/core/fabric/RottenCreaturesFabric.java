package com.github.teamfusion.rottencreatures.core.fabric;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class RottenCreaturesFabric implements ModInitializer {
    public static final ConfigEntriesImpl CONFIG = AutoConfig.register(ConfigEntriesImpl.class, GsonConfigSerializer::new).getConfig();

    @Override
    public void onInitialize() {
        RottenCreatures.bootstrap();
    }
}