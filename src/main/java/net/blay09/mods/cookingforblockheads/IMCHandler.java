package net.blay09.mods.cookingforblockheads;

import net.blay09.mods.cookingforblockheads.api.CookingForBlockheadsAPI;
import net.blay09.mods.cookingforblockheads.api.ToastErrorHandler;
import net.blay09.mods.cookingforblockheads.api.ToastOutputHandler;
import net.blay09.mods.cookingforblockheads.api.event.FoodRegistryInitEvent;
import net.blay09.mods.cookingforblockheads.balyware.NonNullList;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IMCHandler {

	private static final NonNullList<ItemStack> imcNonFoodRecipes = NonNullList.create();

	public static ItemStack ItemStackFromCompund(NBTTagCompound p_i47263_1_) {
		ItemStack item = new ItemStack(p_i47263_1_.hasKey("id", 8)? Item.getByNameOrId(p_i47263_1_.getString("id")):Item.getItemFromBlock(Blocks.AIR));
		item.deserializeNBT(p_i47263_1_);
		item.stackSize = p_i47263_1_.getByte("Count");

		return item;
	}

	public static void handleIMCMessage(FMLInterModComms.IMCEvent event) {
		for(FMLInterModComms.IMCMessage message : event.getMessages()) {
			switch(message.key) {
				case "RegisterTool":
					if(message.getMessageType() == ItemStack.class) {
						CookingForBlockheadsAPI.addToolItem(message.getItemStackValue());
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterTool expected message of type ItemStack");
					}
					break;
				case "RegisterWaterItem":
					if(message.getMessageType() == ItemStack.class) {
						CookingForBlockheadsAPI.addWaterItem(message.getItemStackValue());
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterWaterItem expected message of type ItemStack");
					}
					break;
				case "RegisterMilkItem":
					if(message.getMessageType() == ItemStack.class) {
						CookingForBlockheadsAPI.addMilkItem(message.getItemStackValue());
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterMilkItem expected message of type ItemStack");
					}
					break;
				case "RegisterToast":
					if(message.getMessageType() == NBTTagCompound.class) {
						ItemStack inputItem = ItemStackFromCompund(message.getNBTValue().getCompoundTag("Input"));
						final ItemStack outputItem = ItemStackFromCompund(message.getNBTValue().getCompoundTag("Output"));
						if(!(null == inputItem) && !(null == outputItem)) {
							CookingForBlockheadsAPI.addToastHandler(inputItem, (ToastOutputHandler) itemStack -> outputItem);
						} else {
							CookingForBlockheads.logger.error("IMC API Error: RegisterToast expected message of type NBT with structure {Input : ItemStack, Output : ItemStack}");
						}
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterToast expected message of type NBT");
					}
					break;
				case "RegisterToastError":
					if(message.getMessageType() == NBTTagCompound.class) {
						ItemStack inputItem = ItemStackFromCompund(message.getNBTValue().getCompoundTag("Input"));
						final String langKey = message.getNBTValue().getString("Message");
						if(!(null == inputItem) && !(null == langKey)) {
							CookingForBlockheadsAPI.addToastHandler(inputItem, (ToastErrorHandler) (player, itemStack) -> new TextComponentTranslation(langKey));
						} else {
							CookingForBlockheads.logger.error("IMC API Error: RegisterToastError expected message of type NBT with structure {Input : ItemStack, Message : String}");
						}
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterToastError expected message of type NBT");
					}
					break;
				case "RegisterOvenFuel":
					if(message.getMessageType() == NBTTagCompound.class) {
						ItemStack inputItem = ItemStackFromCompund(message.getNBTValue().getCompoundTag("Input"));
						if(!(null == inputItem) && message.getNBTValue().hasKey("FuelValue", Constants.NBT.TAG_ANY_NUMERIC)) {
							CookingForBlockheadsAPI.addOvenFuel(inputItem, message.getNBTValue().getInteger("FuelValue"));
						} else {
							CookingForBlockheads.logger.error("IMC API Error: RegisterOvenFuel expected message of type NBT with structure {Input : ItemStack, FuelValue : numeric}");
						}
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterOvenFuel expected message of type NBT");
					}
					break;
				case "RegisterOvenRecipe":
					if(message.getMessageType() == NBTTagCompound.class) {
						ItemStack inputItem = ItemStackFromCompund(message.getNBTValue().getCompoundTag("Input"));
						ItemStack outputItem = ItemStackFromCompund(message.getNBTValue().getCompoundTag("Output"));
						if(!(null == inputItem) && !(null == outputItem)) {
							CookingForBlockheadsAPI.addOvenRecipe(inputItem, outputItem);
						} else {
							CookingForBlockheads.logger.error("IMC API Error: RegisterOvenRecipe expected message of type NBT with structure {Input : ItemStack, Output : ItemStack}");
						}
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterOvenRecipe expected message of type NBT");
					}
					break;
				case "RegisterNonFoodRecipe":
					if(message.getMessageType() == ItemStack.class) {
						imcNonFoodRecipes.add(message.getItemStackValue());
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterNonFoodRecipe expected message of type ItemStack");
					}
					break;
				case "RegisterCowClass":
					if(message.getMessageType() == String.class) {
						try {
							Class<?> clazz = Class.forName(message.getStringValue());
							CowJarHandler.registerCowClass(clazz);
						} catch (ClassNotFoundException e) {
							CookingForBlockheads.logger.error("Could not register cow class " + message.getStringValue() + ": " + e.getMessage());
							e.printStackTrace();
						}
					} else {
						CookingForBlockheads.logger.error("IMC API Error: RegisterCowClass expected message of type String");
					}
					break;
			}
		}
	}

	@SubscribeEvent
	public void onFoodRegistryInit(FoodRegistryInitEvent event) {
		for(ItemStack itemStack : imcNonFoodRecipes) {
			event.registerNonFoodRecipe(itemStack);
		}
	}

}
