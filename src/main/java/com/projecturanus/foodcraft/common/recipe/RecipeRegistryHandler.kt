package com.projecturanus.foodcraft.common.recipe

import com.google.gson.GsonBuilder
import com.projecturanus.foodcraft.MODID
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.registries.RegistryBuilder

val BEVERAGE_MAKING_RECIPES by lazy {
    RegistryBuilder<BeverageMakingRecipe>()
        .setType(BeverageMakingRecipe::class.java)
        .setName(ResourceLocation(MODID, "beverage_making_recipes"))
        .create()!!
}

val BREW_BARREL_RECIPES by lazy {
    RegistryBuilder<BrewBarrelRecipe>()
        .setType(BrewBarrelRecipe::class.java)
        .setName(ResourceLocation(MODID, "brew_barrel_recipes"))
        .create()!!
}

val CHOPPING_BOARD_RECIPES by lazy {
    RegistryBuilder<ChoppingBoardRecipe>()
        .setType(ChoppingBoardRecipe::class.java)
        .setName(ResourceLocation(MODID, "chopping_board_recipes"))
        .create()!!
}

val FRYING_PAN_RECIPES by lazy {
    RegistryBuilder<FryingPanRecipe>()
        .setType(FryingPanRecipe::class.java)
        .setName(ResourceLocation(MODID, "frying_pan_recipes"))
        .create()!!
}

val MILL_RECIPES by lazy {
    RegistryBuilder<MillRecipe>()
        .setType(MillRecipe::class.java)
        .setName(ResourceLocation(MODID, "mill_recipes"))
        .create()!!
}

val PAN_RECIPES by lazy {
    RegistryBuilder<PanRecipe>()
        .setType(PanRecipe::class.java)
        .setName(ResourceLocation(MODID, "pan_recipes"))
        .create()!!
}

val POT_RECIPES by lazy {
    RegistryBuilder<PotRecipe>()
        .setType(PotRecipe::class.java)
        .setName(ResourceLocation(MODID, "pot_recipes"))
        .create()!!
}

val PRESSURE_COOKER_RECIPES by lazy {
    RegistryBuilder<PressureCookerRecipe>()
        .setType(PressureCookerRecipe::class.java)
        .setName(ResourceLocation(MODID, "pressure_cooker_recipes"))
        .create()!!
}

@Mod.EventBusSubscriber(modid = MODID)
object RecipeRegistryHandler {
    private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    @JvmStatic
    @SubscribeEvent
    fun registerRegistries(event: RegistryEvent.NewRegistry) {
        BEVERAGE_MAKING_RECIPES
        BREW_BARREL_RECIPES
        CHOPPING_BOARD_RECIPES
        FRYING_PAN_RECIPES
        MILL_RECIPES
        PAN_RECIPES
        POT_RECIPES
        PRESSURE_COOKER_RECIPES
    }

    fun reloadRecipeFluids() {
        arrayOf(
            BEVERAGE_MAKING_RECIPES,
            BREW_BARREL_RECIPES,
            FRYING_PAN_RECIPES,
            PRESSURE_COOKER_RECIPES
        ).forEach { RecipeRegistryJavaFix.putFluids(it) }
    }
}
