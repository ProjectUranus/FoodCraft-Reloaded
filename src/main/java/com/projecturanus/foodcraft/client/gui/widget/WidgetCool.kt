package com.projecturanus.foodcraft.client.gui.widget

import com.projecturanus.foodcraft.common.config.FcConfig
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class WidgetCool(val x: Int, val y: Int, val temperature: ITemperature) : Widget() {

    companion object {
        const val WIDTH = 13
        const val HEIGHT = 12
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val progress = (((temperature.maximumTemperature - temperature.temperature) / (temperature.maximumTemperature - temperature.minimumTemperature)) * 12).toInt()
        drawTexturedModalRect(x, y - progress + HEIGHT, this.x, this.y + HEIGHT - progress, WIDTH, progress)
        if (isMouseIn(x, y, mouseX, mouseY) && FcConfig.clientConfig.enableHoverInfo) {
            this.drawHoveringText(listOf("Heat: ${temperature.temperature} / ${temperature.minimumTemperature}"), x, y, fontRenderer)
        }
    }
}
