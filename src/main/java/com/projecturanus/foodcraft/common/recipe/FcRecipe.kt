package com.projecturanus.foodcraft.common.recipe

import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.registries.IForgeRegistryEntry

interface FcRecipe<T> : IForgeRegistryEntry<T> {
    val ingredients: List<Ingredient>

    /**
     * Init the real recipe for the given context and json.
     * Also register this recipe in the real registry
     */
    fun init()

    fun matches(inv: IItemHandler): Boolean

    fun getRecipeOutput(): ItemStack
}
