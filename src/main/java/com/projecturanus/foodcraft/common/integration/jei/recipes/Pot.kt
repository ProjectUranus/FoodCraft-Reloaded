package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.PotRecipe
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
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class PotWrapper(val helper: IJeiHelpers, val recipe: PotRecipe): IRecipeWrapper {
    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, helper.stackHelper.expandRecipeItemStackInputs(recipe.ingredients) + helper.stackHelper.expandRecipeItemStackInputs(recipe.staples))
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
    }
}

class PotCategory(val helper: IJeiHelpers): IRecipeCategory<PotWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/pot.png")

    val staticFlame: IDrawableStatic by lazy { helper.guiHelper.createDrawable(TEXTURES, 176, 0, 14, 14) }
    val flame: IDrawableAnimated by lazy { helper.guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true) }

    val arrow: IDrawableAnimated = helper.guiHelper.drawableBuilder(TEXTURES, 177, 18, 20, 9)
        .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false)

    override fun getUid(): String = FcRecipeUids.POT

    override fun getModName(): String = MODID

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: PotWrapper, ingredients: IIngredients) {
        val stackGroup = recipeLayout.itemStacks

        for (x in 0 until 4) {
            stackGroup.init(x, true, x * 18, 0)
            stackGroup.set(x, recipeWrapper.recipe.staples.getOrElse(x) { Ingredient.EMPTY }.matchingStacks.toList())
        }

        for (x in 0 until 8) {
            stackGroup.init(x + 4, true, x * 18, 38 - 16)
            stackGroup.set(x + 4, recipeWrapper.recipe.ingredients.getOrElse(x) { Ingredient.EMPTY }.matchingStacks.toList())
        }

        for (x in 0 until 2) {
            stackGroup.init(x + 12, false, 125 - 17 + x * 18, 0)
        }
        stackGroup.set(12, recipeWrapper.recipe.getRecipeOutput())
    }

    override fun drawExtras(minecraft: Minecraft) {
        arrow.draw(minecraft, 95 - 17, 21 - 16)
        flame.draw(Minecraft.getMinecraft(), 82 - 17, 58 - 16)
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 17, 16, 144, 56)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.pot.name")
}
