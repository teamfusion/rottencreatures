package com.github.teamfusion.rottencreatures.client.renderer.item;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.item.SpearItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * fabric only! forge does not support ModifyArgs yet, don't be like forge..
 */
public class SpearItemRenderer {
    public static final ModelResourceLocation INVENTORY_MODEL = new ModelResourceLocation(new ResourceLocation(RottenCreatures.MOD_ID, "spear"), "inventory");
    public static final ModelResourceLocation INVENTORY_HANDHELD_MODEL = new ModelResourceLocation(new ResourceLocation(RottenCreatures.MOD_ID, "spear_in_hand"), "inventory");

    public static BakedModel renderItem(ItemStack stack, ItemTransforms.TransformType type) {
        boolean isInventory = type == ItemTransforms.TransformType.GUI || type == ItemTransforms.TransformType.GROUND || type == ItemTransforms.TransformType.FIXED;
        if (isInventory && stack.getItem() instanceof SpearItem) {
            ModelManager manager = Minecraft.getInstance().getModelManager();
            return manager.getModel(INVENTORY_MODEL);
        }

        return null;
    }
}