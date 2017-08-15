package net.blay09.mods.cookingforblockheads.item;

import net.blay09.mods.cookingforblockheads.CookingForBlockheads;
import net.blay09.mods.cookingforblockheads.balyware.NonNullList;
import net.blay09.mods.cookingforblockheads.network.handler.GuiHandler;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRecipeBook extends Item {

	public static final String name = "recipe_book";
	public static final ResourceLocation registryName = new ResourceLocation(CookingForBlockheads.MOD_ID, name);

	public ItemRecipeBook() {
		setCreativeTab(CookingForBlockheads.creativeTab);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	protected boolean isInCreativeTab(CreativeTabs p_isInCreativeTab_1_) {
		CreativeTabs[] creativetabs = this.getCreativeTabs();
		int var3 = creativetabs.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			CreativeTabs tab = creativetabs[var4];
			if(tab == p_isInCreativeTab_1_) {
				return true;
			}
		}

		CreativeTabs var6 = this.getCreativeTab();
		return var6 != null && (p_isInCreativeTab_1_ == CreativeTabs.SEARCH || p_isInCreativeTab_1_ == var6);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "item.cookingforblockheads:recipe_book_tier" + itemstack.getItemDamage();
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
		if (isInCreativeTab(tab)) {
			subItems.add(new ItemStack(item, 1, 0));
			subItems.add(new ItemStack(item, 1, 1));
			subItems.add(new ItemStack(item, 1, 2));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
		player.openGui(CookingForBlockheads.instance, GuiHandler.ITEM_RECIPE_BOOK, world, hand.ordinal(), 0, 0);
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> tooltip, boolean isShiftDown) {
		super.addInformation(itemStack, player, tooltip, isShiftDown);

		tooltip.add(TextFormatting.YELLOW + I18n.format("tooltip.cookingforblockheads:recipe_book_tier" + itemStack.getItemDamage()));
		for (String s : I18n.format("tooltip.cookingforblockheads:recipe_book_tier" + itemStack.getItemDamage() + ".description").split("\\\\n")) {
			tooltip.add(TextFormatting.GRAY + s);
		}
	}

}
