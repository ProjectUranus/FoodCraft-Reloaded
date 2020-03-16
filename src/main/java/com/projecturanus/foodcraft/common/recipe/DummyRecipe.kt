package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.crafting.JsonContext

abstract class DummyRecipe<T>(val context: JsonContext, val json: JsonObject): FcRecipe<T> {
    private lateinit var registryName: ResourceLocation

    val wrapper get() = RecipeWrapper(this)

    override fun getRegistryName(): ResourceLocation = registryName

    override fun setRegistryName(name: ResourceLocation): T {
        this.registryName = name
        init()
        return this as T
    }

    class RecipeWrapper<T>(val recipe: DummyRecipe<T>?) : IRecipe {
        private lateinit var registryName: ResourceLocation

        override fun canFit(width: Int, height: Int): Boolean = false

        override fun getRegistryType(): Class<IRecipe> = IRecipe::class.java

        override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

        override fun getCraftingResult(inv: InventoryCrafting): ItemStack = ItemStack.EMPTY

        override fun getRegistryName(): ResourceLocation = registryName

        override fun setRegistryName(name: ResourceLocation): IRecipe {
            this.registryName = name
            recipe?.setRegistryName(name)
            recipe?.init()
            return this@RecipeWrapper
        }

        override fun matches(inv: InventoryCrafting, worldIn: World): Boolean = false
    }

}
