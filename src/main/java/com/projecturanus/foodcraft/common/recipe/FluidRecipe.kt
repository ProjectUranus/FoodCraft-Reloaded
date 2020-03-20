package com.projecturanus.foodcraft.common.recipe

import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.registries.IForgeRegistryEntry

interface FluidRecipe<T> : FcRecipe<T> where T: IForgeRegistryEntry<T> {
    val fluidInput: FluidStack?
}
