package com.projecturanus.foodcraft.common.recipe

import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry

class BlastingRecipe : IRecipe, IForgeRegistryEntry.Impl<IRecipe>() {
    override fun canFit(width: Int, height: Int): Boolean = false

    override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack = ItemStack.EMPTY

    override fun matches(inv: InventoryCrafting, worldIn: World) = false
}
