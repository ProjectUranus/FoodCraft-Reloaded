package com.projecturanus.foodcraft.client.gui

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidStack
import java.util.*


/**
 * Exported from JEI
 * @author mezz
 */
class FluidStackRenderer(val capacityMb: Int = Fluid.BUCKET_VOLUME, val tooltipMode: TooltipMode = TooltipMode.ITEM_LIST, val width: Int = TEX_WIDTH, val height: Int = TEX_HEIGHT) {

    enum class TooltipMode {
        SHOW_AMOUNT, SHOW_AMOUNT_AND_CAPACITY, ITEM_LIST
    }

    constructor(capacityMb: Int, showCapacity: Boolean, width: Int, height: Int) : this(capacityMb, if (showCapacity) TooltipMode.SHOW_AMOUNT_AND_CAPACITY else TooltipMode.SHOW_AMOUNT, width, height) {}

    fun render(minecraft: Minecraft, xPosition: Int, yPosition: Int, fluidStack: FluidStack?) {
        GlStateManager.enableBlend()
        GlStateManager.enableAlpha()
        drawFluid(minecraft, xPosition, yPosition, fluidStack)
        GlStateManager.color(1f, 1f, 1f, 1f)
        GlStateManager.disableAlpha()
        GlStateManager.disableBlend()
    }

    private fun drawFluid(minecraft: Minecraft, xPosition: Int, yPosition: Int, fluidStack: FluidStack?) {
        if (fluidStack == null) {
            return
        }
        val fluid = fluidStack.fluid ?: return
        val fluidStillSprite = getStillFluidSprite(minecraft, fluid)
        val fluidColor = fluid.getColor(fluidStack)
        var scaledAmount = fluidStack.amount * height / capacityMb
        if (fluidStack.amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
            scaledAmount = MIN_FLUID_HEIGHT
        }
        if (scaledAmount > height) {
            scaledAmount = height
        }
        drawTiledSprite(minecraft, xPosition, yPosition, width, height, fluidColor, scaledAmount, fluidStillSprite)
    }

    private fun drawTiledSprite(minecraft: Minecraft, xPosition: Int, yPosition: Int, tiledWidth: Int, tiledHeight: Int, color: Int, scaledAmount: Int, sprite: TextureAtlasSprite) {
        minecraft.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
        setGLColorFromInt(color)
        val xTileCount = tiledWidth / TEX_WIDTH
        val xRemainder = tiledWidth - xTileCount * TEX_WIDTH
        val yTileCount = scaledAmount / TEX_HEIGHT
        val yRemainder = scaledAmount - yTileCount * TEX_HEIGHT
        val yStart = yPosition + tiledHeight
        for (xTile in 0..xTileCount) {
            for (yTile in 0..yTileCount) {
                val width = if (xTile == xTileCount) xRemainder else TEX_WIDTH
                val height = if (yTile == yTileCount) yRemainder else TEX_HEIGHT
                val x = xPosition + xTile * TEX_WIDTH
                val y = yStart - (yTile + 1) * TEX_HEIGHT
                if (width > 0 && height > 0) {
                    val maskTop = TEX_HEIGHT - height
                    val maskRight = TEX_WIDTH - width
                    drawTextureWithMasking(x.toDouble(), y.toDouble(), sprite, maskTop, maskRight, 100.0)
                }
            }
        }
    }

    fun getTooltip(minecraft: Minecraft?, fluidStack: FluidStack?, tooltipFlag: ITooltipFlag?): List<String> {
        if (fluidStack == null) return emptyList()
        val tooltip: MutableList<String> = ArrayList()
        val fluidType = fluidStack.fluid ?: return tooltip
        val fluidName = fluidType.getLocalizedName(fluidStack)
        tooltip.add(fluidName)
        if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
            val amount: String = I18n.format("jei.tooltip.liquid.amount.with.capacity", fluidStack.amount, capacityMb)
            tooltip.add(TextFormatting.GRAY.toString() + amount)
        } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
            val amount: String = I18n.format("jei.tooltip.liquid.amount", fluidStack.amount)
            tooltip.add(TextFormatting.GRAY.toString() + amount)
        }
        return tooltip
    }

    companion object {
        private const val TEX_WIDTH = 16
        private const val TEX_HEIGHT = 16
        private const val MIN_FLUID_HEIGHT = 1 // ensure tiny amounts of fluid are still visible
        private fun getStillFluidSprite(minecraft: Minecraft, fluid: Fluid): TextureAtlasSprite {
            val textureMapBlocks: TextureMap = minecraft.textureMapBlocks
            val fluidStill = fluid.still
            var fluidStillSprite: TextureAtlasSprite? = null
            if (fluidStill != null) {
                fluidStillSprite = textureMapBlocks.getTextureExtry(fluidStill.toString())
            }
            return fluidStillSprite ?: textureMapBlocks.missingSprite
        }

        private fun setGLColorFromInt(color: Int) {
            val red = (color shr 16 and 0xFF) / 255.0f
            val green = (color shr 8 and 0xFF) / 255.0f
            val blue = (color and 0xFF) / 255.0f
            GlStateManager.color(red, green, blue, 1.0f)
        }

        private fun drawTextureWithMasking(xCoord: Double, yCoord: Double, textureSprite: TextureAtlasSprite, maskTop: Int, maskRight: Int, zLevel: Double) {
            val uMin = textureSprite.minU.toDouble()
            var uMax = textureSprite.maxU.toDouble()
            val vMin = textureSprite.minV.toDouble()
            var vMax = textureSprite.maxV.toDouble()
            uMax = uMax - maskRight / 16.0 * (uMax - uMin)
            vMax = vMax - maskTop / 16.0 * (vMax - vMin)
            val tessellator = Tessellator.getInstance()
            val bufferBuilder = tessellator.buffer
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
            bufferBuilder.pos(xCoord, yCoord + 16, zLevel).tex(uMin, vMax).endVertex()
            bufferBuilder.pos(xCoord + 16 - maskRight, yCoord + 16, zLevel).tex(uMax, vMax).endVertex()
            bufferBuilder.pos(xCoord + 16 - maskRight, yCoord + maskTop, zLevel).tex(uMax, vMin).endVertex()
            bufferBuilder.pos(xCoord, yCoord + maskTop, zLevel).tex(uMin, vMin).endVertex()
            tessellator.draw()
        }
    }

}
