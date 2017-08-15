package net.blay09.mods.cookingforblockheads;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class ItemUtils {

	private static final Random rand = new Random();

	public static void dropItemHandlerItems(World world, BlockPos pos, IItemHandler itemHandler) {
		dropContent(world, pos, itemHandler);
//		for (int i = 0; i < itemHandler.getSlots(); i++) {
//			ItemStack itemStack = itemHandler.getStackInSlot(i);
//			if (!(null == itemStack)) {
//				spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
//			}
//		}
	}

	public static void spawnItemStack(World world, double x, double y, double z, ItemStack stack) {
		float offsetX = rand.nextFloat() * 0.8F + 0.1F;
		float offsetY = rand.nextFloat() * 0.8F + 0.1F;
		float offsetZ = rand.nextFloat() * 0.8F + 0.1F;

		while (!(null == stack)) {
			EntityItem entityitem = new EntityItem(world, x + (double) offsetX, y + (double) offsetY, z + (double) offsetZ, stack.splitStack(rand.nextInt(21) + 10));
			float motion = 0.05F;
			entityitem.motionX = rand.nextGaussian() * motion;
			entityitem.motionY = rand.nextGaussian() * motion + 0.2;
			entityitem.motionZ = rand.nextGaussian() * motion;
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static boolean areItemStacksEqualWithWildcard(ItemStack first, ItemStack second) {
		return !((null == first) || (null == second)) && first.getItem() == second.getItem() && (first.getItemDamage() == second.getItemDamage() || first.getItemDamage() == OreDictionary.WILDCARD_VALUE || second.getItemDamage() == OreDictionary.WILDCARD_VALUE);
	}

	public static void dropContent(World world, BlockPos pos, IItemHandler itemHandler) {
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			ItemStack itemStack = itemHandler.getStackInSlot(i);
			if (itemStack != null) {
				float offsetX = world.rand.nextFloat() * 0.8f + 0.1f;
				float offsetY = world.rand.nextFloat() * 0.8f + 0.1f;
				EntityItem entityItem;
				while (itemStack.stackSize > 0) {
					float offsetZ = world.rand.nextFloat() * 0.8f + 0.1f;
					int stackSize = Math.min(world.rand.nextInt(21) + 10, itemStack.stackSize);
					itemStack.stackSize -= stackSize;

					entityItem = new EntityItem(world, (double) (pos.getX() + offsetX), (double) (pos.getY() + offsetY), (double) (pos.getZ() + offsetZ), new ItemStack(itemStack.getItem(), stackSize, itemStack.getItemDamage()));
					float motion = 0.05f;
					entityItem.motionX = world.rand.nextGaussian() * motion;
					entityItem.motionY = world.rand.nextGaussian() * motion + 0.2f;
					entityItem.motionZ = world.rand.nextGaussian() * motion;

					NBTTagCompound tagCompound = itemStack.getTagCompound();
					if (tagCompound != null) {
						entityItem.getEntityItem().setTagCompound(tagCompound.copy());
					}
					world.spawnEntityInWorld(entityItem);
				}
			}
		}
	}

	public static void dropItem(World world, BlockPos pos, ItemStack itemStack) {
		if(itemStack == null) {
			return;
		}
		float offsetX = world.rand.nextFloat() * 0.8f + 0.1f;
		float offsetY = world.rand.nextFloat() * 0.8f + 0.1f;
		while (itemStack.stackSize > 0) {
			float offsetZ = world.rand.nextFloat() * 0.8f + 0.1f;
			int stackSize = Math.min(itemStack.stackSize, world.rand.nextInt(21) + 10);
			itemStack.stackSize -= stackSize;

			EntityItem entityItem = new EntityItem(world, (double) (pos.getX() + offsetX), (double) (pos.getY() + offsetY), (double) (pos.getZ() + offsetZ), new ItemStack(itemStack.getItem(), stackSize, itemStack.getItemDamage()));
			float motion = 0.05f;
			entityItem.motionX = world.rand.nextGaussian() * motion;
			entityItem.motionY = world.rand.nextGaussian() * motion + 0.2f;
			entityItem.motionZ = world.rand.nextGaussian() * motion;
			if (itemStack.hasTagCompound()) {
				entityItem.getEntityItem().setTagCompound(itemStack.getTagCompound().copy());
			}
			world.spawnEntityInWorld(entityItem);
		}
	}

}
