package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.logger
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.texture.TextureUtil
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidStack
import org.lwjgl.opengl.GL11
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.*
import javax.annotation.Nonnull


/** Renders the given texture tiled into a GUI  */
fun renderTiledTextureAtlas(x: Int, y: Int, width: Int, height: Int, depth: Float, sprite: TextureAtlasSprite) {
    val tessellator = Tessellator.getInstance()
    val worldRenderer = tessellator.buffer
    worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
    Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
    putTiledTextureQuads(worldRenderer, x, y, width, height, depth, sprite)
    tessellator.draw()
}

fun renderTiledFluid(x: Int, y: Int, width: Int, height: Int, depth: Float, fluidStack: FluidStack) {
    val map: TextureMap = Minecraft.getMinecraft().textureMapBlocks
    var fluidSprite: TextureAtlasSprite? = map.getTextureExtry(fluidStack.fluid.still.toString())
    if (fluidSprite == null) {
        fluidSprite = map.getTextureExtry(TextureMap.LOCATION_MISSING_TEXTURE.toString())
        if (fluidSprite == null) return
    }
    setColorRGBA(fluidStack.fluid.getColor(fluidStack))
    renderTiledTextureAtlas(x, y, width, height, depth, fluidSprite)
}

fun setColorRGB(color: Int) {
    setColorRGBA(color or -0x1000000)
}

fun setColorRGBA(color: Int) {
    val a = alpha(color).toFloat() / 255.0f
    val r = red(color).toFloat() / 255.0f
    val g = green(color).toFloat() / 255.0f
    val b = blue(color).toFloat() / 255.0f
    GlStateManager.color(r, g, b, a)
}

fun alpha(c: Int): Int {
    return c shr 24 and 0xFF
}

fun red(c: Int): Int {
    return c shr 16 and 0xFF
}

fun green(c: Int): Int {
    return c shr 8 and 0xFF
}

fun blue(c: Int): Int {
    return c and 0xFF
}

/** Adds a quad to the rendering pipeline. Call startDrawingQuads beforehand. You need to call draw() yourself.  */
fun putTiledTextureQuads(renderer: BufferBuilder, x: Int, y: Int, width: Int, height: Int, depth: Float, sprite: TextureAtlasSprite) {
    var y = y
    var height = height
    val u1 = sprite.minU
    val v1 = sprite.minV

    // tile vertically
    do {
        val renderHeight = Math.min(sprite.iconHeight, height)
        height -= renderHeight
        val v2 = sprite.getInterpolatedV(16.0 * renderHeight / sprite.iconHeight)

        // we need to draw the quads per width too
        var x2 = x
        var width2 = width
        // tile horizontally
        do {
            val renderWidth = Math.min(sprite.iconWidth, width2)
            width2 -= renderWidth
            val u2 = sprite.getInterpolatedU(16.0 * renderWidth / sprite.iconWidth)
            renderer.pos(x2.toDouble(), y.toDouble(), depth.toDouble()).tex(u1.toDouble(), v1.toDouble()).endVertex()
            renderer.pos(x2.toDouble(), y + renderHeight.toDouble(), depth.toDouble()).tex(u1.toDouble(), v2.toDouble()).endVertex()
            renderer.pos(x2 + renderWidth.toDouble(), y + renderHeight.toDouble(), depth.toDouble()).tex(u2.toDouble(), v2.toDouble()).endVertex()
            renderer.pos(x2 + renderWidth.toDouble(), y.toDouble(), depth.toDouble()).tex(u2.toDouble(), v1.toDouble()).endVertex()
            x2 += renderWidth
        } while (width2 > 0)
        y += renderHeight
    } while (height > 0)
}

fun getAverageColorOfItem(@Nonnull stack: ItemStack?): Optional<Color> {
    if (Minecraft.getMinecraft().renderItem == null) return Optional.empty()
    val model = Minecraft.getMinecraft().renderItem.itemModelMesher.getItemModel(stack)
    var redBucket: Long = 0
    var greenBucket: Long = 0
    var blueBucket: Long = 0
    var alphaBucket: Long = 0
    var pixelCount: Long = 0
    for (bakedQuad in model.getQuads(null, null, 0)) {
        for (i in 0 until bakedQuad.sprite.frameCount) {
            val quadData = bakedQuad.sprite.getFrameTextureData(i)
            for (aQuadData in quadData) {
                for (anAQuadData in aQuadData) {
                    if (anAQuadData == 0) continue
                    val c = Color(anAQuadData, true)
                    pixelCount++
                    redBucket += c.getRed()
                    greenBucket += c.getGreen()
                    blueBucket += c.getBlue()
                    alphaBucket += c.getAlpha()
                }
            }
        }
    }
    if (pixelCount == 0L) return Optional.empty()
    val averageColor = Color((redBucket / pixelCount).toInt(),
        (greenBucket / pixelCount).toInt(),
        (blueBucket / pixelCount).toInt(),
        (alphaBucket / pixelCount).toInt())
    return Optional.of(averageColor)
}

fun getAverageColor(@Nonnull resourceLocation: ResourceLocation): Optional<Color> {
    return try {
        val resource = Minecraft.getMinecraft().resourceManager
            .getResource(resourceLocation)
        var image: Image = TextureUtil.readBufferedImage(resource.inputStream)
        image = image.getScaledInstance(1, 1, Image.SCALE_DEFAULT)
        val image1 = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        val graphics2D = image1.createGraphics()
        graphics2D.drawImage(image, 0, 0, null)
        graphics2D.dispose()
        logger.info("Got " + resourceLocation + " " + Color(image1.getRGB(0, 0)))
        Optional.of(Color(image1.getRGB(0, 0)))
    } catch (e: IOException) {
        logger.info("Cannot read image $resourceLocation", e)
        Optional.empty()
    }
}
