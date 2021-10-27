package com.projecturanus.foodcraft.client.gui.widget

import com.projecturanus.foodcraft.common.config.FcConfig
import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature
import kotlin.reflect.KProperty0

class WidgetCool(val x: Int, val y: Int, val trueTemperature: KProperty0.Getter<Double>) : Widget() {
    var temperature: ITemperature =
        DefaultTemperature()

    companion object {
        const val WIDTH = 13
        const val HEIGHT = 12
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val progress = (((temperature.maximumTemperature - trueTemperature.invoke()) / (temperature.maximumTemperature - temperature.minimumTemperature)) * 12).toInt()
        drawTexturedModalRect(x, y - progress + HEIGHT, this.x, this.y + HEIGHT - progress, WIDTH, progress)
        if (isMouseIn(x, y, mouseX, mouseY) && FcConfig.clientConfig.enableHoverInfo) {
            this.drawHoveringText(listOf("Heat: ${trueTemperature.invoke()} / ${temperature.minimumTemperature}"), x, y, fontRenderer)
        }
    }
}
