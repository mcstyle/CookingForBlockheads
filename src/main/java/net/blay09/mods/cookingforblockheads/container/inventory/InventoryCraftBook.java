package net.blay09.mods.cookingforblockheads.container.inventory;

import net.blay09.mods.cookingforblockheads.KitchenMultiBlock;
import net.blay09.mods.cookingforblockheads.api.capability.IKitchenItemProvider;
import net.blay09.mods.cookingforblockheads.ItemUtils;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class InventoryCraftBook extends InventoryCrafting {

	private static class SourceItem {
		private final IKitchenItemProvider sourceProvider;
		private final int sourceSlot;
		private final ItemStack sourceStack;

		public SourceItem(IKitchenItemProvider sourceProvider, int sourceSlot, ItemStack sourceStack) {
			this.sourceProvider = sourceProvider;
			this.sourceSlot = sourceSlot;
			this.sourceStack = sourceStack;
		}

		public IKitchenItemProvider getSourceProvider() {
			return sourceProvider;
		}

		public int getSourceSlot() {
			return sourceSlot;
		}

		public ItemStack getSourceStack() {
			return sourceStack;
		}
	}

	public InventoryCraftBook(Container container) {
		super(container, 3, 3);
	}

	public ItemStack tryCraft(ItemStack outputItem, List<ItemStack> craftMatrix, EntityPlayer player, KitchenMultiBlock multiBlock) {
		boolean requireContainer = CookingRegistry.doesItemRequireBucketForCrafting(outputItem);

		// Reset the simulation before we start
		List<IKitchenItemProvider> inventories = CookingRegistry.getItemProviders(multiBlock, player.inventory);
		for(IKitchenItemProvider itemProvider : inventories) {
			itemProvider.resetSimulation();
		}

		SourceItem[] sourceItems = new SourceItem[9];

		// Find matching items from source inventories
		matrixLoop:for(int i = 0; i < craftMatrix.size(); i++) {
			ItemStack ingredient = craftMatrix.get(i);
            if(!(null == ingredient)) {
                for(int j = 0; j < inventories.size(); j++) {
					IKitchenItemProvider itemProvider = inventories.get(j);
                    for (int k = 0; k < itemProvider.getSlots(); k++) {
                        ItemStack itemStack = itemProvider.getStackInSlot(k);
                        if (ItemUtils.areItemStacksEqualWithWildcard(itemStack, ingredient)) {
							itemStack = itemProvider.useItemStack(k, 1, true, inventories, requireContainer);
							if(!(null == itemStack)) {
								sourceItems[i] = new SourceItem(inventories.get(j), k, itemStack);
								continue matrixLoop;
							}
                        }
                    }
                }
            }
        }

		// Populate the crafting grid
		for(int i = 0; i < sourceItems.length; i++) {
			setInventorySlotContents(i, sourceItems[i] != null ? sourceItems[i].getSourceStack() : null);
		}

		// Find the matching recipe and make sure it matches what the client expects
		IRecipe craftRecipe = CookingRegistry.findFoodRecipe(this, player.worldObj);
		if(craftRecipe == null || craftRecipe.getRecipeOutput() == null || craftRecipe.getRecipeOutput().getItem() != outputItem.getItem()) {
			return null;
		}

		// Get the final result and remove ingredients
		ItemStack result = craftRecipe.getCraftingResult(this);
		if(!(null == result)) {
			fireEventsAndHandleAchievements(player, result);
			for(int i = 0; i < getSizeInventory(); i++) {
				ItemStack itemStack = getStackInSlot(i);
				if(!(null == itemStack)) {
					if(sourceItems[i] != null) {
						// Eat the ingredients
						IKitchenItemProvider sourceProvider = sourceItems[i].getSourceProvider();
						if(sourceItems[i].getSourceSlot() != -1) {
							sourceProvider.resetSimulation();
							sourceProvider.useItemStack(sourceItems[i].getSourceSlot(), 1, false, inventories, requireContainer);
						}

						// Return container items (like empty buckets)
						ItemStack containerItem = ForgeHooks.getContainerItem(itemStack);
						if(!(null == containerItem)) {
							ItemStack restStack = sourceProvider.returnItemStack(containerItem);
							if(!(null == restStack)) {
								ItemHandlerHelper.giveItemToPlayer(player, restStack);
							}
						}
					}
				}
			}
		}
		return result;
	}

	private void fireEventsAndHandleAchievements(EntityPlayer player, ItemStack result) {
		FMLCommonHandler.instance().firePlayerCraftingEvent(player, result, this);
		result.onCrafting(player.worldObj, player, 1);
	}


}
