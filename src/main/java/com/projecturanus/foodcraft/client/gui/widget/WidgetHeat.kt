package com.projecturanus.foodcraft.client.gui.widget

import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class WidgetHeat : Widget() {
    var temperature: ITemperature = DefaultTemperature()

    companion object {
        const val WIDTH = 14
        const val HEIGHT = 14
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val progress = ((temperature.temperature / temperature.maximumTemperature) * 12).toInt()
        drawTexturedModalRect(x, y - progress, 176, 12 - progress, 14, progress + 2)
        if (isMouseIn(x, y, mouseX, mouseY)) {
            this.drawHoveringText(listOf("Heat: ${temperature.temperature} / ${temperature.maximumTemperature}"), x, y, fontRenderer)
        }
    }
}
