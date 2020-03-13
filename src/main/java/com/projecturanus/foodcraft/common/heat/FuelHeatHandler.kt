package com.projecturanus.foodcraft.common.heat

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.math.MathHelper
import net.minecraftforge.common.util.INBTSerializable
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature
import org.cyclops.commoncapabilities.api.capability.work.IWorker

class FuelHeatHandler : HeatHandler, FuelHandler, INBTSerializable<NBTTagCompound>, IWorker {
    data class FuelInfo(val level: Int, val heat: Int)

    private var encouragement = 0.0
    private var burnTimeInternal = 0.0
    private var heat = 0.0
    var minHeat: Double
    private var maxHeat: Double
    private var heatPower: Double
    var radiation: Double

    var depleteListener: (() -> Unit)? = null
    val temperatureSources = arrayListOf<ITemperature>()
    val boundHandlers = arrayListOf<HeatHandler>()

    constructor() {
        minHeat = 0.0
        maxHeat = 0.0
        heatPower = 0.0
        radiation = 0.0
    }

    constructor(minHeat: Double, maxHeat: Double, heatPower: Double, radiation: Double) {
        this.minHeat = minHeat
        this.maxHeat = maxHeat
        this.heatPower = heatPower
        this.radiation = radiation
    }

    override fun update(bonusRate: Double) {
        // Heating with source
        for (source in temperatureSources) {
            heat += MathHelper.clamp(source.temperature - temperature, -maxHeatPower, maxHeatPower)
        }

        // Heating with bound
        var totalDelta = 0.0
        for (source in boundHandlers) {
            var delta = if (source.heatPower > heatPower) MathHelper.clamp(source.temperature - temperature, -source.heatPower / 2.0, heatPower / 2.0)
            else MathHelper.clamp(source.temperature - temperature, -heatPower / 2.0, source.heatPower / 2.0)
            delta = MathHelper.clamp(delta, -maxHeatPower / 2.0, maxHeatPower / 2.0)
            if (delta == maxHeatPower || totalDelta == maxHeatPower) {
                // Heat up (heat power fixed)
                heat += maxHeatPower
                source.addHeat(-maxHeatPower)
                heat -= radiation
                heat = MathHelper.clamp(heat, minHeat, maximumTemperature)
                return
            } else {
                val temp = totalDelta
                totalDelta = MathHelper.clamp(totalDelta + delta, -maxHeatPower / 2.0, maxHeatPower / 2.0)
                source.addHeat(-(totalDelta - temp))
            }
        }
        heat += totalDelta

        // Heating with fuel
        if (burnTime > 0.0) {
            // Decrease burn time
            burnTime -= (1.0 + bonusRate) * (1.0 + encouragement)

            // Decrease encouragement
            encouragement = (encouragement - 0.01).coerceAtLeast(0.0)

            // Restrict burn time not greater than max burn time
            burnTime = MathHelper.clamp(burnTime, 0.0, maxBurnTime)

            // Heat up (heat power fixed)
            heat += getHeatPower()
        } else if (heat != maxHeat) { // Depleted and as an only heat source
            depleteListener?.invoke()
        }
        if (heat == minHeat) return
        heat -= radiation
        heat = MathHelper.clamp(heat, minHeat, maximumTemperature)
    }

    override fun getHeatPower(): Double {
        return if (burnTime > 0) maxHeatPower else 0.0
    }

    override fun bind(temperatureSource: ITemperature) {
        temperatureSources += temperatureSource
    }

    override fun bind(heatHandler: HeatHandler) {
        heatHandler.unbind(this)
        boundHandlers += heatHandler
    }

    override fun getMaxHeatPower(): Double {
        return heatPower
    }

    override fun unbind(temperatureSource: ITemperature) {
        temperatureSources -= temperatureSource
    }

    override fun unbind(heatHandler: HeatHandler) {
        boundHandlers -= heatHandler
    }

    fun setHeatPower(heatPower: Double) {
        this.heatPower = heatPower
    }

    fun setMaxHeat(maxHeat: Double) {
        this.maxHeat = maxHeat
    }

    override fun getTemperature(): Double {
        return heat
    }

    override fun setTemperature(heat: Double) {
        this.heat = heat
    }

    override fun getMaximumTemperature(): Double {
        return maxHeat
    }

    override fun getMinimumTemperature(): Double {
        return ITemperature.ZERO_CELCIUS
    }

    override fun getDefaultTemperature(): Double {
        return ITemperature.ZERO_CELCIUS
    }

    override fun addHeat(delta: Double) {
        heat = MathHelper.clamp(heat + delta, 0.0, maximumTemperature)
    }

    fun encourage() {
        encouragement = MathHelper.clamp(encouragement + 0.5, 0.0, 1.0)
    }

    override fun getBurnTime(): Double {
        return burnTimeInternal
    }

    override fun setBurnTime(burnTime: Double) {
        this.burnTimeInternal = burnTime
    }

    val level: Int
        get() {
            if (burnTime == 0.0) {
                return 0
            }
            return if ((burnTime - 1).toInt() / 1000 + encouragement > 0) 2 else 1
        }

    override fun getMaxBurnTime(): Double {
        return 3000.0
    }

    override fun addBurnTime(delta: Double) {
        burnTime = MathHelper.clamp(burnTime + delta, 0.0, maxBurnTime)
    }

    fun addFuel(stack: ItemStack): Int {
        val fuel = TileEntityFurnace.getItemBurnTime(stack)
        if (fuel > 0) {
            if (temperature + 20 < maximumTemperature) {
                burnTime = MathHelper.clamp(burnTime + fuel, 0.0, maxBurnTime)
                stack.shrink(1)
                return fuel
            }
        }
        return 0
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        heat = nbt.getDouble("heat")
        burnTime = nbt.getDouble("burnTime")
    }

    override fun serializeNBT(): NBTTagCompound {
        val compound = NBTTagCompound()
        compound.setDouble("heat", heat)
        compound.setDouble("burnTime", burnTime)
        return compound
    }

    override fun hasWork(): Boolean = heat <= maxHeat
    override fun canWork(): Boolean = burnTime > 0
}
