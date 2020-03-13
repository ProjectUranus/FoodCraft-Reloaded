package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val FRYING_PAN_TEXTURES = ResourceLocation(MODID, "textures/gui/container/frying_pan.png")

class GuiContainerFryingPan(container: ContainerMachine) : GuiContainerMachine(container, FRYING_PAN_TEXTURES) {
}
