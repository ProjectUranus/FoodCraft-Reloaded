package com.projecturanus.foodcraft.fluid

import com.projecturanus.foodcraft.MODID
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.Fluid

class FluidMilk : Fluid("milk", ResourceLocation(MODID, "milk_still"), ResourceLocation(MODID, "milk_flow")) {
    init {
        color = 0xffffff
        this.setDensity(13600)
        this.setViscosity(750)
        this.setLuminosity(0)
        this.setTemperature(300)
    }
}
