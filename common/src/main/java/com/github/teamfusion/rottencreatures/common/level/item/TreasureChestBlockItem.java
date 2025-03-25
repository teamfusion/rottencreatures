package com.github.teamfusion.rottencreatures.common.level.item;

import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreasureChestBlockItem extends BlockItem {
    public TreasureChestBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    public void displayTreasureChestContents(ItemStack stack, @Nullable Player player, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag compoundTag = stack.getTagElement("BlockEntityTag");
        if (compoundTag != null) {
            // First check if the player is the owner
            boolean isOwner = player != null && compoundTag.hasUUID("Owner") &&
                player.getUUID().equals(compoundTag.getUUID("Owner"));

            // Only show the content details to the owner
            if (isOwner) {
                if (compoundTag.contains("Items", Tag.TAG_LIST)) {
                    ListTag itemsList = compoundTag.getList("Items", Tag.TAG_COMPOUND);
                    int displayCount = 0;
                    int totalItems = 0;

                    for (int i = 0; i < itemsList.size(); i++) {
                        CompoundTag itemTag = itemsList.getCompound(i);
                        ItemStack itemStack = ItemStack.of(itemTag);
                        if (!itemStack.isEmpty()) {
                            totalItems++;
                            if (displayCount < 5) {
                                displayCount++;
                                MutableComponent itemText = itemStack.getHoverName().copy();
                                itemText.append(" x").append(String.valueOf(itemStack.getCount()));
                                tooltip.add(itemText);
                            }
                        }
                    }

                    if (totalItems - displayCount > 0) {
                        tooltip.add(Component.translatable("container.shulkerBox.more", totalItems - displayCount).withStyle(ChatFormatting.ITALIC));
                    }
                }
            } else {
                tooltip.add(Component.translatable(LangConstants.TREASURE_CHEST_HIDDEN_CONTENTS).withStyle(ChatFormatting.GRAY));
            }
        }
    }
}