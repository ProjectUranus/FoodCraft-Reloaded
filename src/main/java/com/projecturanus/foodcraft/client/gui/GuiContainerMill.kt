package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val MILL_TEXTURES = ResourceLocation(MODID, "textures/gui/container/beverage_making.png")

class GuiContainerMill(container: ContainerMachine) : GuiContainerMachine(container, MILL_TEXTURES) {
}
