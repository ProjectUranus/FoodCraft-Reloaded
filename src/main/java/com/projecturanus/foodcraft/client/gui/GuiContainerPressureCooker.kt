package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.client.gui.widget.WidgetHeat
import com.projecturanus.foodcraft.client.gui.widget.WidgetProgressBar
import com.projecturanus.foodcraft.common.block.container.ContainerPressureCooker
import com.projecturanus.foodcraft.common.config.FcConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.config.GuiUtils
import org.lwjgl.util.Rectangle

val PRESSURE_COOKER_TEXTURES = ResourceLocation(MODID, "textures/gui/container/pressure_cooker.png")

class GuiContainerPressureCooker(override val container: ContainerPressureCooker) : GuiContainerMachine(container, PRESSURE_COOKER_TEXTURES) {
    val tileEntity by lazy { container.tileEntity }
    val heatHandler by lazy { tileEntity.heatHandler }
    val progress get() = container.progress / FcConfig.machineConfig.pressureCookerProgress.toDouble()

    val widgetHeat = WidgetHeat(176, 0, container::heat.getter)
    val widgetProgress by lazy { WidgetProgressBar(176, 14, this::progress.getter) }
    val rectangle: Rectangle = Rectangle(18, 14, 11, 59)
    val renderer by lazy { FluidStackRenderer(tileEntity.fluidTank.capacity, true, 11, 59) }

    override fun initGui() {
        super.initGui()
        widgetHeat.temperature = heatHandler
        widgetHeat.setWorldAndResolution(mc, width, height)
        widgetProgress.setWorldAndResolution(mc, width, height)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        val i = (width - xSize) / 2
        val j = (height - ySize) / 2

        widgetHeat.draw(i + 122, j + 62, mouseX, mouseY, partialTicks)
        widgetProgress.draw(i + 118, j + 30, mouseX, mouseY, partialTicks)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY)
        renderer.render(Minecraft.getMinecraft(), 18, 14, container.fluidStack)
        if (rectangle.contains(mouseX - guiLeft, mouseY - guiTop) && container.fluidStack != null) {
            val scaledresolution = ScaledResolution(mc)
            GuiUtils.drawHoveringText(ItemStack.EMPTY, renderer.getTooltip(mc, container.fluidStack, ITooltipFlag.TooltipFlags.NORMAL), mouseX - guiLeft, mouseY - guiTop, scaledresolution.scaledWidth, scaledresolution.scaledHeight, -1, fontRenderer)
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}
