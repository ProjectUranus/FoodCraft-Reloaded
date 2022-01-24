package com.projecturanus.foodcraft.common.network.handler

import com.projecturanus.foodcraft.common.heat.PropertyTemperature
import com.projecturanus.foodcraft.common.network.S2CContainerHeatUpdate
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ClientContainerHeatUpdateHandler : IMessageHandler<S2CContainerHeatUpdate, IMessage?> {
    @SideOnly(Side.CLIENT)
    override fun onMessage(message: S2CContainerHeatUpdate, ctx: MessageContext): IMessage? {
        val player = Minecraft.getMinecraft().player
        val container = player.openContainer
        if (container is PropertyTemperature) {
            container.minHeat = message.minTemperature
            container.heat = message.temperature
            container.maxHeat = message.maxTemperature
        }
        return null
    }
}
