package com.projecturanus.foodcraft.common.network

import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

class S2CContainerHeatUpdate(var temperature: Double = 0.0) : IMessage {
    override fun fromBytes(buf: ByteBuf) {
        temperature = buf.readDouble()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeDouble(temperature)
    }

}
