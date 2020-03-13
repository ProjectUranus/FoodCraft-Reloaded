package com.projecturanus.foodcraft.common.capability

import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

fun fromMinecraftTemperature(mcTemp: Double): Double {
    return 13.6484805403 * mcTemp + 7.0879687222 + ITemperature.ZERO_CELCIUS
}

class Temperature(val initialTemperature: Double = ITemperature.ZERO_CELCIUS,
                  val minTemperature: Double = 0.0,
                  val maxTemperature: Double = Double.MAX_VALUE): ITemperature {

    override fun getMaximumTemperature(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMinimumTemperature(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDefaultTemperature(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTemperature(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
