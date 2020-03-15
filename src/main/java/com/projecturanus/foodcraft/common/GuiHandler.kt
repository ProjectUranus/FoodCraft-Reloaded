package com.projecturanus.foodcraft.common

import com.projecturanus.foodcraft.client.gui.*
import com.projecturanus.foodcraft.common.block.container.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

const val STOVE = 0
const val BEVERAGE_MAKING = 1
const val BREW_BARREL = 2
const val CHOPPING_BOARD = 3
const val FRYING_PAN = 4
const val MILL = 5
const val PAN = 6
const val POT = 7
const val PRESSURE_COOKER = 8

object GuiHandler : IGuiHandler {
    @SideOnly(Side.CLIENT)
    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? =
        when (ID) {
            STOVE -> GuiContainerStove(getServerGuiElement(ID, player, world, x, y, z) as ContainerStove)
            BEVERAGE_MAKING -> GuiContainerBeverageMaking(getServerGuiElement(ID, player, world, x, y, z) as ContainerBeverageMaking)
            BREW_BARREL -> GuiContainerBrewBarrel(getServerGuiElement(ID, player, world, x, y, z) as ContainerBrewBarrel)
            CHOPPING_BOARD -> GuiContainerChoppingBoard(getServerGuiElement(ID, player, world, x, y, z) as ContainerChoppingBoard)
            FRYING_PAN -> GuiContainerFryingPan(getServerGuiElement(ID, player, world, x, y, z) as ContainerFryingPan)
            MILL -> GuiContainerMill(getServerGuiElement(ID, player, world, x, y, z) as ContainerMill)
            PAN -> GuiContainerPan(getServerGuiElement(ID, player, world, x, y, z) as ContainerPan)
            POT -> GuiContainerPot(getServerGuiElement(ID, player, world, x, y, z) as ContainerPot)
            PRESSURE_COOKER -> GuiContainerPressureCooker(getServerGuiElement(ID, player, world, x, y, z) as ContainerPressureCooker)
            else -> null
        }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        val tileEntity = world.getTileEntity(BlockPos(x, y, z))
        return when (ID) {
            STOVE -> ContainerStove(player.inventory, tileEntity!!)
            BEVERAGE_MAKING -> ContainerBeverageMaking(player.inventory, tileEntity!!)
            BREW_BARREL -> ContainerBrewBarrel(player.inventory, tileEntity!!)
            CHOPPING_BOARD -> ContainerChoppingBoard(player.inventory, tileEntity!!)
            FRYING_PAN -> ContainerFryingPan(player.inventory, tileEntity!!)
            MILL -> ContainerMill(player.inventory, tileEntity!!)
            PAN -> ContainerPan(player.inventory, tileEntity!!)
            POT -> ContainerPot(player.inventory, tileEntity!!)
            PRESSURE_COOKER -> ContainerPressureCooker(player.inventory, tileEntity!!)
            else -> null
        }
    }
}
