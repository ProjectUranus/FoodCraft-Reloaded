package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.client.gui.widget.WidgetHeat
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import com.projecturanus.foodcraft.common.block.entity.TileEntityStove
import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import net.minecraft.util.ResourceLocation

val STOVE_TEXTURES = ResourceLocation(MODID, "textures/gui/container/stove.png")

class GuiContainerStove(container: ContainerMachine) : GuiContainerMachine(container, STOVE_TEXTURES) {
    val fuelHandler by lazy { container.tileEntity.getCapability(InjectedCapabilities.TEMPERATURE, null) as FuelHeatHandler }
    val currentBurnTime = (container.tileEntity as TileEntityStove)::currentItemBurnTime

    val widgetHeat = WidgetHeat()

    override fun initGui() {
        super.initGui()
        widgetHeat.temperature = fuelHandler
        widgetHeat.setWorldAndResolution(mc, width, height)
    }

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY)
        val i = (width - xSize) / 2
        val j = (height - ySize) / 2

        widgetHeat.draw(i + 81, j + 48, mouseX, mouseY, partialTicks)

        if (fuelHandler.hasWork()) {
            this.drawTexturedModalRect(i + 83, j + 22, 176, 14, 9, 9)
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}
