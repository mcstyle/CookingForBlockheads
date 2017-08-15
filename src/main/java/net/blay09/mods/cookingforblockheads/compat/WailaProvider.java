package net.blay09.mods.cookingforblockheads.compat;

import mcp.mobius.waila.api.*;
import net.blay09.mods.cookingforblockheads.block.BlockMilkJar;
import net.blay09.mods.cookingforblockheads.block.BlockToaster;
import net.blay09.mods.cookingforblockheads.tile.TileMilkJar;
import net.blay09.mods.cookingforblockheads.tile.TileToaster;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WailaProvider {

	public void register(IWailaRegistrar registrar) {
		registrar.registerBodyProvider(new MilkJarDataProvider(), BlockMilkJar.class);
		registrar.registerBodyProvider(new ToasterDataProvider(), BlockToaster.class);
	}

	public static class MilkJarDataProvider implements IWailaDataProvider {
		@Override
		public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
			return null;
		}

		@Override
		public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
			return list;
		}

		@Override
		public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
			TileEntity tileEntity = accessor.getTileEntity();
			if(tileEntity instanceof TileMilkJar) {
				TileMilkJar tileMilkJar = (TileMilkJar) tileEntity;
				list.add(I18n.format("waila.cookingforblockheads:milk_stored", (int) tileMilkJar.getMilkAmount(), (int) tileMilkJar.getMilkCapacity()));
			}
			return list;
		}

		@Override
		public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
			return list;
		}

		@Override
		public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileEntity, NBTTagCompound tagCompound, World world, BlockPos pos) {
			if(tileEntity != null) {
				tileEntity.writeToNBT(tagCompound);
			}
			return tagCompound;
		}
	}

	public static class ToasterDataProvider implements IWailaDataProvider {
		@Override
		public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
			return null;
		}

		@Override
		public List<String> getWailaHead(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
			return list;
		}

		@Override
		public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
			TileEntity tileEntity = accessor.getTileEntity();
			if(tileEntity instanceof TileToaster) {
				TileToaster tileToaster = (TileToaster) tileEntity;
				if(tileToaster.isActive()) {
					list.add(I18n.format("waila.cookingforblockheads:toast_progress", (int) (tileToaster.getToastProgress() * 100)) + "%");
				}
			}
			return list;
		}

		@Override
		public List<String> getWailaTail(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler config) {
			return list;
		}

		@Override
		public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileEntity, NBTTagCompound tagCompound, World world, BlockPos pos) {
			if(tileEntity != null) {
				tileEntity.writeToNBT(tagCompound);
			}
			return tagCompound;
		}
	}

}
