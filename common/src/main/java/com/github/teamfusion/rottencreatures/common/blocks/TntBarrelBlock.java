package com.github.teamfusion.rottencreatures.common.blocks;

import com.github.teamfusion.rottencreatures.common.entities.PrimedTntBarrel;
import com.github.teamfusion.rottencreatures.core.mixin.access.FireworkRocketEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class TntBarrelBlock extends Block {
    public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

    public TntBarrelBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(UNSTABLE, false));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState onState, boolean bl) {
        if (onState.is(state.getBlock())) return;
        if (level.hasNeighborSignal(pos)) {
            explode(level, pos, true);
            level.removeBlock(pos, false);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos newPos, boolean bl) {
        if (level.hasNeighborSignal(pos)) {
            level.removeBlock(pos, false);
            explode(level, pos, true);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && !player.isCreative() && state.getValue(UNSTABLE)) explode(level, pos, false);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            PrimedTntBarrel tnt = new PrimedTntBarrel(level, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, explosion.getSourceMob());
            tnt.setFuse((short)(level.random.nextInt(tnt.getFuse() / 4) + tnt.getFuse() / 8));
            level.addFreshEntity(tnt);
        }
    }

    public static void explode(Level level, BlockPos pos, boolean immediately) {
        explode(level, pos, null, immediately);
    }

    private static void explode(Level level, BlockPos pos, @Nullable LivingEntity entity) {
        explode(level, pos, entity, false);
    }

    /**
     * primes the tnt barrel block and checks if it should explode with a delay or instantly
     */
    private static void explode(Level level, BlockPos pos, @Nullable LivingEntity entity, boolean immediately) {
        if (!level.isClientSide) {
            PrimedTntBarrel tnt = new PrimedTntBarrel(level, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, entity);
            if (immediately) tnt.setFuse(0);
            level.addFreshEntity(tnt);
            level.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(entity, GameEvent.PRIME_FUSE, pos);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE)) {
            explode(level, pos, player);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            Item item = stack.getItem();
            if (!player.isCreative()) {
                if (stack.is(Items.FLINT_AND_STEEL)) {
                    stack.hurtAndBreak(1, player, user -> user.broadcastBreakEvent(hand));
                } else {
                    stack.shrink(1);
                }
            }

            player.awardStat(Stats.ITEM_USED.get(item));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (stack.is(Items.FIREWORK_ROCKET)) {
            Direction direction = hitResult.getDirection().getOpposite();
            if (!level.isClientSide) {
                FireworkRocketEntity firework = new FireworkRocketEntity(level, stack, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, true);
                ((FireworkRocketEntityAccessor)firework).setLifetime(80);
                int offset = stack.getOrCreateTagElement("Fireworks").getByte("Flight") - 1;
                firework.shoot(direction.getStepX(), direction.getStepY() + (offset * 0.125D), direction.getStepZ(), 0.5F, 1.0F);
                level.addFreshEntity(firework);

                PrimedTntBarrel tnt = new PrimedTntBarrel(level, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, player);
                tnt.startRiding(firework);
                level.addFreshEntity(tnt);

                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);

                level.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.PRIME_FUSE, pos);
            }

            if (!player.isCreative()) stack.shrink(1);

            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    /**
     * if a projectile on fire reaches the block, it marks it an unstable and primes it
     */
    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos pos = hitResult.getBlockPos();
            Entity entity = projectile.getOwner();
            if (projectile.isOnFire() && projectile.mayInteract(level, pos)) {
                explode(level, pos, entity instanceof LivingEntity living ? living : null);
                level.removeBlock(pos, false);
            }
        }
    }

    /**
     * prevents the block dropping on explosion
     */
    @Override
    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UNSTABLE);
    }
}