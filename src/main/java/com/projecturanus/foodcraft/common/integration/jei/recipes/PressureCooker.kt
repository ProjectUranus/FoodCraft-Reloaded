package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.PressureCookerRecipe
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

class PressureCookerWrapper(val helper: IJeiHelpers, val recipe: PressureCookerRecipe): IRecipeWrapper {

    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, helper.stackHelper.expandRecipeItemStackInputs(recipe.ingredients))
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
        recipe.fluidInput?.let { ingredients.setInput(VanillaTypes.FLUID, it) }
    }
}

class PressureCookerCategory(val helper: IJeiHelpers): IRecipeCategory<PressureCookerWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/pressure_cooker.png")
    val staticFlame: IDrawableStatic by lazy { helper.guiHelper.createDrawable(TEXTURES, 176, 0, 14, 14) }
    val flame: IDrawableAnimated by lazy { helper.guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true) }
    val arrow: IDrawableAnimated

    init {
        arrow = helper.guiHelper.drawableBuilder(TEXTURES, 176, 14, 24, 17)
            .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false)
    }

    override fun getUid(): String = FcRecipeUids.PRESSURE_COOKER

    override fun getModName(): String = MODID

    override fun drawExtras(minecraft: Minecraft) {
        arrow.draw(minecraft, 105 - 7, 26 - 8)
        flame.draw(Minecraft.getMinecraft(), 109 - 7, 58 - 8)
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: PressureCookerWrapper, ingredients: IIngredients) {
        recipeLayout.fluidStacks.init(0, true, 15 - 7, 12 - 8, 11, 57, 4000, true, null)

        recipeWrapper.recipe.fluidInput?.let { recipeLayout.fluidStacks.set(0, it) }

        val stackGroup = recipeLayout.itemStacks

        stackGroup.init(0, true, 33 - 7, 26 - 8)
        stackGroup.init(1, true, 57 - 7, 26 - 8)
        stackGroup.init(2, true, 81 - 7, 26 - 8)
        stackGroup.init(3, false, 131 - 7, 26 - 8)

        stackGroup.set(ingredients)
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 7, 8, 148, 64)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.pressure_cooker.name")
}
