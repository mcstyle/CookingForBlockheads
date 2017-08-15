package net.blay09.mods.cookingforblockheads;

import net.minecraftforge.common.config.Config;

@Config(modid = CookingForBlockheads.MOD_ID)
public class ModConfig {

    public static General general = new General();

    public static Compat compat = new Compat();

    @Config.Comment("Setting any of these options to false will disable their crafting recipe.")
    public static Blocks blocks = new Blocks();

    @Config.Comment("Setting any of these options to false will disable their crafting recipe.")
    public static Items items = new Items();

    @Config.Comment("Setting any of these options to false will disable their specific mod support.")
    public static Modules modules = new Modules();

    public static class General {
        @Config.LangKey("Cow in a Jar")
        @Config.Comment("If true, a cow can be squished into a Milk Jar by dropping an anvil on top.")
        public boolean cowJarEnabled = true;

        @Config.LangKey("Cow in a Jar Milk per Tick")
        @Config.Comment("The amount of milk the cow in a jar generates per tick.")
        @Config.RangeDouble(min = 0)
        public float cowJarMilkPerTick  = 0.5f;

        @Config.LangKey("Sink Requires Water")
        @Config.Comment("Set this to true if you'd like the sink to require water to be piped in, instead of providing infinite of it.")
        public boolean sinkRequiresWater = false;

        @Config.LangKey("Disallow Oven Automation")
        @Config.Comment("Set this to true if you'd like to disallow automation of the oven (pipes and such won't be able to insert/extract)")
        public boolean disallowOvenAutomation = false;

        @Config.LangKey("Oven Fuel Time Multiplier")
        @Config.Comment("The fuel multiplier for the cooking oven. Higher values means fuel lasts longer, 1.0 is furnace default.")
        @Config.RangeDouble(min = 0.1f, max = 0.2f)
        public float ovenFuelTimeMultiplier = 0.33f;

        @Config.LangKey("Oven Cook Time Multiplier")
        @Config.Comment("The cooking time multiplier for the cooking oven. Higher values means it will take longer.")
        @Config.RangeDouble(min = 0.25f, max = 9f)
        public float ovenCookTimeMultiplier = 1f;
    }

    public static class Compat {
        @Config.LangKey("Oven Requires Cooking Oil")
        @Config.Comment("Set this to true if you'd like the oven to only accept cooking oil as fuel (requires Pam's Harvestcraft)")
        public boolean ovenRequiresCookingOil = false;
    }

    public static class Blocks {

    }

    public static class Items {

    }

    public static class Modules {
        @Config.LangKey("Vanilla Minecraft")
        @Config.Comment("Sink support, ingredient recipes")
        public boolean vanilla = true;

        @Config.LangKey("Pam's Harvestcraft")
        @Config.Comment("Tool support, oven recipes, oven fuel, ingredient recipes, toast")
        public boolean pamsHarvestcraft = true;

        @Config.LangKey("More Foods")
        @Config.Comment("Tool support, ingredient recipes, toast")
        public boolean moreFoods = true;

        @Config.LangKey("Extra Food")
        @Config.Comment("Tool support, ingredient recipes, toast")
        public boolean extraFood = true;

        @Config.LangKey("Food Expansion")
        @Config.Comment("Ingredient recipes")
        public boolean foodExpansion = true;

        @Config.LangKey("Vanilla Food Pantry")
        @Config.Comment("Tool support, Ingredient recipes")
        public boolean vanillaFoodPantry = true;
    }

}
