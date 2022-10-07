package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.mixin.access.BuiltInLootTablesAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class UndeadMiner extends Zombie {
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(UndeadMiner.class, EntityDataSerializers.INT);
    public static final ResourceLocation A_RANK = BuiltInLootTablesAccessor.callRegister(new ResourceLocation(RottenCreatures.MOD_ID, "entities/undead_miner/a_rank"));
    public static final ResourceLocation B_RANK = BuiltInLootTablesAccessor.callRegister(new ResourceLocation(RottenCreatures.MOD_ID, "entities/undead_miner/b_rank"));
    public static final ResourceLocation C_RANK = BuiltInLootTablesAccessor.callRegister(new ResourceLocation(RottenCreatures.MOD_ID, "entities/undead_miner/c_rank"));
    public static final ResourceLocation M_RANK = BuiltInLootTablesAccessor.callRegister(new ResourceLocation(RottenCreatures.MOD_ID, "entities/undead_miner/m_rank"));

    public UndeadMiner(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ARMOR, 2.0D);
    }

    /**
     * allows the miner to avoid the sun if possible
     */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getVariant().getId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setVariant(Variant.BY_ID[tag.getInt("Variant")]);
    }

    public Variant getVariant() {
        return Variant.BY_ID[this.entityData.get(DATA_VARIANT)];
    }

    private void setVariant(Variant variant) {
        this.entityData.set(DATA_VARIANT, variant.getId());
    }

    /**
     * prevents babies from generating, no child labor!
     */
    @Override
    public boolean isBaby() {
        return false;
    }

    /**
     * checks for the miner variant to apply a different pickaxe
     */
    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(difficulty);
        for (Variant variant : Variant.values()) {
            if (this.getVariant() == variant) {
                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(variant.getPickaxe()));
            }
        }
    }

    /**
     * applies random values to generate miners with different ranks
     * also checks if it should generate the mesa miner
     *
     * - A-Rank : 10%
     * - B-Rank : 30%
     * - C-Rank : 60%
     * - M-Rank : 60% but only in mesa biome
     */
    public Variant getRandomVariant(Random random, Holder<Biome> biome) {
        int chance = random.nextInt(50);
        if (chance <= 15) {
            return Variant.B;
        } else {
            return chance >= 45 ? Variant.A : biome.is(BiomeTags.IS_BADLANDS) ? Variant.M : Variant.C;
        }
    }

    /**
     * applies a custom loot table depending on the rank of the miner
     */
    @Override
    protected ResourceLocation getDefaultLootTable() {
        return switch (this.getVariant()) {
            case A -> A_RANK;
            case B -> B_RANK;
            case C -> C_RANK;
            case M -> M_RANK;
        };
    }

    /**
     * generates a miner depending on a random value and the located biome
     */
    @Nullable @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        this.setVariant(this.getRandomVariant(level.getRandom(), level.getBiome(this.blockPosition())));
        return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
    }

    public static boolean checkUndeadMinerSpawnRules(EntityType<UndeadMiner> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWN_EGG || !level.canSeeSky(pos)) && pos.getY() < level.getSeaLevel();
    }

    /**
     * manages each variant per rank
     */
    public enum Variant {
        C(0, "c", Items.STONE_PICKAXE),
        B(1, "b", Items.IRON_PICKAXE),
        A(2, "a", Items.DIAMOND_PICKAXE),
        M(3, "m", Items.GOLDEN_PICKAXE);

        public static final Variant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(Variant::getId)).toArray(Variant[]::new);
        private final int id;
        private final String name;
        private final Item pickaxe;

        Variant(int id, String name, Item pickaxe) {
            this.id = id;
            this.name = name;
            this.pickaxe = pickaxe;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public Item getPickaxe() {
            return this.pickaxe;
        }
    }
}