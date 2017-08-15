package net.blay09.mods.cookingforblockheads;

import java.util.List;
import java.util.Set;

import net.blay09.mods.cookingforblockheads.api.IKitchenMultiBlock;
import net.blay09.mods.cookingforblockheads.api.capability.CapabilityKitchenConnector;
import net.blay09.mods.cookingforblockheads.api.capability.CapabilityKitchenItemProvider;
import net.blay09.mods.cookingforblockheads.api.capability.CapabilityKitchenSmeltingProvider;
import net.blay09.mods.cookingforblockheads.api.capability.IKitchenItemProvider;
import net.blay09.mods.cookingforblockheads.api.capability.IKitchenSmeltingProvider;
import net.blay09.mods.cookingforblockheads.api.capability.KitchenItemProvider;
import net.blay09.mods.cookingforblockheads.block.ModBlocks;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class KitchenMultiBlock implements IKitchenMultiBlock {

	private final Set<BlockPos> checkedPos = Sets.newHashSet();
	private final List<IKitchenItemProvider> itemProviderList = Lists.newArrayList();
	private final List<IKitchenSmeltingProvider> smeltingProviderList = Lists.newArrayList();

	public KitchenMultiBlock(World world, BlockPos pos) {
		findNeighbourKitchenBlocks(world, pos);
	}

	private void findNeighbourKitchenBlocks(World world, BlockPos pos) {
		for (int i = 0; i <= 5; i++) {
			EnumFacing dir = EnumFacing.getFront(i);
			BlockPos position = pos.offset(dir);
			if (!checkedPos.contains(position)) {
				checkedPos.add(position);
				TileEntity tileEntity = world.getTileEntity(position);
				if (tileEntity != null) {
					IKitchenItemProvider itemProvider = tileEntity.getCapability(CapabilityKitchenItemProvider.CAPABILITY, null);
					if (itemProvider != null) {
						itemProviderList.add(itemProvider);
					}
					IKitchenSmeltingProvider smeltingProvider = tileEntity.getCapability(CapabilityKitchenSmeltingProvider.CAPABILITY, null);
					if (smeltingProvider != null) {
						smeltingProviderList.add(smeltingProvider);
					}
					if (itemProvider != null || smeltingProvider != null || tileEntity.hasCapability(CapabilityKitchenConnector.CAPABILITY, null)) {
						findNeighbourKitchenBlocks(world, position);
					}
				} else {
					IBlockState state = world.getBlockState(position);
					if(state.getBlock() == ModBlocks.kitchenFloor) {
						findNeighbourKitchenBlocks(world, position);
					}
				}
			}
		}
	}

	@Override
	public List<IKitchenItemProvider> getItemProviders(InventoryPlayer playerInventory) {
		List<IKitchenItemProvider> sourceInventories = Lists.newArrayList();
		sourceInventories.addAll(itemProviderList);
		sourceInventories.add(new KitchenItemProvider(new InvWrapper(playerInventory)));
		return sourceInventories;
	}

	@Override
	public ItemStack smeltItem(ItemStack itemStack, int count) {
		ItemStack restStack = itemStack.copy().splitStack(count);
		for (IKitchenSmeltingProvider provider : smeltingProviderList) {
			restStack = provider.smeltItem(restStack);
			if ((null == restStack)) {
				break;
			}
		}
		itemStack.stackSize -= (count - (!(null == restStack) ? restStack.stackSize : 0));
		return itemStack;
	}

	public void trySmelt(ItemStack outputItem, ItemStack inputItem, EntityPlayer player, boolean stack) {
		if ((null == inputItem)) {
			return;
		}
		boolean requireBucket = CookingRegistry.doesItemRequireBucketForCrafting(outputItem);
		List<IKitchenItemProvider> inventories = getItemProviders(player.inventory);
		for (IKitchenItemProvider itemProvider : inventories) {
			itemProvider.resetSimulation();
			for (int i = 0; i < itemProvider.getSlots(); i++) {
				ItemStack itemStack = itemProvider.getStackInSlot(i);
				if (ItemUtils.areItemStacksEqualWithWildcard(itemStack, inputItem)) {
					int smeltCount = Math.min(itemStack.stackSize, stack ? inputItem.getMaxStackSize() : 1);
					ItemStack restStack = itemProvider.useItemStack(i, smeltCount, false, inventories, requireBucket);
					if (!(null == restStack)) {
						restStack = smeltItem(restStack, smeltCount);
						if (!(null == restStack)) {
							restStack = itemProvider.returnItemStack(restStack);
							if (!player.inventory.addItemStackToInventory(restStack)) {
								player.dropItem(restStack, false);
							}
						}
						player.openContainer.detectAndSendChanges();
						return;
					}
				}
			}
		}

	}

	@Override
	public boolean hasSmeltingProvider() {
		return smeltingProviderList.size() > 0;
	}

}
