package com.projecturanus.foodcraft.common.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagDouble
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

/**
 * Provide basic capabilities while CommonCapabilities is not installed
 */
object DefaultCapabilities {
    fun registerDefaultCapabilities() {
        CapabilityManager.INSTANCE.register(ITemperature::class.java, object : Capability.IStorage<ITemperature> {
            override fun readNBT(capability: Capability<ITemperature>?, instance: ITemperature?, side: EnumFacing?, nbt: NBTBase?) {
            }
            override fun writeNBT(capability: Capability<ITemperature>?, instance: ITemperature?, side: EnumFacing?): NBTBase? = NBTTagDouble(0.0)
        }) { DefaultTemperature() }
    }
}
