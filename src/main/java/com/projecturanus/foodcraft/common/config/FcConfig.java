package com.projecturanus.foodcraft.common.config;

import com.projecturanus.foodcraft.FoodCraftReloadedKt;
import com.projecturanus.foodcraft.worldgen.FruitTreeWorldGen;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Config(modid = FoodCraftReloadedKt.MODID)
@Mod.EventBusSubscriber(modid = FoodCraftReloadedKt.MODID, value = Side.CLIENT)
public class FcConfig {
    @Config.Name("Fruit Tree Generate Chance")
    @Config.Comment({"Generate chance for fruit tree, default to 0.08 (lower to be more rare)", "This will replace vanilla tree generation by event"})
    @Config.RangeDouble(min = 0.0, max = 1.0)
    @Config.SlidingOption
    public static double fruitTreeChance = 0.08;

    @Config.Name("Fruit Tree Biomes Whitelist")
    @Config.Comment("Use ; to split, requires full ResourceLocation name")
    public static String fruitTreeBiomes = "minecraft:jungle;minecraft:forest;minecraft:forest_hills;minecraft:birch_forest;minecraft:birch_forest_hills;minecraft:mutated_jungle;minecraft:mutated_jungle_edge";

    @Config.Name("Machine Settings")
    public static MachineConfig machineConfig = new MachineConfig();

    public static class MachineConfig {
        @Config.Comment("Minimum temperature (in celsius degrees) to start a recipe for Beverage Making Recipes")
        public double beverageMakingHeat = 80;

        @Config.Comment("Maximum temperature (in celsius degrees) to start a recipe for Beverage Making Cooling Recipes")
        public double beverageMakingCool = -15;

        @Config.Comment("Heat Power for Beverage Making (must be higher than radiation)")
        public double beverageMakingPower = 0.4;

        @Config.Comment("Radiation for Beverage Making")
        public double beverageMakingRadiation = 0.15;

        @Config.Comment("Heat Power for Beverage Making Cooling (must be higher than radiation)")
        public double beverageMakingCoolingPower = 0.4;

        @Config.Comment("Radiation for Beverage Making Cooling")
        public double beverageMakingCoolingRadiation = 0.05;

        @Config.Comment("Ticks to progress Beverage Making Recipes")
        @Config.RangeInt(min = 1)
        public int beverageMakingProgress = 200;

        @Config.Comment("Ticks to progress Brew Barrel Recipes")
        @Config.RangeInt(min = 1)
        public int brewBarrelProgress = 3600;

        @Config.Comment("Ticks to progress Chopping Board Recipes")
        @Config.RangeInt(min = 1)
        public int choppingBoardProgress = 20;

        @Config.Comment("Minimum temperature (in celsius degrees) to start a recipe for Frying Pan Recipes")
        public double fryingPanHeat = 90;

        @Config.Comment("Heat Power for Frying Pan (must be higher than radiation)")
        public double fryingPanPower = 0.6;

        @Config.Comment("Radiation for Frying Pan")
        public double fryingPanRadiation = 0.15;

        @Config.Comment("Ticks to progress Frying Pan Recipes")
        @Config.RangeInt(min = 1)
        public int fryingPanProgress = 200;

        @Config.Comment("Minimum temperature (in celsius degrees) to start a recipe for Mill Recipes")
        public double millHeat = 100;

        @Config.Comment("Heat Power for Mill (must be higher than radiation)")
        public double millPower = 0.6;

        @Config.Comment("Radiation for Mill")
        public double millRadiation = 0.15;

        @Config.Comment("Ticks to progress Mill Recipes")
        @Config.RangeInt(min = 1)
        public int millProgress = 200;

        @Config.Comment("Minimum temperature (in celsius degrees) to start a recipe for Pressure Cooker Recipes")
        public double pressureCookerHeat = 100;

        @Config.Comment("Heat Power for Pressure Cooker (must be higher than radiation)")
        public double pressureCookerPower = 0.65;

        @Config.Comment("Radiation for Pressure Cooker")
        public double pressureCookerRadiation = 0.15;

        @Config.Comment("Ticks to progress Pressure Cooker Recipes")
        @Config.RangeInt(min = 1)
        public int pressureCookerProgress = 400;

        @Config.Comment("Heat Power for Pan (must be higher than radiation)")
        public double panPower = 1.0;

        @Config.Comment("Radiation for Pan")
        public double panRadiation = 0.02;

        @Config.Comment("Minimum temperature (in celsius degrees) to start a recipe for Pan Recipes")
        public double panHeat = 80;

        @Config.Comment("Heat Power for Pot (must be higher than radiation)")
        public double potPower = 1.0;

        @Config.Comment("Radiation for Pot")
        public double potRadiation = 0.02;

        @Config.Comment("Minimum temperature (in celsius degrees) to start a recipe for Pot Recipes")
        public double potHeat = 80;
    }

    @Config.Name("Client Settings")
    public static ClientConfig clientConfig = new ClientConfig();

    public static class ClientConfig {
        @Config.Name("Enable Hovering Info")
        @Config.Comment({"Hovering info for machine GUI, displays detailed data about widgets", "Disable this if you encountered performance decrease or exceptions"})
        public boolean enableHoverInfo = false;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onReloadConfig(ConfigChangedEvent event) {
        if (event.getModID().equals(FoodCraftReloadedKt.MODID)) {
            ConfigManager.sync(FoodCraftReloadedKt.MODID, Config.Type.INSTANCE);
            FruitTreeWorldGen.INSTANCE.setBiomeIds(fruitTreeBiomes);
        }
    }
}
