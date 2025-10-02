package com.github.teamfusion.rottencreatures.core.neoforge;

import net.neoforged.fml.common.Mod;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;

@Mod(RottenCreatures.MOD_ID)
public final class RottenCreaturesNeoForge {
    public RottenCreaturesNeoForge() {
        // Run our common setup.
        RottenCreatures.bootstrap();
    }
}
