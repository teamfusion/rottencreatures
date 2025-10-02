package com.github.teamfusion.rottencreatures.common.level.entities.deadbeard;

import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
import com.github.teamfusion.rottencreatures.common.level.blockentities.TreasureChestBlockEntity;
import com.github.teamfusion.rottencreatures.common.level.entities.SpellcasterZombie;
import com.github.teamfusion.rottencreatures.common.level.entities.lackey.Lackey;
import com.github.teamfusion.rottencreatures.common.level.entities.lackey.SkeletonLackey;
import com.github.teamfusion.rottencreatures.common.level.entities.lackey.ZombieLackey;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.core.data.loot.RCLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class DeadBeard extends SpellcasterZombie {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(DeadBeard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IGNITED = SynchedEntityData.defineId(DeadBeard.class, EntityDataSerializers.BOOLEAN);

    public DeadBeard(EntityType<? extends SpellcasterZombie> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
            .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0)
            .add(Attributes.MAX_HEALTH, 70.0)
            .add(Attributes.MOVEMENT_SPEED, 0.275)
            .add(Attributes.ATTACK_DAMAGE, 5.0)
            .add(Attributes.ARMOR, 2.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new CastingSpellGoal());
        this.goalSelector.addGoal(4, new SummonLackeysGoal());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FUSE_ID, 100);
        builder.define(DATA_IGNITED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("Fuse", (short) this.getFuse());
        tag.putBoolean("IsIgnited", this.isIgnited());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFuse(tag.getShort("Fuse"));
        this.setIgnited(tag.getBoolean("IsIgnited"));
    }

    public void setFuse(int ticks) {
        this.getEntityData().set(DATA_FUSE_ID, ticks);
    }

    public int getFuse() {
        return this.getEntityData().get(DATA_FUSE_ID);
    }

    public void setIgnited(boolean ignited) {
        this.getEntityData().set(DATA_IGNITED, ignited);
    }

    public boolean isIgnited() {
        return this.getEntityData().get(DATA_IGNITED);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    /**
     * the captain's hat protects him from sunlight!
     */
    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void setBaby(boolean baby) {

    }

    private void explode() {
        this.level().explode(
            this,
            this.getX(),
            this.getY(0.0625),
            this.getZ(),
            4.0F,
            Level.ExplosionInteraction.MOB
        );
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);

        if (recentlyHit) {
            // Drop TNT Barrel if ignited
            if (this.isIgnited()) {
                this.spawnAtLocation(RCBlocks.TNT_BARREL.get());
            }

            // Always drop Treasure Chest with random loot
            ItemStack chestStack = new ItemStack(RCBlocks.TREASURE_CHEST.get());
            CompoundTag customTag = new CompoundTag();

            // Set DeadBeard as the owner
            customTag.putUUID(TreasureChestBlockEntity.TAG_OWNER_UUID, this.getUUID());

            if (this.level() instanceof ServerLevel server) {
                // Get loot from loot table
                LootParams builder = createTreasureLootContext(source, server);
                LootTable lootTable = server.getServer().reloadableRegistries().getLootTable(RCLootTables.TREASURE_CHEST);
                List<ItemStack> lootItems = lootTable.getRandomItems(builder);

                // Find first valid item max stack size using streams
                int targetMaxStackSize = lootItems.stream()
                    .filter(item -> !item.isEmpty())
                    .findFirst()
                    .map(ItemStack::getMaxStackSize)
                    .orElse(-1);

                // Only proceed if we found a valid item
                if (targetMaxStackSize > 0) {
                    ListTag itemsList = new ListTag();
                    int totalCount = 0;

                    // Add all compatible items up to max capacity
                    for (ItemStack item : lootItems) {
                        // Skip empty or incompatible items
                        if (item.isEmpty() || item.getMaxStackSize() != targetMaxStackSize) {
                            continue;
                        }

                        // Stop if we've reached the limit
                        if (totalCount >= targetMaxStackSize) {
                            break;
                        }

                        // Calculate how many we can add
                        int toAdd = Math.min(item.getCount(), targetMaxStackSize - totalCount);

                        // Create a copy with the appropriate count
                        ItemStack stackToAdd = item.copy();
                        stackToAdd.setCount(toAdd);

                        // Save to NBT
                        // Replace with this code:
                        CompoundTag itemTag = new CompoundTag();
                        itemTag.putString("id", BuiltInRegistries.ITEM.getKey(stackToAdd.getItem()).toString());
                        itemTag.putInt("Count", stackToAdd.getCount());

                        CustomData data = stackToAdd.get(DataComponents.CUSTOM_DATA);
                        if (data != null && !data.isEmpty()) {
                            itemTag.put("tag", data.copyTag());
                        }

                        itemsList.add(itemTag);

                        totalCount += toAdd;
                    }

                    // Store the items in the chest's NBT
                    customTag.put(TreasureChestBlockEntity.TAG_ITEMS, itemsList);
                    customTag.putInt("ItemCount", totalCount);
                    customTag.putInt(TreasureChestBlockEntity.TAG_MAX_STACK_SIZE, targetMaxStackSize);
                }
            }

            // Set the BlockEntityTag and spawn the chest
            chestStack.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));

            this.spawnAtLocation(chestStack);
        }
    }

    private LootParams createTreasureLootContext(DamageSource source, ServerLevel server) {
        LootParams.Builder builder = new LootParams.Builder(server)
            .withParameter(LootContextParams.THIS_ENTITY, this)
            .withParameter(LootContextParams.ORIGIN, this.position())
            .withParameter(LootContextParams.DAMAGE_SOURCE, source)
            .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, source.getEntity())
            .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, source.getDirectEntity());

        if (this.lastHurtByPlayer != null) {
            builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
        }

        return builder.create(LootContextParamSets.ENTITY);
    }

    @Override
    protected ItemStack getSkull() {
        return new ItemStack(RCBlocks.DEAD_BEARD_HEAD.get());
    }

    /**
     * checks for the health of dead beard to see if it should ignite the TNT Barrel
     */
    @Override
    public void tick() {
        super.tick();

        if (!this.isIgnited() && this.getHealth() <= 10.0 && !this.isDeadOrDying()) {
            this.setIgnited(true);
            this.setFuse(100);
            this.level().playSound(null, this.blockPosition(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        if (this.isIgnited() && !this.isDeadOrDying()) {
            int cooldown = this.getFuse() - 1;
            this.setFuse(cooldown);
            if (cooldown <= 0) {
                this.discard();
                this.explode();
            }
        }
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        return !this.isIgnited() && super.canAttack(entity);
    }

    /**
     * prevents dead beard to turn into a drowned if it lays underwater for too long
     */
    @Override
    protected boolean convertsInWater() {
        return false;
    }

    public static boolean checkDeadBeardSpawnRules(EntityType<DeadBeard> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || level.canSeeSky(pos));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RCSoundEvents.DEAD_BEARD_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return RCSoundEvents.DEAD_BEARD_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return RCSoundEvents.DEAD_BEARD_DEATH.get();
    }

    class SummonLackeysGoal extends UseSpellGoal {
        private final TargetingConditions lackeyCountTargeting = TargetingConditions.forNonCombat().range(16.0).ignoreLineOfSight().ignoreInvisibilityTesting();

        /**
         * checks if there are still any lackeys around before summoning another bunch
         */
        @Override
        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else {
                int zombies = level().getNearbyEntities(ZombieLackey.class, this.lackeyCountTargeting, DeadBeard.this, getBoundingBox().inflate(16.0)).size();
                int skeletons = level().getNearbyEntities(SkeletonLackey.class, this.lackeyCountTargeting, DeadBeard.this, getBoundingBox().inflate(16.0)).size();
                return random.nextInt(4) + 1 > (zombies + skeletons);
            }
        }

        /**
         * summons up to 4 lackeys to defend him, also applies resistance II to himself for 5 seconds
         */
        @Override
        protected void performSpellCasting() {
            ServerLevel server = (ServerLevel) level();

            server.playSound(null, blockPosition(), RCSoundEvents.DEAD_BEARD_CALL.get(), SoundSource.HOSTILE, 1.0F, 1.0F);

            for (int i = 0; i <= random.nextInt(4); i++) {
                BlockPos pos = blockPosition().offset(-2 + random.nextInt(5), -1, -2 + random.nextInt(5));
                Monster lackey = random.nextBoolean() ? RCEntityTypes.ZOMBIE_LACKEY.get().create(server) : RCEntityTypes.SKELETON_LACKEY.get().create(server);

                if (lackey instanceof Lackey lackeyIn) {
                    lackey.moveTo(pos, 0.0F, 0.0F);
                    lackey.setDeltaMovement(0.0, 0.5, 0.0);
                    server.playSound(null, lackey.blockPosition(), SoundEvents.GRAVEL_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    lackey.finalizeSpawn(server, server.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null);
                    lackeyIn.setLimitedLife(500);
                    server.addFreshEntity(lackey);
                }
            }

            addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1));
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 340;
        }
    }
}