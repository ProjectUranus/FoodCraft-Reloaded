package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.BrewBarrelRecipe
import mezz.jei.api.IJeiHelpers
import mezz.jei.api.gui.IDrawable
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.ingredients.VanillaTypes
import mezz.jei.api.recipe.IRecipeCategory
import mezz.jei.api.recipe.IRecipeWrapper
import net.minecraft.client.resources.I18n
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class BrewBarrelWrapper(val helper: IJeiHelpers, val recipe: BrewBarrelRecipe) : IRecipeWrapper {
    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, helper.stackHelper.expandRecipeItemStackInputs(recipe.ingredients))
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
        recipe.fluidInput?.let { ingredients.setInput(VanillaTypes.FLUID, it) }
    }
}

class BrewBarrelCategory(val helper: IJeiHelpers) : IRecipeCategory<BrewBarrelWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/brew_barrel.png")

    override fun getUid(): String = FcRecipeUids.BREW_BARREL

    override fun getModName(): String = MODID

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: BrewBarrelWrapper, ingredients: IIngredients) {
        recipeLayout.fluidStacks.init(0, true, 15 - 7, 12 - 8, 11, 57, 4000, true, null)

        recipeLayout.fluidStacks.set(ingredients)

        val stackGroup = recipeLayout.itemStacks
        stackGroup.init(0, true, 50 - 7, 27 - 8)
        stackGroup.init(1, true, 74 - 7, 27 - 8)
        stackGroup.init(2, true, 97 - 7, 27 - 8)

        stackGroup.init(3, false, 134 - 7, 27 - 8)
        stackGroup.set(ingredients)
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 7, 8, 150, 64)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.brew_barrel.name")

}
