package com.projecturanus.foodcraft.common.integration.jei.recipes

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerBeverageMaking
import com.projecturanus.foodcraft.common.integration.jei.FcRecipeUids
import com.projecturanus.foodcraft.common.recipe.BeverageMakingRecipe
import mezz.jei.api.IJeiHelpers
import mezz.jei.api.gui.IDrawable
import mezz.jei.api.gui.IDrawableAnimated
import mezz.jei.api.gui.IDrawableStatic
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.ingredients.VanillaTypes
import mezz.jei.api.recipe.IRecipeCategory
import mezz.jei.api.recipe.IRecipeWrapper
import mezz.jei.api.recipe.transfer.IRecipeTransferError
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

lateinit var helperLate: IJeiHelpers

class BeverageMakingWrapper(val helper: IJeiHelpers, val recipe: BeverageMakingRecipe): IRecipeWrapper {

    companion object {
        val helper: IJeiHelpers by lazy { helperLate }
        val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/beverage_making.png")
        val staticFlame: IDrawableStatic by lazy { helper.guiHelper.createDrawable(TEXTURES, 176, 0, 14, 14) }
        val flame: IDrawableAnimated by lazy { helper.guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true) }
        val staticCool: IDrawableStatic by lazy { helper.guiHelper.createDrawable(TEXTURES, 190, 0, 12, 12) }
        val cool: IDrawableAnimated by lazy { helper.guiHelper.createAnimatedDrawable(staticCool, 300, IDrawableAnimated.StartDirection.TOP, true) }
    }

    init {
        helperLate = helper
    }

    override fun getIngredients(ingredients: IIngredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.inputIngredient.matchingStacks.toList())
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput())
        recipe.fluidInput?.let { ingredients.setInput(VanillaTypes.FLUID, it) }
    }

    override fun drawInfo(minecraft: Minecraft, recipeWidth: Int, recipeHeight: Int, mouseX: Int, mouseY: Int) {
        if (recipe.cool == true)
            cool.draw(Minecraft.getMinecraft(), 143 - 7, 50 - 8)
        else if (recipe.cool == false)
            flame.draw(Minecraft.getMinecraft(), 141 - 7, 19 - 8)
    }
}

class BeverageMakingCategory(val helper: IJeiHelpers): IRecipeCategory<BeverageMakingWrapper> {
    val TEXTURES = ResourceLocation(MODID, "textures/gui/jei/beverage_making.png")
    val arrow: IDrawableAnimated

    init {
        arrow = helper.guiHelper.drawableBuilder(TEXTURES, 176, 14, 24, 17)
            .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false)
    }

    override fun getUid(): String = FcRecipeUids.BEVERAGE_MAKING

    override fun getModName(): String = MODID

    override fun drawExtras(minecraft: Minecraft) {
        arrow.draw(minecraft, 55 - 7, 26 - 8)
    }

    override fun setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: BeverageMakingWrapper, ingredients: IIngredients) {
        recipeLayout.fluidStacks.init(0, true, 15 - 7, 12 - 8, 11, 57, 4000, true, null)

        recipeWrapper.recipe.fluidInput?.let { recipeLayout.fluidStacks.set(0, it) }

        val stackGroup = recipeLayout.itemStacks

        stackGroup.init(0, true, 33 - 7, 26 - 8)
        stackGroup.init(1, false, 81 - 7, 26 - 8)

        val inputs = ingredients.getInputs(VanillaTypes.ITEM)
        stackGroup.set(0, inputs[0])
        stackGroup.set(1, ingredients.getOutputs(VanillaTypes.ITEM)[0])
    }

    override fun getBackground(): IDrawable = helper.guiHelper.createDrawable(TEXTURES, 7, 8, 150, 64)

    @SideOnly(Side.CLIENT)
    override fun getTitle(): String = I18n.format("tile.$MODID.beverage_making.name")
}
