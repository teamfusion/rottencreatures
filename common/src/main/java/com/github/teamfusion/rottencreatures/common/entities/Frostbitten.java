package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.Random;

/**
 * - has the Freeze effect when he hits the user or another mob, if the entity has leather armor this doesn't apply. //
 * - can walk over the water like the FrostWalker enchantment of boots //
 * - if a baby died, he'll explode applying freezing effect to the nearest entity. a TINY explosion of snow that doesn't break anything. //
 *
 * - if a zombie dies in powder snow, he transforms into a Frostbitten
 */
public class Frostbitten extends Zombie {
    public Frostbitten(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.22D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Burned.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);
        if (hurt && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
            if (!living.getItemBySlot(EquipmentSlot.HEAD).is(Items.LEATHER_HELMET) && !living.getItemBySlot(EquipmentSlot.CHEST).is(Items.LEATHER_CHESTPLATE) && !living.getItemBySlot(EquipmentSlot.LEGS).is(Items.LEATHER_LEGGINGS) && !living.getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS)) {
                float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                living.addEffect(new MobEffectInstance(RCMobEffects.FREEZE.get(), 140 * (int)modifier), this);
            }
        }

        return hurt;
    }

    @Override
    protected void onChangedBlock(BlockPos pos) {
        super.onChangedBlock(pos);
        FrostWalkerEnchantment.onEntityMoved(this, this.level, pos, 2);
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
        if (this.isBaby()) {
            if (!this.level.isClientSide && this.deathTime == 20)
                RCMobEffects.spawnLingeringCloud(this, MobEffects.POISON);
//                RCMobEffects.createAreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ(), RCMobEffects.FREEZE.get(), 2.5F, 3);
            this.level.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getRandomY(), this.getZ(), this.random.nextDouble(-0.15, 0.15), 0.0D, this.random.nextDouble(-0.15, 0.15));
        }
    }

    public static boolean checkFrostbittenSpawnRules(EntityType<Frostbitten> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWN_EGG || level.canSeeSky(pos));
    }
}