package com.projecturanus.foodcraft.common.network.handler

import com.projecturanus.foodcraft.common.block.container.FluidContainer
import com.projecturanus.foodcraft.common.network.S2CFluidUpdate
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ClientFluidUpdateHandler : IMessageHandler<S2CFluidUpdate, IMessage?> {
    @SideOnly(Side.CLIENT)
    override fun onMessage(message: S2CFluidUpdate, ctx: MessageContext): IMessage? {
        val player = Minecraft.getMinecraft().player
        val container = player.openContainer
        if (container is FluidContainer) {
            container.fluidStack = message.fluidStack
        }
        return null
    }
}
