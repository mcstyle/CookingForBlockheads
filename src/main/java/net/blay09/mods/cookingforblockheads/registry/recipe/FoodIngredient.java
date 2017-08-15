package net.blay09.mods.cookingforblockheads.registry.recipe;

import net.blay09.mods.cookingforblockheads.ItemUtils;
import net.minecraft.item.ItemStack;

public class FoodIngredient {

    private final ItemStack[] itemStacks;
    private final boolean isToolItem;

    public FoodIngredient(ItemStack itemStack, boolean isToolItem) {
        this(new ItemStack[] { itemStack }, isToolItem);
    }

    public FoodIngredient(ItemStack[] itemStacks, boolean isToolItem) {
        this.itemStacks = itemStacks;
        this.isToolItem = isToolItem;
    }

    public boolean isValidItem(ItemStack itemStack) {
        for(ItemStack oreStack : itemStacks) {
            if(ItemUtils.areItemStacksEqualWithWildcard(oreStack, itemStack)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack[] getItemStacks() {
        return itemStacks;
    }

    public boolean isToolItem() {
        return isToolItem;
    }

}
