package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val POT_TEXTURES = ResourceLocation(MODID, "textures/gui/container/pot.png")

class GuiContainerPot(container: ContainerMachine) : GuiContainerMachine(container, POT_TEXTURES) {
}
