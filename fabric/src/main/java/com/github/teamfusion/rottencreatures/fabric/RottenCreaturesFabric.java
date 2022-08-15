package com.github.teamfusion.rottencreatures.fabric;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.fabricmc.api.ModInitializer;

public class RottenCreaturesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RottenCreatures.bootstrap();
    }
}
