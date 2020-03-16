package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.recipe.FcRecipe
import net.minecraftforge.registries.IForgeRegistry

abstract class TileEntityHeatRecipeMachine<T>(recipeRegistry: IForgeRegistry<T>, val fuelSlot: Int, inputSlots: IntRange, outputSlots: IntRange, slots: Int) : TileEntityRecipeMachine<T>(recipeRegistry, inputSlots, outputSlots, slots) where T: FcRecipe<T> {
    abstract fun createFuelHandler(): FuelHeatHandler
}
