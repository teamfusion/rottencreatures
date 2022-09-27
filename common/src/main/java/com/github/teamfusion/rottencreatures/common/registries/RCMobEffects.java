package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.mixin.access.MobEffectAccessor;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class RCMobEffects {
    public static final CoreRegistry<MobEffect> EFFECTS = CoreRegistry.create(Registry.MOB_EFFECT, RottenCreatures.MOD_ID);

    public static final Supplier<MobEffect> FREEZE = EFFECTS.register("freeze", () -> MobEffectAccessor.createMobEffect(MobEffectCategory.HARMFUL, 5883888));

    public static void createAreaEffectCloud(Level level, double x, double y, double z, MobEffect effect, float radius, int time) {
        AreaEffectCloud cloud = new AreaEffectCloud(level, x, y, z);
        cloud.setRadius(radius);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(time);
        cloud.setDuration(cloud.getDuration() / 2);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(effect));
        level.addFreshEntity(cloud);
    }
}