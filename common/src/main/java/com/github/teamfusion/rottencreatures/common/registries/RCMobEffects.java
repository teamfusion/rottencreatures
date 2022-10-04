package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.mixin.access.MobEffectAccessor;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Freeze Effect:
 * - applies the frozen hearts
 * - applies frozen screen
 * - applies shaking to the entity model
 * - prevents the target from moving or attacking
 */
public class RCMobEffects {
    public static final CoreRegistry<MobEffect> EFFECTS = CoreRegistry.create(Registry.MOB_EFFECT, RottenCreatures.MOD_ID);

    public static final Supplier<MobEffect> FREEZE = EFFECTS.register("freeze", () -> MobEffectAccessor.createMobEffect(MobEffectCategory.HARMFUL, 5883888));

    public static void createAreaEffectCloud(Level level, double x, double y, double z, MobEffect effect, float radius, int time) {
        createAreaEffectCloud(level, x, y, z, effect, null, radius, time);
    }

    public static void createAreaEffectCloud(Level level, double x, double y, double z, MobEffect effect, @Nullable ParticleOptions particle, float radius, int time) {
        AreaEffectCloud cloud = new AreaEffectCloud(level, x, y, z);
        cloud.setRadius(radius);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(time);
        cloud.setDuration(cloud.getDuration() / 2);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(effect));
        if (particle != null) cloud.setParticle(particle);
        level.addFreshEntity(cloud);
    }

    public static void spawnLingeringCloud(LivingEntity entity, MobEffect effect, float radius, int time) {
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        AreaEffectCloud cloud = new AreaEffectCloud(entity.level, entity.getX(), entity.getY(), entity.getZ());
        cloud.setRadiusPerTick(radius);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(time);
        cloud.setDuration(cloud.getDuration() / 2);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        if (!effects.isEmpty()) for (MobEffectInstance instance : effects) cloud.addEffect(new MobEffectInstance(instance));
        cloud.addEffect(new MobEffectInstance(effect));
        entity.level.addFreshEntity(cloud);
    }

    public static void spawnLingeringCloud(LivingEntity entity, MobEffect effect) {
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        AreaEffectCloud cloud = new AreaEffectCloud(entity.level, entity.getX(), entity.getY(), entity.getZ());
        cloud.setRadiusPerTick(3.0F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setDuration(cloud.getDuration() / 2);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        if (!effects.isEmpty()) for (MobEffectInstance instance : effects) cloud.addEffect(new MobEffectInstance(instance));
        cloud.addEffect(new MobEffectInstance(effect));
        entity.level.addFreshEntity(cloud);
    }
}