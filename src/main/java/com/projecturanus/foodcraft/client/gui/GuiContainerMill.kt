package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.client.gui.widget.WidgetHeat
import com.projecturanus.foodcraft.client.gui.widget.WidgetProgressBar
import com.projecturanus.foodcraft.common.block.container.ContainerMill
import net.minecraft.util.ResourceLocation

val MILL_TEXTURES = ResourceLocation(MODID, "textures/gui/container/mill.png")

class GuiContainerMill(override val container: ContainerMill) : GuiContainerMachine(container, MILL_TEXTURES) {
    val tileEntity by lazy { container.tileEntity }
    val heatHandler by lazy { tileEntity.heatHandler }
    val progress get() = container.progress / 200.0

    val widgetHeat = WidgetHeat(176, 0, container::heat.getter)
    val widgetProgress by lazy { WidgetProgressBar(176, 14, this::progress.getter) }

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

        widgetHeat.draw(i + 81, j + 37, mouseX, mouseY, partialTicks)
        widgetProgress.draw(i + 75, j + 20, mouseX, mouseY, partialTicks)
    }
}
