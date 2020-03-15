package com.projecturanus.foodcraft.fluid

import com.projecturanus.foodcraft.MODID
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.Fluid

object FluidMilk : Fluid("milk", ResourceLocation(MODID, "fluid/milk_still"), ResourceLocation(MODID, "fluid/milk_flow")) {
    init {
        color = 0xffffff
        this.setDensity(13600)
        this.setViscosity(750)
        this.setLuminosity(0)
        this.setTemperature(300)
    }
}
