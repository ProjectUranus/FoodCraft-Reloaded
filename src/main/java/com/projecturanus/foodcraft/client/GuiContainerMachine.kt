package com.projecturanus.foodcraft.client

import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.client.gui.inventory.GuiContainer

abstract class GuiContainerMachine(val container: ContainerMachine) : GuiContainer(container) {
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawDefaultBackground()
        super.drawScreen(mouseX, mouseY, partialTicks)
        renderHoveredToolTip(mouseX, mouseY)
    }

    override fun drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) {
        val s: String = container.displayName.unformattedText
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752)
        fontRenderer.drawString(container.playerInventory.displayName.unformattedText, 8, ySize - 96 + 2, 4210752)
    }
}
