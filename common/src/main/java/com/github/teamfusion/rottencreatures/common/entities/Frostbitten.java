package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.List;
import java.util.Random;

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
            if (!living.canFreeze()) {
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
    public void die(DamageSource source) {
        if (this.isBaby()) {
            for (int i = 0; i < 8; i++) {
                this.level.addParticle(ParticleTypes.CLOUD, this.getX(), this.getRandomY(), this.getZ(), -0.15D + this.random.nextDouble(0.45D), 0.0D, -0.15D + this.random.nextDouble(0.45D));
            }

            if (!this.level.isClientSide) {
                MobEffect freeze = RCMobEffects.FREEZE.get();
                List<ServerPlayer> players = ((ServerLevel)this.level).getPlayers(player -> this.distanceToSqr(player) < 10.0F && player.gameMode.isSurvival());
                for (ServerPlayer player : players) {
                    if (!player.hasEffect(freeze)) {
                        player.addEffect(new MobEffectInstance(freeze, 100), this);
                    }
                }
            }
        }

        super.die(source);
    }

    public static boolean checkFrostbittenSpawnRules(EntityType<Frostbitten> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWN_EGG || level.canSeeSky(pos));
    }
}