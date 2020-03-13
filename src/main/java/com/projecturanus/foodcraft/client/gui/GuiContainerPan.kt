package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val PAN_TEXTURES = ResourceLocation(MODID, "textures/gui/container/pan.png")

class GuiContainerPan(container: ContainerMachine) : GuiContainerMachine(container, PAN_TEXTURES) {
}
