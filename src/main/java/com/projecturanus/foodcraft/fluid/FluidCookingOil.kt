package com.projecturanus.foodcraft.fluid

import com.projecturanus.foodcraft.MODID
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.Fluid

object FluidCookingOil : Fluid("cooking_oil", ResourceLocation(MODID, "fluid/cooking_oil_still"), ResourceLocation(MODID, "fluid/cooking_oil_flow")) {
    init {
        color = 0x7d6f47
        this.setDensity(13600)
        this.setViscosity(750)
        this.setLuminosity(0)
        this.setTemperature(300)
    }
}
