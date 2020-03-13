package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.ChoppingBoardRecipe
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

class ChoppingBoardWrapper(val helper: IJeiHelpers, val recipe: ChoppingBoardRecipe): IRecipeWrapper {
    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, helper.stackHelper.expandRecipeItemStackInputs(recipe.ingredients))
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
    }
}

class ChoppingBoardCategory(val helper: IJeiHelpers): IRecipeCategory<ChoppingBoardWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/chopping_board.png")

    override fun getUid(): String = FcRecipeUids.CHOPPING

    override fun getModName(): String = MODID

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: ChoppingBoardWrapper, ingredients: IIngredients) {
        val stackGroup = recipeLayout.itemStacks
        stackGroup.init(0, true, 0, 0)
        stackGroup.init(1, true, 97 - 69 - 1, 0)
        stackGroup.init(2, true, 124 - 69 - 1, 0)
        stackGroup.init(3, false, 97 - 69 - 1, 53 - 20 - 1)

        val inputs = ingredients.getInputs(VanillaTypes.ITEM)
        stackGroup.set(0, inputs[0])
        stackGroup.set(1, inputs[1])
        stackGroup.set(2, inputs[2])
        stackGroup.set(3, ingredients.getOutputs(VanillaTypes.ITEM)[0])
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 69, 20, 140 - 69 + 1, 69 - 20 + 1)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.chopping_board.name")
}
