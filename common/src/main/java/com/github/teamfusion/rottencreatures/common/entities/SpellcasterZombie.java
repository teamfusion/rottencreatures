package com.github.teamfusion.rottencreatures.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public abstract class SpellcasterZombie extends Zombie {
    protected int spellCastingTickCount;

    public SpellcasterZombie(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.spellCastingTickCount = tag.getInt("SpellTicks");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SpellTicks", this.spellCastingTickCount);
    }

    public boolean isCastingSpell() {
        return this.spellCastingTickCount > 0;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.spellCastingTickCount > 0) --this.spellCastingTickCount;
    }

    protected int getSpellCastingTime() {
        return this.spellCastingTickCount;
    }

    protected class CastingSpellGoal extends Goal {
        public CastingSpellGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return SpellcasterZombie.this.getSpellCastingTime() > 0;
        }

        @Override
        public void start() {
            super.start();
//            SpellcasterZombie.this.navigation.stop();
        }

        @Override
        public void tick() {
            if (SpellcasterZombie.this.getTarget() != null) {
                SpellcasterZombie.this.getLookControl().setLookAt(SpellcasterZombie.this.getTarget(), (float)SpellcasterZombie.this.getMaxHeadYRot(), (float)SpellcasterZombie.this.getMaxHeadXRot());
            }
        }
    }

    protected abstract class UseSpellGoal extends Goal {
        protected int attackWarmupDelay;
        protected int nextAttackTickCount;

        @Override
        public boolean canUse() {
            LivingEntity target = SpellcasterZombie.this.getTarget();
            if (target == null || !target.isAlive()) {
                return false;
            } else if (SpellcasterZombie.this.isCastingSpell()) {
                return false;
            } else {
                return SpellcasterZombie.this.tickCount >= this.nextAttackTickCount;
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = SpellcasterZombie.this.getTarget();
            return target != null && target.isAlive() && this.attackWarmupDelay > 0;
        }

        @Override
        public void start() {
            this.attackWarmupDelay = this.adjustedTickDelay(this.getCastWarmupTime());
            SpellcasterZombie.this.spellCastingTickCount = this.getCastingTime();
            this.nextAttackTickCount = SpellcasterZombie.this.tickCount + this.getCastingInterval();
        }

        @Override
        public void tick() {
            --this.attackWarmupDelay;
            if (this.attackWarmupDelay == 0) {
                this.performSpellCasting();
            }
        }

        protected abstract void performSpellCasting();

        protected int getCastWarmupTime() {
            return 20;
        }

        protected abstract int getCastingTime();

        protected abstract int getCastingInterval();
    }
}