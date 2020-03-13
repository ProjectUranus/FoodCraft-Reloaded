package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val CHOPPING_BOARD_TEXTURES = ResourceLocation(MODID, "textures/gui/container/chopping_board.png")

class GuiContainerChoppingBoard(container: ContainerMachine) : GuiContainerMachine(container, CHOPPING_BOARD_TEXTURES) {
}
