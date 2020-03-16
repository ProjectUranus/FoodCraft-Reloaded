package com.projecturanus.foodcraft.common.recipe

import net.minecraftforge.fluids.FluidStack

interface FluidRecipe<T> : FcRecipe<T> {
    val fluidInput: FluidStack?
}
