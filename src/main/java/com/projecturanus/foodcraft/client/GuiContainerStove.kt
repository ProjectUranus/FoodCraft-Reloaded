package com.projecturanus.foodcraft.client

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import com.projecturanus.foodcraft.common.block.entity.TileEntityStove
import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation

val TEXTURES = ResourceLocation(MODID, "textures/gui/container/stove.png")

class GuiContainerStove(container: ContainerMachine) : GuiContainerMachine(container) {
    val fuelHandler by lazy { container.tileEntity.getCapability(InjectedCapabilities.TEMPERATURE, null) as FuelHeatHandler }
    val currentBurnTime = (container.tileEntity as TileEntityStove)::currentItemBurnTime

    override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        mc.textureManager.bindTexture(TEXTURES)
        val i = (width - xSize) / 2
        val j = (height - ySize) / 2
        this.drawTexturedModalRect(i, j, 0, 0, xSize, ySize)

        if (fuelHandler.canWork()) {
            val k = getBurnLeftScaled(13)
            this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1)
        }
    }

    private fun getBurnLeftScaled(pixels: Int): Int {
        var i: Int = fuelHandler.burnTime.toInt()
        if (i == 0) {
            i = 200
        }
        return currentBurnTime.get() * pixels / i
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
    }
}
