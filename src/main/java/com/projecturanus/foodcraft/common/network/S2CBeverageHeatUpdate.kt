package com.projecturanus.foodcraft.common.network

import com.projecturanus.foodcraft.common.heat.TemperatureImpl
import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class S2CBeverageHeatUpdate : IMessage {
    val cool: TemperatureImpl
    val heat: TemperatureImpl

    constructor() {
        this.cool = TemperatureImpl()
        this.heat = TemperatureImpl()
    }

    constructor(cool: ITemperature, heat: ITemperature) {
        this.cool = TemperatureImpl(cool)
        this.heat = TemperatureImpl(heat)
    }

    override fun fromBytes(buf: ByteBuf) {
        cool.minHeat = buf.readDouble()
        cool.heat = buf.readDouble()
        cool.maxHeat = buf.readDouble()
        heat.minHeat = buf.readDouble()
        heat.heat = buf.readDouble()
        heat.maxHeat = buf.readDouble()
    }

    override fun toBytes(buf: ByteBuf) {
        buf.writeDouble(cool.minHeat)
        buf.writeDouble(cool.heat)
        buf.writeDouble(cool.maxHeat)
        buf.writeDouble(heat.minHeat)
        buf.writeDouble(heat.heat)
        buf.writeDouble(heat.maxHeat)
    }

}
