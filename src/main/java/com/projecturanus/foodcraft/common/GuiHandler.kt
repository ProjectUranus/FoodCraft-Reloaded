package com.projecturanus.foodcraft.common

import com.projecturanus.foodcraft.client.GuiContainerStove
import com.projecturanus.foodcraft.common.block.container.ContainerMachine
import com.projecturanus.foodcraft.common.block.container.ContainerStove
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

const val STOVE = 0

object GuiHandler : IGuiHandler {
    @SideOnly(Side.CLIENT)
    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? =
        when (ID) {
            STOVE -> GuiContainerStove(getServerGuiElement(ID, player, world, x, y, z) as ContainerMachine)
            else -> null
        }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? =
        when (ID) {
            STOVE -> ContainerStove(player.inventory, world.getTileEntity(BlockPos(x, y, z))!!)
            else -> null
        }
}
