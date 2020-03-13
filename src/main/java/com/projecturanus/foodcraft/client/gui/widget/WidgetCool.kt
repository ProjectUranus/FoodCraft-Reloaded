package com.projecturanus.foodcraft.client.gui.widget

import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class WidgetCool : Widget() {
    var temperature: ITemperature = DefaultTemperature()

    companion object {
        const val WIDTH = 13
        const val HEIGHT = 12
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val progress = 11 - ((temperature.temperature / temperature.minimumTemperature) * 11).toInt()
        drawTexturedModalRect(x, y - progress, 176, 12 - progress, 14, progress + 2)
        if (isMouseIn(x, y, mouseX, mouseY)) {
            this.drawHoveringText(listOf("Heat: ${temperature.temperature} / ${temperature.minimumTemperature}"), x, y, fontRenderer)
        }
    }
}
