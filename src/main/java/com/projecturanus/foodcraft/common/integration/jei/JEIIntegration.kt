package com.projecturanus.foodcraft.common.integration.jei

import com.projecturanus.foodcraft.client.gui.GuiContainerBeverageMaking
import com.projecturanus.foodcraft.client.gui.GuiContainerChoppingBoard
import com.projecturanus.foodcraft.common.init.FCRItems
import com.projecturanus.foodcraft.common.integration.jei.recipes.BeverageMakingCategory
import com.projecturanus.foodcraft.common.integration.jei.recipes.BeverageMakingWrapper
import com.projecturanus.foodcraft.common.integration.jei.recipes.ChoppingBoardCategory
import com.projecturanus.foodcraft.common.integration.jei.recipes.ChoppingBoardWrapper
import com.projecturanus.foodcraft.common.recipe.BEVERAGE_MAKING_RECIPES
import com.projecturanus.foodcraft.common.recipe.BeverageMakingRecipe
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
        registry.addRecipes(BEVERAGE_MAKING_RECIPES.valuesCollection, FcRecipeUids.BEVERAGE_MAKING)

        registry.handleRecipes(ChoppingBoardRecipe::class.java, { ChoppingBoardWrapper(registry.jeiHelpers, it) }, FcRecipeUids.CHOPPING)
        registry.handleRecipes(BeverageMakingRecipe::class.java, { BeverageMakingWrapper(registry.jeiHelpers, it) }, FcRecipeUids.BEVERAGE_MAKING)

        registry.addRecipeClickArea(GuiContainerChoppingBoard::class.java, 20, 20, 20, 20, FcRecipeUids.CHOPPING)
        registry.addRecipeClickArea(GuiContainerBeverageMaking::class.java, 20, 20, 20, 20, FcRecipeUids.BEVERAGE_MAKING)

        registry.addRecipeCatalyst(ItemStack(FCRItems.CHOPPING_BOARD), FcRecipeUids.CHOPPING)
        registry.addRecipeCatalyst(ItemStack(FCRItems.BEVERAGE_MAKING), FcRecipeUids.BEVERAGE_MAKING)
    }

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(ChoppingBoardCategory(registry.jeiHelpers), BeverageMakingCategory(registry.jeiHelpers))
    }
}
