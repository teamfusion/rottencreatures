package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.LootBuilder;
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
    private static final LootBuilder BUILDER = LootBuilder.of("undead_miner");
    public static final ResourceLocation DIAMOND_LOOT = BUILDER.build("diamond");
    public static final ResourceLocation IRON_LOOT = BUILDER.build("iron");
    public static final ResourceLocation STONE_LOOT = BUILDER.build("stone");
    public static final ResourceLocation GOLD_LOOT = BUILDER.build("gold");

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
     * - Diamond-Rank : 10%
     * - Iron-Rank : 30%
     * - Stone-Rank : 60%
     * - Gold-Rank : 60% but only in mesa biome
     */
    public Variant getRandomVariant(Random random, Holder<Biome> biome) {
        int chance = random.nextInt(50);
        if (chance <= 15) {
            return Variant.IRON;
        } else {
            return chance >= 45 ? Variant.DIAMOND : biome.is(BiomeTags.IS_BADLANDS) ? Variant.GOLD : Variant.STONE;
        }
    }

    /**
     * applies a custom loot table depending on the rank of the miner
     */
    @Override
    protected ResourceLocation getDefaultLootTable() {
        return switch (this.getVariant()) {
            case DIAMOND -> DIAMOND_LOOT;
            case IRON -> IRON_LOOT;
            case STONE -> STONE_LOOT;
            case GOLD -> GOLD_LOOT;
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
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || !level.canSeeSky(pos)) && pos.getY() < level.getSeaLevel();
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