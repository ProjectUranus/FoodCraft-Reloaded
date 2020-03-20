package com.projecturanus.foodcraft

import com.projecturanus.foodcraft.common.GuiHandler
import com.projecturanus.foodcraft.common.block.entity.*
import com.projecturanus.foodcraft.common.capability.DefaultCapabilities
import com.projecturanus.foodcraft.common.init.RegisterHandler
import com.projecturanus.foodcraft.common.network.registerMessages
import com.projecturanus.foodcraft.common.recipe.RecipeRegistryHandler
import com.projecturanus.foodcraft.fluid.FluidCookingOil
import com.projecturanus.foodcraft.fluid.FluidMilk
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.*
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import org.apache.logging.log4j.LogManager

const val MODID = "foodcraftreloaded"
val logger = LogManager.getLogger(MODID)

@Mod(modid = MODID, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", dependencies = "after: commoncapabilities")
object FoodCraftReloaded {
    @Mod.EventHandler
    fun construct(event: FMLConstructionEvent) {
        RegisterHandler.registerPlants()
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        NetworkRegistry.INSTANCE.registerGuiHandler(FoodCraftReloaded, GuiHandler)
        registerTileEntities()
        if (Loader.isModLoaded("theoneprobe")) {
            if (FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "com.projecturanus.foodcraft.common.integration.theoneprobe.TOPIntegration"))
                logger.info("The One Probe Integration Initialized")
        }
        FluidRegistry.registerFluid(FluidMilk)
        FluidRegistry.addBucketForFluid(FluidMilk)
        FluidRegistry.registerFluid(FluidCookingOil)
        FluidRegistry.addBucketForFluid(FluidCookingOil)
        registerMessages()
    }

    fun registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityBeverageMaking::class.java, ResourceLocation(MODID, "beverage_making"))
        GameRegistry.registerTileEntity(TileEntityBrewBarrel::class.java, ResourceLocation(MODID, "brew_barrel"))
        GameRegistry.registerTileEntity(TileEntityChoppingBoard::class.java, ResourceLocation(MODID, "chopping_board"))
        GameRegistry.registerTileEntity(TileEntityFryingPan::class.java, ResourceLocation(MODID, "frying_pan"))
        GameRegistry.registerTileEntity(TileEntityMill::class.java, ResourceLocation(MODID, "mill"))
        GameRegistry.registerTileEntity(TileEntityPan::class.java, ResourceLocation(MODID, "pan"))
        GameRegistry.registerTileEntity(TileEntityPot::class.java, ResourceLocation(MODID, "pot"))
        GameRegistry.registerTileEntity(TileEntityPressureCooker::class.java, ResourceLocation(MODID, "pressure_cooker"))
        GameRegistry.registerTileEntity(TileEntityStove::class.java, ResourceLocation(MODID, "stove"))
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        if (!Loader.isModLoaded("harvestcraft"))
            RegisterHandler.registerVanillaOres()
        else {
            RegisterHandler.mirrorOres("listAlloil", "foodSesameoil")
            RegisterHandler.mirrorOres("listAlloil", "foodOliveoil")
        }

        if (!Loader.isModLoaded("commoncapabilities")) {
            DefaultCapabilities.registerDefaultCapabilities()
        }
    }

    @Mod.EventHandler
    fun loadComplete(event: FMLLoadCompleteEvent) {
        logger.info("Caching Allowed Fluid for Machines")
        RecipeRegistryHandler.reloadRecipeFluids()
    }
}
