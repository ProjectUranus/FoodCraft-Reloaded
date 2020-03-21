package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.PanRecipe
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

class PanWrapper(val helper: IJeiHelpers, val recipe: PanRecipe): IRecipeWrapper {
    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, helper.stackHelper.expandRecipeItemStackInputs(recipe.ingredients))
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
    }
}

class PanCategory(val helper: IJeiHelpers): IRecipeCategory<PanWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/pan.png")

    val staticFlame: IDrawableStatic by lazy { helper.guiHelper.createDrawable(TEXTURES, 176, 0, 14, 14) }
    val flame: IDrawableAnimated by lazy { helper.guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true) }

    val arrow: IDrawableAnimated = helper.guiHelper.drawableBuilder(TEXTURES, 176, 14, 24, 17)
        .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false)

    override fun getUid(): String = FcRecipeUids.PAN

    override fun getModName(): String = MODID

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: PanWrapper, ingredients: IIngredients) {
        val stackGroup = recipeLayout.itemStacks
        stackGroup.init(0, true, 41 - 12, 34 - 12)
        stackGroup.init(1, false, 104 - 12, 34 - 12)
        stackGroup.set(ingredients)
    }

    override fun drawExtras(minecraft: Minecraft) {
        arrow.draw(minecraft, 69 - 12, 36 - 13)
        flame.draw(Minecraft.getMinecraft(), 78 - 12, 16 - 13)
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 12, 13, 141, 46)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.pan.name")
}
