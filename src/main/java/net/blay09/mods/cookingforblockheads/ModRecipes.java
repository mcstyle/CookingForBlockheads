package net.blay09.mods.cookingforblockheads;

import net.blay09.mods.cookingforblockheads.block.ModBlocks;
import net.blay09.mods.cookingforblockheads.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

	public static void load() {
//		GameRegistry.addSmelting(Items.BOOK, new ItemStack(ModItems.recipeBook, 1, 1), 0f);
//		GameRegistry.addSmelting(new ItemStack(ModItems.recipeBook, 1, 0), new ItemStack(ModItems.recipeBook, 1, 1), 0f);

//		boolean noFilterEdition = config.getBoolean("NoFilter Edition", "items", true, "");
//		boolean craftingEdition = config.getBoolean("Cooking for Blockheads II", "items", true, "");

		// #NoFilter Edition
		//if(noFilterEdition) {
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.recipeBook, 1, 0), new ItemStack(ModItems.recipeBook, 1, 1));
		//}

		// Cooking for Blockheads I
		//f(config.getBoolean("Cooking for Blockheads I", "items", true, "")) {
			GameRegistry.addSmelting(Items.BOOK, new ItemStack(ModItems.recipeBook, 1, 1), 0f);
			GameRegistry.addSmelting(new ItemStack(ModItems.recipeBook, 1, 0), new ItemStack(ModItems.recipeBook, 1, 1), 0f);
		//}

		// Cooking for Blockheads II
		//if(craftingEdition) {
			GameRegistry.addRecipe(new ItemStack(ModItems.recipeBook, 1, 2), " D ", "CBC", " D ", 'C', Blocks.CRAFTING_TABLE, 'D', Items.DIAMOND, 'B', new ItemStack(ModItems.recipeBook, 1, 1));
			GameRegistry.addRecipe(new ItemStack(ModItems.recipeBook, 1, 2), " C ", "DBD", " C ", 'C', Blocks.CRAFTING_TABLE, 'D', Items.DIAMOND, 'B', new ItemStack(ModItems.recipeBook, 1, 1));
		//}

		// Fridge
		//if(config.getBoolean("Fridge", "blocks", true, "")) {
			GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.fridge), Blocks.CHEST, Items.IRON_DOOR);
		//}

		// Sink
		//if(config.getBoolean("Sink", "blocks", true, "")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.sink), "III", "WBW", "WWW", 'I', "ingotIron", 'W', "logWood", 'B', Items.WATER_BUCKET));
		//}

		// Toaster
		//if(config.getBoolean("Toaster", "blocks", true, "")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.toaster), "  B", "IDI", "ILI", 'I', "ingotIron", 'B', Blocks.STONE_BUTTON, 'D', Blocks.IRON_TRAPDOOR, 'L', Items.LAVA_BUCKET));
		//}

		// Spice Rack
		//if(config.getBoolean("Spice Rack", "blocks", true, "")) {
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.spiceRack), "slabWood"));
		//}

		// Milk Jar
		//if(config.getBoolean("Milk Jar", "blocks", true, "")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.milkJar), "GPG", "GMG", "GGG", 'G', "blockGlass", 'P', "plankWood", 'M', Items.MILK_BUCKET));
		//}

		// Cooking Table
		//if(config.getBoolean("Cooking Table", "blocks", true, "")) {
		//	if(craftingEdition) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.cookingTable), "SSS", "CBC", "CCC", 'S', Blocks.STONE, 'C', Blocks.HARDENED_CLAY, 'B', new ItemStack(ModItems.recipeBook, 1, 2)));
		//	} else {
		//		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.cookingTable), "SSS", "CBC", "CCC", 'S', Blocks.STONE, 'C', Blocks.HARDENED_CLAY, 'B', Items.BOOK));
		//	}
		//}

		// Kitchen Counter
		//if(config.getBoolean("Kitchen Counter", "blocks", true, "")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.counter), "SSS", "CBC", "CCC", 'S', Blocks.STONE, 'C', Blocks.HARDENED_CLAY, 'B', Blocks.CHEST));
		//}

		// Cooking Oven
		//if(config.getBoolean("Cooking Oven", "blocks", true, "")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.oven), "GGG", "IFI", "III", 'G', new ItemStack(Blocks.STAINED_GLASS, 1, 15), 'I', "ingotIron", 'F', Blocks.FURNACE));
		//}

		// Tool Rack
		//if(config.getBoolean("Tool Rack", "blocks", true, "")) {
			if(OreDictionary.getOres("nuggetIron", false).size() > 0) {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.toolRack), "SSS", "I I", 'S', "slabWood", 'I', "nuggetIron"));
			} else {
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.toolRack), "SSS", "I I", 'S', "slabWood", 'I', Blocks.STONE_BUTTON));
			}
		//}

		// Cooking Corner
		//if(config.getBoolean("Kitchen Corner", "blocks", true, "")) {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.corner), "SSS", "CBC", "CCC", 'S', Blocks.STONE, 'C', Blocks.HARDENED_CLAY, 'B', Blocks.CHEST));
		//}

		// Cooking Floor
		//if(config.getBoolean("Kitchen Corner", "blocks", true, "")) {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.kitchenFloor), "BW", "WB", 'B', Blocks.COAL_BLOCK, 'W', Blocks.QUARTZ_BLOCK));
		//}
	}

}
