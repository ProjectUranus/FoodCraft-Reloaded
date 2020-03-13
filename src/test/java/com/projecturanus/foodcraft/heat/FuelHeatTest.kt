package com.projecturanus.foodcraft.heat

import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FuelHeatTest {
    @Test
    fun testFuelHeatHandler() {
        runBlocking {
            val handler = FuelHeatHandler()
            handler.setMaxHeat(1000.0)
            handler.heatPower = 20.0
            handler.radiation = 0.3
            val source = FuelHeatHandler()
            source.addBurnTime(500.0)
            source.heatPower = 15.0
            source.setMaxHeat(100.0)
            source.bind(handler)
            source.radiation = 0.3
            repeat(50) {
                source.update(0.0)
                handler.update(0.0)
                println("Source: ${source.temperature}, Handler: ${handler.temperature}")
            }
        }
    }
}
