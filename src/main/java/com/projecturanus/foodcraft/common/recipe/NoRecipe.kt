package com.projecturanus.foodcraft.common.recipe

import com.projecturanus.foodcraft.MODID
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object NoRecipe : IRecipe {
    override fun canFit(width: Int, height: Int): Boolean = false

    override fun getRegistryType(): Class<IRecipe> = IRecipe::class.java

    override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

    override fun getRegistryName(): ResourceLocation? = ResourceLocation(MODID, "placeholder")

    override fun getCraftingResult(inv: InventoryCrafting): ItemStack = ItemStack.EMPTY

    override fun setRegistryName(name: ResourceLocation?): IRecipe = this

    override fun matches(inv: InventoryCrafting, worldIn: World): Boolean = false
}
