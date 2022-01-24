package com.projecturanus.foodcraft.common.heat

import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

data class TemperatureImpl(override var minHeat: Double = 0.0, override var heat: Double = 0.0, override var maxHeat: Double = 0.0): PropertyTemperature {
    constructor(temperature: ITemperature) : this() {
        minHeat = temperature.minimumTemperature
        heat = temperature.temperature
        maxHeat = temperature.maximumTemperature
    }
}
