package com.github.teamfusion.platform.config.fabric;

import com.github.teamfusion.platform.Environment;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
//        return Environment.isLoaded("cloth_config") ? parent -> AutoConfig.getConfigScreen(ConfigImpl.class, parent).get() : ModMenuApi.super.getModConfigScreenFactory();
        return parent -> AutoConfig.getConfigScreen(ConfigImpl.class, parent).get();
    }
}