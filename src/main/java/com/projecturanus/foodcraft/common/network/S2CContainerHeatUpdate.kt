package com.projecturanus.foodcraft.common.network

import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class S2CContainerHeatUpdate : IMessage {
    var minTemperature: Double = 0.0
    var maxTemperature: Double = 0.0
    var temperature: Double = 0.0

    constructor()

    constructor(temperature: ITemperature) {
        minTemperature = temperature.minimumTemperature
        maxTemperature = temperature.maximumTemperature
        this.temperature = temperature.temperature
    }

    override fun fromBytes(buf: ByteBuf) {
        minTemperature = buf.readDouble()
        temperature = buf.readDouble()
        maxTemperature = buf.readDouble()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeDouble(minTemperature)
        buf.writeDouble(temperature)
        buf.writeDouble(maxTemperature)
    }

}
