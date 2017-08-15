package net.blay09.mods.cookingforblockheads.compat;

import net.blay09.mods.cookingforblockheads.api.CookingForBlockheadsAPI;
import net.blay09.mods.cookingforblockheads.api.ToastOutputHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class HarvestCraftAddon extends SimpleAddon {

    private static final String[] ADDITIONAL_RECIPES = new String[] {
            "flouritem",
            "doughitem",
            "cornmealitem",
            "freshwateritem",
            "pastaitem",
            "vanillaitem",
            "butteritem",
            "heavycreamitem",
            "saltitem",
            "freshmilkitem",
            "mayoitem",
            "cocoapowderitem",
            "ketchupitem",
            "vinegaritem",
            "mustarditem",
            "blackpepperitem",
            "groundcinnamonitem",
            "groundnutmegitem",
            "saladdressingitem",
            "batteritem",
            "oliveoilitem",
            "hotsauceitem",
            "sweetandsoursauceitem",
            "fivespiceitem",
            "hoisinsauceitem",
            "noodlesitem",
            "sesameoilitem",
            "garammasalaitem",
            "soysauceitem",
            "currypowderitem",
            "bubblywateritem",
            "carrotcakeitem",
            "holidaycakeitem",
            "pumpkincheesecakeitem",
            "pavlovaitem",
            "lamingtonitem",
            "cheesecakeitem",
            "cherrycheesecakeitem",
            "pineappleupsidedowncakeitem",
            "chocolatesprinklecakeitem",
            "redvelvetcakeitem"
    };

    private static final String[] OVEN_RECIPES = new String[] {
            "turkeyrawitem", "turkeycookeditem",
            "rabbitrawitem", "rabbitcookeditem",
            "venisonrawitem", "venisoncookeditem"
    };

    private static final String[] TOOLS = new String[] {
            "cuttingboarditem",
            "potitem",
            "skilletitem",
            "saucepanitem",
            "bakewareitem",
            "mortarandpestleitem",
            "mixingbowlitem",
            "juiceritem"
    };

    private static final String OLIVE_OIL_ITEM = "oliveoilitem";
    private static final String TOAST_ITEM = "toastitem";

    private static final String FRESH_WATER_ITEM = "freshwateritem";
    private static final String FRESH_MILK_ITEM = "freshmilkitem";

    public HarvestCraftAddon() {
        super("harvestcraft");

        ItemStack oliveOil = getModItemStack(OLIVE_OIL_ITEM);
        if(!(null == oliveOil)) {
            CookingForBlockheadsAPI.addOvenFuel(oliveOil, 1600);
        }

        for(int i = 0; i < OVEN_RECIPES.length; i += 2) {
            ItemStack sourceItem = getModItemStack(OVEN_RECIPES[i]);
            ItemStack resultItem = getModItemStack(OVEN_RECIPES[i + 1]);
            if(!(null == oliveOil) && !(null == oliveOil)) {
                CookingForBlockheadsAPI.addOvenRecipe(sourceItem, resultItem);
            }
        }

        final ItemStack toastItem = getModItemStack(TOAST_ITEM);
        if(!(null == toastItem)) {
            CookingForBlockheadsAPI.addToastHandler(new ItemStack(Items.BREAD), (ToastOutputHandler) itemStack -> toastItem);
        }

        addNonFoodRecipe(ADDITIONAL_RECIPES);
        addTool(TOOLS);

        CookingForBlockheadsAPI.addWaterItem(getModItemStack(FRESH_WATER_ITEM));
        CookingForBlockheadsAPI.addMilkItem(getModItemStack(FRESH_MILK_ITEM));
    }

    public static boolean isWeirdConversionRecipe(IRecipe recipe) {
        if(recipe.getRecipeSize() == 2 && recipe instanceof ShapelessOreRecipe && recipe.getRecipeOutput().stackSize == 2) {
            ShapelessOreRecipe oreRecipe = (ShapelessOreRecipe) recipe;
            Object first = oreRecipe.getInput().get(0);
            Object second = oreRecipe.getInput().get(1);
            ItemStack firstItem = null;
            ItemStack secondItem = null;
            if (first instanceof ItemStack) {
                firstItem = (ItemStack) first;
            } else if (first instanceof ArrayList) {
                List list = (List) first;
                if (list.size() == 1) {
                    firstItem = (ItemStack) list.get(0);
                }
            }
            if (second instanceof ItemStack) {
                secondItem = (ItemStack) second;
            } else if (second instanceof ArrayList) {
                List list = (List) second;
                if (list.size() == 1) {
                    secondItem = (ItemStack) list.get(0);
                }
            }
            if (firstItem != null && secondItem != null && ItemStack.areItemStacksEqual(firstItem, secondItem) && oreRecipe.getRecipeOutput() != null && oreRecipe.getRecipeOutput().isItemEqual(firstItem)) {
                return true;
            }
        }
        return false;
    }

}
