package com.projecturanus.foodcraft.common.heat

import com.projecturanus.foodcraft.common.capability.ITemperature
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.math.MathHelper

class FuelHeatHandler : HeatHandler, FuelHandler {
    data class FuelInfo(val level: Int, val heat: Int)

    private var encouragement = 0.0
    private var burnTimeInternal = 0.0
    private var heat = 0.0
    var minHeat: Double
    private var maxHeat: Double
    private var heatPower: Double
    var radiation: Double

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
        if (burnTime > 0.0) {
            burnTime -= (1.0 + bonusRate) * (1.0 + encouragement)
            encouragement = (encouragement - 0.01).coerceAtLeast(0.0)
            burnTime = MathHelper.clamp(burnTime, 0.0, maxBurnTime)
            heat += getHeatPower()
        }
        heat -= radiation
        heat = MathHelper.clamp(heat, minHeat, maximumTemperature)
    }

    override fun getHeatPower(): Double {
        return if (getBurnTime() > 0) maxHeatPower else 0.0
    }

    override fun getMaxHeatPower(): Double {
        return heatPower
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

    fun addFuel(stack: ItemStack): ItemStack {
        var stack = stack
        stack = stack.copy()
        val fuel = TileEntityFurnace.getItemBurnTime(stack)
        if (fuel > 0) {
            if (temperature + 20 < maximumTemperature) {
                burnTime = MathHelper.clamp(burnTime + fuel, 0.0, maxBurnTime)
                stack.shrink(1)
            }
        }
        return stack
    }
}
