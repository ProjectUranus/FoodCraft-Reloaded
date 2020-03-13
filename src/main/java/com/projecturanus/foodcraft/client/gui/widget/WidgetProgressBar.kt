package com.projecturanus.foodcraft.client.gui.widget

class WidgetProgressBar(val x: Int, val y: Int) : Widget() {
    var progress = 0.0

    companion object {
        const val WIDTH = 24
        const val HEIGHT = 16
    }

    override fun isMouseIn(x: Int, y: Int, mouseX: Int, mouseY: Int): Boolean {
        return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT
    }

    override fun draw(x: Int, y: Int, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val l: Int = (progress * WIDTH).toInt()
        this.drawTexturedModalRect(x, y, this.x, this.y, l + 1, 16)
        if (isMouseIn(x, y, mouseX, mouseY)) {
            this.drawHoveringText(listOf("Progress: $progress"), x, y, fontRenderer)
        }
    }
}
