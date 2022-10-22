package com.github.teamfusion.rottencreatures.mixin.common;

import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow protected abstract BlockPos findLightningTargetAround(BlockPos blockPos);

    private final ServerLevel $this = ServerLevel.class.cast(this);

    /**
     * for each lightning that is struck, there's a 5% chance to spawn an Immortal
     */
    @Inject(method = "tickChunk", at = @At("TAIL"))
    private void rc$tickChunk(LevelChunk chunk, int i, CallbackInfo ci) {
        ChunkPos chunkPos = chunk.getPos();
        boolean isRaining = $this.isRaining();
        int x = chunkPos.getMinBlockX();
        int z = chunkPos.getMinBlockZ();
        if (isRaining && $this.isThundering() && $this.random.nextInt(100000) == 0) {
            BlockPos pos = this.findLightningTargetAround($this.getBlockRandomPos(x, 0, z, 15));
            if ($this.isRainingAt(pos)) {
                DifficultyInstance difficulty = $this.getCurrentDifficultyAt(pos);
                boolean canSpawn = $this.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && $this.random.nextDouble() < (double)difficulty.getEffectiveDifficulty() * 0.025D && !$this.getBlockState(pos.below()).is(Blocks.LIGHTNING_ROD);
                if (canSpawn) {
                    Immortal immortal = RCEntityTypes.IMMORTAL.get().create($this);
                    immortal.setPos(pos.getX(), pos.getY(), pos.getZ());
                    $this.addFreshEntity(immortal);
                }
            }
        }
    }

    @Inject(method = "findLightningTargetAround", at = @At("TAIL"), cancellable = true)
    private void rc$findLightningTargetAround(BlockPos pos, CallbackInfoReturnable<BlockPos> cir) {
        LivingEntity entity = $this.getNearestPlayer(TargetingConditions.forNonCombat().range(64), pos.getX(), pos.getY(), pos.getZ());
        if (entity != null && entity.hasEffect(RCMobEffects.CHANNELLED.get()) && $this.random.nextFloat() <= 0.02F * entity.getEffect(RCMobEffects.CHANNELLED.get()).getAmplifier() + 1 && $this.canSeeSky(entity.blockPosition())) {
            cir.setReturnValue(entity.blockPosition());
        }
    }
}