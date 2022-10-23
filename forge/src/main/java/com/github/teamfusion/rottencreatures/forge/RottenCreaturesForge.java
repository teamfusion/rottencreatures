package com.github.teamfusion.rottencreatures.forge;

import com.github.teamfusion.platform.common.worldgen.forge.BiomeManagerImpl;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraftforge.fml.common.Mod;

@Mod(RottenCreatures.MOD_ID)
public class RottenCreaturesForge {
    public RottenCreaturesForge() {
        RottenCreatures.bootstrap();
        BiomeManagerImpl.setup();
    }
}
