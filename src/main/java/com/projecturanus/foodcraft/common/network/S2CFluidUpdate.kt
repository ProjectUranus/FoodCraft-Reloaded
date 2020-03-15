package com.projecturanus.foodcraft.common.network

import io.netty.buffer.ByteBuf
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import java.nio.charset.StandardCharsets

class S2CFluidUpdate(var fluidStack: FluidStack? = null) : IMessage {
    override fun fromBytes(buf: ByteBuf) {
        if (buf.readBoolean()) {
            val name = buf.readCharSequence(buf.readInt(), StandardCharsets.UTF_8)
            val amount = buf.readInt()
            fluidStack = FluidRegistry.getFluidStack(name.toString(), amount)
        }
    }

    override fun toBytes(buf: ByteBuf) {
        if (fluidStack != null) {
            buf.writeBoolean(true)
            val name = fluidStack?.fluid?.name
            buf.writeInt(name?.length ?: 0)
            buf.writeCharSequence(name, StandardCharsets.UTF_8)
            buf.writeInt(fluidStack?.amount ?: 0)
        } else {
            buf.writeBoolean(false)
        }
    }
}
