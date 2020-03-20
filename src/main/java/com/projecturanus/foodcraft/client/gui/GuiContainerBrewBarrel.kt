package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerBrewBarrel
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.config.GuiUtils
import org.lwjgl.util.Rectangle
import java.time.Duration


val BREW_BARREL_TEXTURES = ResourceLocation(MODID, "textures/gui/container/brew_barrel.png")

class GuiContainerBrewBarrel(override val container: ContainerBrewBarrel) : GuiContainerMachine(container, BREW_BARREL_TEXTURES) {
    val tileEntity by lazy { container.tileEntity }
    val rectangle: Rectangle = Rectangle(18, 14, 11, 59)
    val renderer by lazy { FluidStackRenderer(tileEntity.fluidTank.capacity, true, 11, 59) }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)
        val i = (width - xSize) / 2
        val j = (height - ySize) / 2
        renderer.render(Minecraft.getMinecraft(), 18, 14, container.fluidStack)
        if (rectangle.contains(mouseX - guiLeft, mouseY - guiTop) && container.fluidStack != null) {
            val scaledresolution = ScaledResolution(mc)
            GuiUtils.drawHoveringText(ItemStack.EMPTY, renderer.getTooltip(mc, container.fluidStack, ITooltipFlag.TooltipFlags.NORMAL), mouseX - guiLeft, mouseY - guiTop, scaledresolution.scaledWidth, scaledresolution.scaledHeight, -1, fontRenderer)
        }
        if (container.progress > 0) {
            fontRenderer.drawString(Duration.ofMillis((3600L - container.progress) * 50).toString(), i + 53, j + 53, 4210752)
        }
    }
}
