package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.client.gui.widget.WidgetCookBar
import com.projecturanus.foodcraft.client.gui.widget.WidgetHeat
import com.projecturanus.foodcraft.client.gui.widget.WidgetProgressBar
import com.projecturanus.foodcraft.common.block.container.ContainerPot
import net.minecraft.util.ResourceLocation

val POT_TEXTURES = ResourceLocation(MODID, "textures/gui/container/pot.png")

class GuiContainerPot(override val container: ContainerPot) : GuiContainerMachine(container, POT_TEXTURES) {
    val tileEntity by lazy { container.tileEntity }
    val heatHandler by lazy { tileEntity.heatHandler }
    val progress get() = container.progress / container.minProgress.toDouble()
    val overcookProgress get() = (container.progress - container.minProgress) / (container.maxProgress - container.minProgress).toDouble()

    val widgetHeat = WidgetHeat(176, 0, container::heat.getter)
    val widgetProgress by lazy { WidgetProgressBar(176, 15, this::progress.getter) }
    val widgetCookBar by lazy { WidgetCookBar(176, 31, this::overcookProgress.getter) }

    override fun initGui() {
        super.initGui()
        widgetHeat.temperature = heatHandler
        widgetHeat.setWorldAndResolution(mc, width, height)
        widgetProgress.setWorldAndResolution(mc, width, height)
        widgetCookBar.setWorldAndResolution(mc, width, height)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        val i = (width - xSize) / 2
        val j = (height - ySize) / 2

        widgetHeat.draw(i + 81, j + 64, mouseX, mouseY, partialTicks)
        widgetProgress.draw(i + 93, j + 20, mouseX, mouseY, partialTicks)
        widgetCookBar.draw(i + 48, j + 38, mouseX, mouseY, partialTicks)
    }
}
