package com.projecturanus.foodcraft.common.integration.jei

import com.projecturanus.foodcraft.client.gui.*
import com.projecturanus.foodcraft.common.init.FCRItems
import com.projecturanus.foodcraft.common.integration.jei.recipes.*
import com.projecturanus.foodcraft.common.recipe.*
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
        registry.addRecipes(BREW_BARREL_RECIPES.valuesCollection, FcRecipeUids.BREW_BARREL)
        registry.addRecipes(FRYING_PAN_RECIPES.valuesCollection, FcRecipeUids.FRYING)
        registry.addRecipes(MILL_RECIPES.valuesCollection, FcRecipeUids.MILLING)
        registry.addRecipes(PAN_RECIPES.valuesCollection, FcRecipeUids.PAN)
        registry.addRecipes(POT_RECIPES.valuesCollection, FcRecipeUids.POT)
        registry.addRecipes(PRESSURE_COOKER_RECIPES.valuesCollection, FcRecipeUids.PRESSURE_COOKER)

        registry.handleRecipes(ChoppingBoardRecipe::class.java, { ChoppingBoardWrapper(registry.jeiHelpers, it) }, FcRecipeUids.CHOPPING)
        registry.handleRecipes(BeverageMakingRecipe::class.java, { BeverageMakingWrapper(registry.jeiHelpers, it) }, FcRecipeUids.BEVERAGE_MAKING)
        registry.handleRecipes(BrewBarrelRecipe::class.java, { BrewBarrelWrapper(registry.jeiHelpers, it) }, FcRecipeUids.BREW_BARREL)
        registry.handleRecipes(FryingPanRecipe::class.java, { FryingPanWrapper(registry.jeiHelpers, it) }, FcRecipeUids.FRYING)
        registry.handleRecipes(MillRecipe::class.java, { MillWrapper(registry.jeiHelpers, it) }, FcRecipeUids.MILLING)
        registry.handleRecipes(PanRecipe::class.java, { PanWrapper(registry.jeiHelpers, it) }, FcRecipeUids.PAN)
        registry.handleRecipes(PotRecipe::class.java, { PotWrapper(registry.jeiHelpers, it) }, FcRecipeUids.POT)
        registry.handleRecipes(PressureCookerRecipe::class.java, { PressureCookerWrapper(registry.jeiHelpers, it) }, FcRecipeUids.PRESSURE_COOKER)

        registry.addRecipeClickArea(GuiContainerChoppingBoard::class.java, 96, 43, 18, 13, FcRecipeUids.CHOPPING)
        registry.addRecipeClickArea(GuiContainerBeverageMaking::class.java, 92, 30, 18, 13, FcRecipeUids.BEVERAGE_MAKING)
        registry.addRecipeClickArea(GuiContainerBrewBarrel::class.java, 119, 31, 16, 16, FcRecipeUids.BREW_BARREL)
        registry.addRecipeClickArea(GuiContainerFryingPan::class.java, 92, 30, 18, 13, FcRecipeUids.FRYING)
        registry.addRecipeClickArea(GuiContainerMill::class.java, 76, 21, 18, 13, FcRecipeUids.MILLING)
        registry.addRecipeClickArea(GuiContainerPan::class.java, 72, 39, 18, 13, FcRecipeUids.PAN)
        registry.addRecipeClickArea(GuiContainerPot::class.java, 94, 23, 20, 9, FcRecipeUids.POT)
        registry.addRecipeClickArea(GuiContainerPressureCooker::class.java, 118, 30, 18, 13, FcRecipeUids.PRESSURE_COOKER)

        registry.addRecipeCatalyst(ItemStack(FCRItems.CHOPPING_BOARD), FcRecipeUids.CHOPPING)
        registry.addRecipeCatalyst(ItemStack(FCRItems.BEVERAGE_MAKING), FcRecipeUids.BEVERAGE_MAKING)
        registry.addRecipeCatalyst(ItemStack(FCRItems.BREW_BARREL), FcRecipeUids.BREW_BARREL)
        registry.addRecipeCatalyst(ItemStack(FCRItems.FRYING_PAN), FcRecipeUids.FRYING)
        registry.addRecipeCatalyst(ItemStack(FCRItems.MILL), FcRecipeUids.MILLING)
        registry.addRecipeCatalyst(ItemStack(FCRItems.PAN), FcRecipeUids.PAN)
        registry.addRecipeCatalyst(ItemStack(FCRItems.POT), FcRecipeUids.POT)
        registry.addRecipeCatalyst(ItemStack(FCRItems.PRESSURE_COOKER), FcRecipeUids.PRESSURE_COOKER)
    }

    override fun registerCategories(registry: IRecipeCategoryRegistration) {
        registry.addRecipeCategories(ChoppingBoardCategory(registry.jeiHelpers),
            BeverageMakingCategory(registry.jeiHelpers),
            BrewBarrelCategory(registry.jeiHelpers),
            FryingPanCategory(registry.jeiHelpers),
            MillCategory(registry.jeiHelpers),
            PanCategory(registry.jeiHelpers),
            PotCategory(registry.jeiHelpers),
            PressureCookerCategory(registry.jeiHelpers)
        )
    }
}
