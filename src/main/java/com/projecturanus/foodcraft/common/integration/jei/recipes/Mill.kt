package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.MillRecipe
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

class MillWrapper(val helper: IJeiHelpers, val recipe: MillRecipe): IRecipeWrapper {
    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.inputIngredient.matchingStacks.toList())
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
    }
}

class MillCategory(val helper: IJeiHelpers): IRecipeCategory<MillWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/mill.png")
    val staticFlame: IDrawableStatic by lazy { helper.guiHelper.createDrawable(TEXTURES, 176, 0, 14, 14) }
    val flame: IDrawableAnimated by lazy { helper.guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true) }
    val arrow: IDrawableAnimated

    init {
        arrow = helper.guiHelper.drawableBuilder(TEXTURES, 176, 14, 24, 17)
            .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false)
    }

    override fun getUid(): String = FcRecipeUids.MILLING

    override fun getModName(): String = MODID

    override fun drawExtras(minecraft: Minecraft) {
        arrow.draw(minecraft, 76 - 42, 21 - 12)
        flame.draw(Minecraft.getMinecraft(), 81 - 42, 36 - 12)
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: MillWrapper, ingredients: IIngredients) {
        val stackGroup = recipeLayout.itemStacks

        stackGroup.init(0, true, 48 - 42, 18 - 12)
        stackGroup.init(1, false, 111 - 42, 18 - 12)

        stackGroup.set(ingredients)
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 42, 12, 93, 64)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.mill.name")
}
