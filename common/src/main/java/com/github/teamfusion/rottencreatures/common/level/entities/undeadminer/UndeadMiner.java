package com.github.teamfusion.rottencreatures.common.level.entities.undeadminer;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.data.loot.RCLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public class UndeadMiner extends Zombie {
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(UndeadMiner.class, EntityDataSerializers.INT);

    public UndeadMiner(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 6;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
            .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0)
            .add(Attributes.MAX_HEALTH, 24.0)
            .add(Attributes.ATTACK_DAMAGE, 4.0)
            .add(Attributes.ARMOR, 2.0);
    }

    /**
     * allows the miner to avoid the sun if possible
     */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
    }

    /**
     * prevents miners to burn at sunlight
     */
    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT, 0);
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

    @Override
    public void setBaby(boolean baby) {
        // Ensure no babies are generated
    }

    /**
     * checks for the miner variant to apply a different pickaxe
     */
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        Arrays.stream(Variant.values())
            .filter(variant -> this.getVariant() == variant)
            .forEach(variant -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(variant.getPickaxe())));
    }

    /**
     * applies random values to generate miners with different ranks
     * also checks if it should generate the mesa miner
     * - Diamond-Rank : 10%
     * - Iron-Rank : 30%
     * - Stone-Rank : 60%
     * - Gold-Rank : 60% but only in mesa biome
     */
    public Variant getRandomVariant(RandomSource random, Holder<Biome> biome) {
        int chance = random.nextInt(50);

        if (chance <= 15) {
            return Variant.IRON;
        } else if (chance >= 45) {
            return Variant.DIAMOND;
        } else if (biome.is(BiomeTags.IS_BADLANDS)) {
            return Variant.GOLD;
        } else {
            return Variant.STONE;
        }
    }

    /**
     * applies a custom loot table depending on the rank of the miner
     */
    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return switch (this.getVariant()) {
            case DIAMOND -> RCLootTables.UNDEAD_DIAMOND_MINER;
            case IRON -> RCLootTables.UNDEAD_IRON_MINER;
            case STONE -> RCLootTables.UNDEAD_STONE_MINER;
            case GOLD -> RCLootTables.UNDEAD_GOLD_MINER;
        };
    }

    @Override
    protected ItemStack getSkull() {
        return new ItemStack(RCBlocks.UNDEAD_MINER_HEAD.get());
    }

    /**
     * generates a miner depending on a random value and the located biome
     */
    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setVariant(this.getRandomVariant(level.getRandom(), level.getBiome(this.blockPosition())));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public static boolean checkUndeadMinerSpawnRules(EntityType<UndeadMiner> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || !level.canSeeSky(pos)) && pos.getY() <= RottenCreatures.CONFIG.undeadMinerDepth.get();
    }

    /**
     * manages each variant per rank
     */
    public enum Variant {
        STONE(0, "stone", Items.STONE_PICKAXE),
        IRON(1, "iron", Items.IRON_PICKAXE),
        DIAMOND(2, "diamond", Items.DIAMOND_PICKAXE),
        GOLD(3, "gold", Items.GOLDEN_PICKAXE);

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