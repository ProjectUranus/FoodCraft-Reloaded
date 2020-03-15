package com.projecturanus.foodcraft.common.network

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.network.handler.ClientBeverageHeatUpdateHandler
import com.projecturanus.foodcraft.common.network.handler.ClientContainerHeatUpdateHandler
import com.projecturanus.foodcraft.common.network.handler.ClientFluidUpdateHandler
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.relauncher.Side

val CHANNAL = NetworkRegistry.INSTANCE.newSimpleChannel(MODID)

fun registerMessages() {
    CHANNAL.registerMessage(ClientBeverageHeatUpdateHandler, S2CBeverageHeatUpdate::class.java, 0, Side.CLIENT)
    CHANNAL.registerMessage(ClientContainerHeatUpdateHandler, S2CContainerHeatUpdate::class.java, 1, Side.CLIENT)
    CHANNAL.registerMessage(ClientFluidUpdateHandler, S2CFluidUpdate::class.java, 2, Side.CLIENT)
}
