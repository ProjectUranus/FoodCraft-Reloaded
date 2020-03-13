package com.projecturanus.foodcraft.common.heat

import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class InfiniteHeatSource(val heat: Double, val power: Double) : HeatHandler {
    override fun addHeat(delta: Double) {}

    override fun getMinimumTemperature(): Double = heat

    override fun update(bonusRate: Double) {}

    override fun setTemperature(heat: Double) {}

    override fun getHeatPower(): Double = power
    override fun bind(temperatureSource: ITemperature) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMaxHeatPower(): Double = power
    override fun unbind(temperatureSource: ITemperature) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bind(heatHandler: HeatHandler) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unbind(heatHandler: HeatHandler) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTemperature(): Double = heat

    override fun getMaximumTemperature(): Double = heat

    override fun getDefaultTemperature(): Double = heat
}
