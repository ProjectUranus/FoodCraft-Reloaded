package com.projecturanus.foodcraft.client.gui.widget

import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature
import kotlin.reflect.KProperty0

class WidgetHeat(val x: Int, val y: Int, val trueTemperature: KProperty0.Getter<Double>) : Widget() {
    var temperature: ITemperature = DefaultTemperature()

    companion object {
        const val WIDTH = 14
        const val HEIGHT = 14
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val progress = (((trueTemperature.invoke() - temperature.minimumTemperature) / (temperature.maximumTemperature  - temperature.minimumTemperature)) * 14).toInt()
        drawTexturedModalRect(x, y - progress + HEIGHT, this.x, this.y + HEIGHT - progress, WIDTH, progress)
        if (isMouseIn(x, y, mouseX, mouseY)) {
            this.drawHoveringText(listOf("Heat: ${trueTemperature.invoke()} / ${temperature.maximumTemperature}"), x, y, fontRenderer)
        }
    }
}
