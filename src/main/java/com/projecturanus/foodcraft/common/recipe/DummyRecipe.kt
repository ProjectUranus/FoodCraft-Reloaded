package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import com.projecturanus.foodcraft.MODID
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.crafting.JsonContext
import net.minecraftforge.registries.IForgeRegistryEntry

abstract class DummyRecipe<T>(val context: JsonContext, val json: JsonObject): FcRecipe<T> where T: IForgeRegistryEntry<T> {
    private lateinit var registryName: ResourceLocation

    val wrapper get() = RecipeWrapper(this)

    override fun getRegistryName(): ResourceLocation = registryName

    override fun setRegistryName(name: ResourceLocation): T {
        this.registryName = name
        init()
        return this as T
    }

    class RecipeWrapper<T>(val recipe: DummyRecipe<T>?) : IRecipe where T: IForgeRegistryEntry<T> {
        companion object {
            val LOCATION = ResourceLocation(MODID, "ignore_this_dummy_recipe")
        }

        override fun canFit(width: Int, height: Int): Boolean = false

        override fun getRegistryType(): Class<IRecipe> = IRecipe::class.java

        override fun getRecipeOutput(): ItemStack = ItemStack.EMPTY

        override fun getCraftingResult(inv: InventoryCrafting): ItemStack = ItemStack.EMPTY

        override fun getRegistryName(): ResourceLocation = LOCATION

        override fun setRegistryName(name: ResourceLocation): IRecipe {
            recipe?.setRegistryName(name)
            recipe?.init()
            return this@RecipeWrapper
        }

        override fun matches(inv: InventoryCrafting, worldIn: World): Boolean = false
    }

}
