package com.github.teamfusion.rottencreatures.common.level.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class PrimedTntBarrel extends Entity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(PrimedTntBarrel.class, EntityDataSerializers.INT);
    @Nullable private LivingEntity owner;

    public PrimedTntBarrel(EntityType<?> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    public PrimedTntBarrel(Level level, double x, double y, double z, @Nullable LivingEntity owner) {
        this(RCEntityTypes.TNT_BARREL.get(), level);
        this.setPos(x, y, z);
        double offset = level.random.nextDouble() * 6.3F;
        this.setDeltaMovement(-Math.sin(offset) * 0.02, 0.2F, -Math.cos(offset) * 0.02);
        this.setFuse(80);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.owner = owner;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));

        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int cooldown = this.getFuse() - 1;
        this.setFuse(cooldown);

        if (cooldown <= 0) {
            this.discard();
            if (!this.level.isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(
                    ParticleTypes.SMOKE,
                    this.getX(),
                    this.getY() + 0.5,
                    this.getZ(),
                    0.0, 0.0, 0.0
                );
            }
        }
    }

    private void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625), this.getZ(), 4.0F, Explosion.BlockInteraction.BREAK);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putShort("Fuse", (short) this.getFuse());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setFuse(tag.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.15F;
    }

    public void setFuse(int delay) {
        this.entityData.set(DATA_FUSE_ID, delay);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}