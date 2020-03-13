package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val BEVERAGE_MAKING_TEXTURES = ResourceLocation(MODID, "textures/gui/container/beverage_making.png")

class GuiContainerBeverageMaking(container: ContainerMachine) : GuiContainerMachine(container, BEVERAGE_MAKING_TEXTURES) {
}
