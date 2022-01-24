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
            container.tileEntity.heatHandler.minHeat = message.heat.minHeat
            container.tileEntity.heatHandler.temperature = message.heat.heat
            container.tileEntity.heatHandler.setMaxHeat(message.heat.maxHeat)

            container.tileEntity.coolHandler.minHeat = message.cool.minHeat
            container.tileEntity.coolHandler.temperature = message.cool.heat
            container.tileEntity.coolHandler.setMaxHeat(message.cool.maxHeat)
        }
        return null
    }
}
