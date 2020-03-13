package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.crafting.JsonContext
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.registries.IForgeRegistryEntry

abstract class DummyRecipe<T>(val context: JsonContext, val json: JsonObject): IForgeRegistryEntry<T> {
    private lateinit var registryName: ResourceLocation

    val wrapper get() = RecipeWrapper(this)

    /**
     * Init the real recipe for the given context and json.
     * Also register this recipe in the real registry
     */
    abstract fun init()

    abstract fun matches(inv: IItemHandler): Boolean

    abstract fun getRecipeOutput(): ItemStack

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
