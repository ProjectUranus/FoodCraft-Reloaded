package com.projecturanus.foodcraft.client.gui

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import net.minecraft.util.ResourceLocation

val BREW_BARREL_TEXTURES = ResourceLocation(MODID, "textures/gui/container/brew_barrel.png")

class GuiContainerBrewBarrel(container: ContainerMachine) : GuiContainerMachine(container, BREW_BARREL_TEXTURES) {
}
