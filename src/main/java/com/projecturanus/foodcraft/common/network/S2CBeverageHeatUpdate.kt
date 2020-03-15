package com.projecturanus.foodcraft.common.network

import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

class S2CBeverageHeatUpdate(var heatTemperature: Double = 0.0, var coolTemperature: Double = 0.0) : IMessage {
    override fun fromBytes(buf: ByteBuf) {
        heatTemperature = buf.readDouble()
        coolTemperature = buf.readDouble()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeDouble(heatTemperature)
        buf.writeDouble(coolTemperature)
    }

}
