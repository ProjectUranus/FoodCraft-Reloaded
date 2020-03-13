package com.projecturanus.foodcraft.common.recipe

import com.google.gson.GsonBuilder
import com.projecturanus.foodcraft.MODID
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryBuilder

val BEVERAGE_MAKING_RECIPES: IForgeRegistry<BeverageMakingRecipe> by lazy {
    RegistryBuilder<BeverageMakingRecipe>()
        .setType(BeverageMakingRecipe::class.java)
        .setName(ResourceLocation(MODID, "beverage_making_recipes"))
        .create()
}

val CHOPPING_BOARD_RECIPES: IForgeRegistry<ChoppingBoardRecipe> by lazy {
    RegistryBuilder<ChoppingBoardRecipe>()
        .setType(ChoppingBoardRecipe::class.java)
        .setName(ResourceLocation(MODID, "chopping_board_recipes"))
        .create()
}

@Mod.EventBusSubscriber(modid = MODID)
object RecipeRegistryHandler {
    private val GSON = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    @JvmStatic
    @SubscribeEvent
    fun registerRegistries(event: RegistryEvent.NewRegistry) {
        BEVERAGE_MAKING_RECIPES
        CHOPPING_BOARD_RECIPES
    }

    @JvmStatic
    @SubscribeEvent
    fun loadRecipes(event: RegistryEvent.Register<IRecipe>) {
    }
}
