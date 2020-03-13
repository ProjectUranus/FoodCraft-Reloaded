package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val PRESSURE_COOKER_TEXTURES = ResourceLocation(MODID, "textures/gui/container/pressure_cooker.png")

class GuiContainerPressureCooker(container: ContainerMachine) : GuiContainerMachine(container, PRESSURE_COOKER_TEXTURES) {
}
