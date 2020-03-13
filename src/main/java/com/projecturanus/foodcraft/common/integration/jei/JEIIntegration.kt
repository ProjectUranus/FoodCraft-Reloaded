package com.projecturanus.foodcraft.common.integration.jei

import com.projecturanus.foodcraft.client.gui.GuiContainerChoppingBoard
import com.projecturanus.foodcraft.common.init.FCRItems
import com.projecturanus.foodcraft.common.integration.jei.recipes.ChoppingBoardCategory
import com.projecturanus.foodcraft.common.integration.jei.recipes.ChoppingBoardWrapper
import com.projecturanus.foodcraft.common.recipe.CHOPPING_BOARD_RECIPES
import com.projecturanus.foodcraft.common.recipe.ChoppingBoardRecipe
import mezz.jei.api.IModPlugin
import mezz.jei.api.IModRegistry
import mezz.jei.api.JEIPlugin
import mezz.jei.api.recipe.IRecipeCategoryRegistration
import net.minecraft.item.ItemStack

@JEIPlugin
class JEIIntegration : IModPlugin {
    override fun register(registry: IModRegistry) {
        registry.addRecipes(CHOPPING_BOARD_RECIPES.valuesCollection, FcRecipeUids.CHOPPING)
        registry.handleRecipes(ChoppingBoardRecipe::class.java, { ChoppingBoardWrapper(registry.jeiHelpers, it) }, FcRecipeUids.CHOPPING)
        registry.addRecipeClickArea(GuiContainerChoppingBoard::class.java, 20, 20, 20, 20, FcRecipeUids.CHOPPING)
        registry.addRecipeCatalyst(ItemStack(FCRItems.CHOPPING_BOARD), FcRecipeUids.CHOPPING)
    }

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(ChoppingBoardCategory(registry.jeiHelpers))
    }
}
