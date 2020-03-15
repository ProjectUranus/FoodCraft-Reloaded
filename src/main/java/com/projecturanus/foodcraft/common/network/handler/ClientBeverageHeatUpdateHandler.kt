package com.projecturanus.foodcraft.common.network.handler

import com.projecturanus.foodcraft.common.block.container.ContainerBeverageMaking
import com.projecturanus.foodcraft.common.network.S2CBeverageHeatUpdate
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ClientBeverageHeatUpdateHandler : IMessageHandler<S2CBeverageHeatUpdate, IMessage?> {
    @SideOnly(Side.CLIENT)
    override fun onMessage(message: S2CBeverageHeatUpdate, ctx: MessageContext): IMessage? {
        val player = Minecraft.getMinecraft().player
        val container = player.openContainer
        if (container is ContainerBeverageMaking) {
            container.heatTemperature = message.heatTemperature
            container.coolTemperature = message.coolTemperature
        }
        return null
    }
}
