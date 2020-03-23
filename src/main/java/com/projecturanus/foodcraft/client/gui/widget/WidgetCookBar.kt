package com.projecturanus.foodcraft.client.gui.widget

import com.projecturanus.foodcraft.common.config.FcConfig
import kotlin.reflect.KProperty0

class WidgetCookBar(val x: Int, val y: Int, val progress: KProperty0.Getter<Double>) : Widget() {

    companion object {
        const val WIDTH = 78
        const val HEIGHT = 3
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val l: Int = (progress.invoke() * WIDTH).toInt()
        this.drawTexturedModalRect(x, y, this.x, this.y, l, HEIGHT)
        if (isMouseIn(x, y, mouseX, mouseY) && l >= 0 && FcConfig.clientConfig.enableHoverInfo) {
            this.drawHoveringText(listOf(String.format("Overcooked progress: %.2f%%", progress.invoke() * 100.0)), x, y, fontRenderer)
        }
    }
}
