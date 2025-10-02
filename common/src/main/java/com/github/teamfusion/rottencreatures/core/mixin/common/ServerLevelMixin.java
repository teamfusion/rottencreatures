package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.level.entities.immortal.Immortal;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow protected abstract BlockPos findLightningTargetAround(BlockPos blockPos);

    @Unique private final ServerLevel self = ServerLevel.class.cast(this);

    /**
     * for each lightning that is struck, there's a 5% chance to spawn an Immortal
     */
    @Inject(method = "tickChunk", at = @At("TAIL"))
    private void rc$tickChunk(LevelChunk chunk, int i, CallbackInfo ci) {
        ChunkPos chunkPos = chunk.getPos();
        boolean isRaining = self.isRaining();
        int x = chunkPos.getMinBlockX();
        int z = chunkPos.getMinBlockZ();

        if (isRaining && self.isThundering() && self.random.nextInt(100000) == 0) {
            BlockPos pos = this.findLightningTargetAround(self.getBlockRandomPos(x, 0, z, 15));
            if (self.isRainingAt(pos)) {
                DifficultyInstance difficulty = self.getCurrentDifficultyAt(pos);
                boolean canSpawn = self.random.nextDouble() < (double) difficulty.getEffectiveDifficulty() * RottenCreatures.CONFIG.immortalChance.get();

                if (self.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && !self.getBlockState(pos.below()).is(Blocks.LIGHTNING_ROD) && canSpawn) {
                    Immortal immortal = RCEntityTypes.IMMORTAL.get().create(self);
                    if (immortal != null) {
                        immortal.setPos(pos.getX(), pos.getY(), pos.getZ());
                        self.addFreshEntity(immortal);
                    }
                }
            }
        }
    }

    /**
     * checks for nearby players that have the channelled effect active.
     * since the only way of getting this effect is being attacked by an Immortal or Zap. I see no use on checking for any living entity that is not a player.
     */
    @Inject(method = "findLightningTargetAround", at = @At("TAIL"), cancellable = true)
    private void rc$findLightningTargetAround(BlockPos pos, CallbackInfoReturnable<BlockPos> cir) {
        LivingEntity entity = self.getNearestPlayer(TargetingConditions.forNonCombat().range(64), pos.getX(), pos.getY(), pos.getZ());
        if (entity == null) return;

        boolean hasEffect = entity.hasEffect(RCMobEffects.CHANNELLED.getHolder().get()) && self.random.nextFloat() <= 0.02F * entity.getEffect(RCMobEffects.CHANNELLED.getHolder().get()).getAmplifier() + 1;

        if (hasEffect && self.canSeeSky(entity.blockPosition())) {
            cir.setReturnValue(entity.blockPosition());
        }
    }
}