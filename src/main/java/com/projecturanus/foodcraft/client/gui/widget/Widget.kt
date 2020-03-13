package com.projecturanus.foodcraft.client.gui.widget

import net.minecraft.client.gui.GuiScreen

open class Widget : GuiScreen() {

    open fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean = false

    open fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
    }
}
