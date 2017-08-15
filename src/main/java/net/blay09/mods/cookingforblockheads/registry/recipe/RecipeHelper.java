package net.blay09.mods.cookingforblockheads.registry.recipe;

import com.google.common.collect.Lists;
import net.blay09.mods.cookingforblockheads.registry.CookingRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

public class RecipeHelper {
    static class GeneralFoodRecipeOverride extends GeneralFoodRecipe {
        public GeneralFoodRecipeOverride(IRecipe recipe) {
            super(recipe);
        }
        public void setCraftMatrix(List<FoodIngredient> craftMatrix) {
            this.craftMatrix = craftMatrix;
        }
    }

    public static GeneralFoodRecipeOverride makeGeneralFoodRecipe(IRecipe irecipe) {
        GeneralFoodRecipeOverride foodRecipe = new GeneralFoodRecipeOverride(irecipe);
        if (irecipe instanceof ShapedRecipes) {
            ShapedRecipes recipe = (ShapedRecipes) irecipe;
            List<FoodIngredient> craftMatrix = Lists.newArrayList();
            for(int i = 0; i < recipe.recipeItems.length; i++) {
                if(recipe.recipeItems[i] != null) {
                    boolean isToolItem = CookingRegistry.isToolItem(recipe.recipeItems[i]);
                    craftMatrix.add(new FoodIngredient(recipe.recipeItems[i].copy(), isToolItem));
                } else {
                    craftMatrix.add(null);
                }
            }

            foodRecipe.setCraftMatrix(craftMatrix);

        } else if (irecipe instanceof ShapelessRecipes) {
            ShapelessRecipes recipe = (ShapelessRecipes) irecipe;
            List<FoodIngredient> craftMatrix = Lists.newArrayList();
            for(int i = 0; i < recipe.recipeItems.size(); i++) {
                if (recipe.recipeItems.get(i) != null) {
                    boolean isToolItem = CookingRegistry.isToolItem(recipe.recipeItems.get(i));
                    craftMatrix.add(new FoodIngredient(recipe.recipeItems.get(i).copy(), isToolItem));
                }
            }

            foodRecipe.setCraftMatrix(craftMatrix);

            return foodRecipe;
        } else if (irecipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe recipe = (ShapelessOreRecipe) irecipe;
            List<FoodIngredient> craftMatrix = Lists.newArrayList();
            for(int i = 0; i < recipe.getInput().size(); i++) {
                Object input = recipe.getInput().get(i);

                if (input == null) {
                    continue;
                }

                if(input instanceof ItemStack) {
                    boolean isToolItem = CookingRegistry.isToolItem((ItemStack) input);
                    craftMatrix.add(new FoodIngredient(((ItemStack) input), isToolItem));
                } else if(input instanceof List) {
                    List<ItemStack> stackList = (List<ItemStack>) input;
                    boolean toolFound = false;
                    for (ItemStack itemStack : stackList) {
                        if (CookingRegistry.isToolItem(itemStack)) {
                            toolFound = true;
                        }
                    }
                    craftMatrix.add(new FoodIngredient(stackList.toArray(new ItemStack[stackList.size()]), toolFound));
                }
            }
            foodRecipe.setCraftMatrix(craftMatrix);
        } else if (irecipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe recipe = (ShapedOreRecipe) irecipe;
            List<FoodIngredient> craftMatrix = Lists.newArrayList();
            for(int i = 0; i < recipe.getInput().length; i++) {
                Object input = recipe.getInput()[i];
                if (input == null) {
                    craftMatrix.add(null);
                } else if(input instanceof ItemStack) {
                    craftMatrix.add(new FoodIngredient((ItemStack) input, false));
                } else if(input instanceof List) {
                    craftMatrix.add(new FoodIngredient(((List<ItemStack>) input).toArray(new ItemStack[((List<ItemStack>) input).size()]), false));
                }
            }

            foodRecipe.setCraftMatrix(craftMatrix);
        }

        return foodRecipe;
    }
}
