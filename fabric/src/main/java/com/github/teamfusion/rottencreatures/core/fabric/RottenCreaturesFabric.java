package com.github.teamfusion.rottencreatures.core.fabric;

import net.fabricmc.api.ModInitializer;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;

public final class RottenCreaturesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        RottenCreatures.bootstrap();
    }
}
