package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.recipe.FcRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.registries.IForgeRegistry
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

abstract class TileEntityHeatRecipeMachine<T>(recipeRegistry: IForgeRegistry<T>, inputSlots: IntRange, outputSlots: IntRange, slots: Int) : ITemperature, TileEntityRecipeMachine<T>(recipeRegistry, inputSlots, outputSlots, slots) where T: FcRecipe<T> {
    open val heatHandler = this.createFuelHandler()

    override fun getTemperature(): Double = heatHandler.temperature
    override fun getMaximumTemperature(): Double = heatHandler.maximumTemperature
    override fun getMinimumTemperature(): Double = heatHandler.minimumTemperature
    override fun getDefaultTemperature(): Double = heatHandler.defaultTemperature

    abstract fun createFuelHandler(): FuelHeatHandler

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability == InjectedCapabilities.TEMPERATURE)
            heatHandler as T?
        else
            super.getCapability(capability, facing)
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == InjectedCapabilities.TEMPERATURE || super.hasCapability(capability, facing)
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        super.readFromNBT(nbt)
        heatHandler.deserializeNBT(nbt.getCompoundTag("heat"))
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        val compound = super.writeToNBT(compound)
        compound.setTag("heat", heatHandler.serializeNBT())
        return compound
    }
}
