package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.FryingPanRecipe
import mezz.jei.api.IJeiHelpers
import mezz.jei.api.gui.IDrawable
import mezz.jei.api.gui.IDrawableAnimated
import mezz.jei.api.gui.IDrawableStatic
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.ingredients.VanillaTypes
import mezz.jei.api.recipe.IRecipeCategory
import mezz.jei.api.recipe.IRecipeWrapper
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.oredict.OreDictionary

class FryingPanWrapper(val helper: IJeiHelpers, val recipe: FryingPanRecipe): IRecipeWrapper {

    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.inputIngredient.matchingStacks.toList())
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
        recipe.fluidInput?.let { ingredients.setInput(VanillaTypes.FLUID, it) }
    }
}

class FryingPanCategory(val helper: IJeiHelpers): IRecipeCategory<FryingPanWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/frying_pan.png")
    val staticFlame: IDrawableStatic by lazy { helper.guiHelper.createDrawable(TEXTURES, 176, 0, 14, 14) }
    val flame: IDrawableAnimated by lazy { helper.guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true) }
    val arrow: IDrawableAnimated

    init {
        arrow = helper.guiHelper.drawableBuilder(TEXTURES, 176, 14, 24, 17)
            .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false)
    }

    override fun getUid(): String = FcRecipeUids.FRYING

    override fun getModName(): String = MODID

    override fun drawExtras(minecraft: Minecraft) {
        arrow.draw(minecraft, 89 - 7, 20 - 8)
        flame.draw(Minecraft.getMinecraft(), 119 - 7, 52 - 8)
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: FryingPanWrapper, ingredients: IIngredients) {
        recipeLayout.fluidStacks.init(0, true, 15 - 7, 13 - 8, 11, 57, 4000, true, null)

        recipeWrapper.recipe.fluidInput?.let { recipeLayout.fluidStacks.set(0, it) }

        val stackGroup = recipeLayout.itemStacks

        stackGroup.init(0, true, 54 - 7, 20 - 8)
        stackGroup.init(1, false, 126 - 7, 20 - 8)
        stackGroup.init(2, false, 33 - 7, 48 - 8)

        stackGroup.set(ingredients)
        stackGroup.set(2, OreDictionary.getOres("listAlloil"))
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 7, 8, 142, 64)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.frying_pan.name")
}
