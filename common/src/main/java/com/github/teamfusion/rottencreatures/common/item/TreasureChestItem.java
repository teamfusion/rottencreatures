package com.github.teamfusion.rottencreatures.common.item;

import com.github.teamfusion.rottencreatures.common.entities.TreasureChest;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TreasureChestItem extends Item {
    public TreasureChestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level level = context.getLevel();
            BlockPos pos = new BlockPlaceContext(context).getClickedPos();
            ItemStack stack = context.getItemInHand();
            Vec3 position = Vec3.atBottomCenterOf(pos);
            AABB boundingBox = RCEntityTypes.TREASURE_CHEST.get().getDimensions().makeBoundingBox(position.x(), position.y(), position.z());
            if (level.noCollision(null, boundingBox) && level.getEntities(null, boundingBox).isEmpty()) {
                if (level instanceof ServerLevel server) {
                    TreasureChest chest = RCEntityTypes.TREASURE_CHEST.get().create(server, stack.getTag(), null, context.getPlayer(), pos, MobSpawnType.SPAWN_EGG, true, true);
                    if (chest == null) {
                        return InteractionResult.FAIL;
                    }

                    float yaw = (float) Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    chest.moveTo(chest.getX(), chest.getY(), chest.getZ(), yaw, 0.0F);
                    server.addFreshEntityWithPassengers(chest);
                    level.playSound(null, chest.getX(), chest.getY(), chest.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    chest.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
                }

                stack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}