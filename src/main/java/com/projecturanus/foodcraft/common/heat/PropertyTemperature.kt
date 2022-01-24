package com.projecturanus.foodcraft.common.heat

import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

interface PropertyTemperature : ITemperature {
    var minHeat: Double
    var heat: Double
    var maxHeat: Double

    override fun getTemperature(): Double = heat

    override fun getMaximumTemperature(): Double = maxHeat

    override fun getMinimumTemperature(): Double = minHeat

    override fun getDefaultTemperature(): Double = minHeat
}
