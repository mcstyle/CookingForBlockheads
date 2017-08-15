package net.blay09.mods.cookingforblockheads.block;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockKitchenFloor extends Block {

	public static final String name = "kitchen_floor";
	public static final ResourceLocation registryName = new ResourceLocation(CookingForBlockheads.MOD_ID, name);

	public BlockKitchenFloor() {
		super(Material.ROCK);

		setUnlocalizedName(registryName.toString());
		setSoundType(SoundType.STONE);
		setCreativeTab(CookingForBlockheads.creativeTab);
		setHardness(5f);
		setResistance(10f);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		tooltip.add(TextFormatting.YELLOW + I18n.format("tooltip." + CookingForBlockheads.MOD_ID + ":multiblock_kitchen"));
		for (String s : I18n.format("tooltip." + registryName + ".description").split("\\\\n")) {
			tooltip.add(TextFormatting.GRAY + s);
		}
	}
}
