package com.projecturanus.foodcraft.client.gui.widget

import com.projecturanus.foodcraft.common.config.FcConfig
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class WidgetHeat(val x: Int, val y: Int, val temperature: ITemperature) : Widget() {

    companion object {
        const val WIDTH = 14
        const val HEIGHT = 14
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val progress = (((temperature.temperature - temperature.minimumTemperature) / (temperature.maximumTemperature  - temperature.minimumTemperature)) * 14).toInt()
        drawTexturedModalRect(x, y - progress + HEIGHT, this.x, this.y + HEIGHT - progress, WIDTH, progress)
        if (isMouseIn(x, y, mouseX, mouseY) && FcConfig.clientConfig.enableHoverInfo) {
            this.drawHoveringText(listOf("Heat: ${temperature.temperature} / ${temperature.maximumTemperature}"), x, y, fontRenderer)
        }
    }
}
