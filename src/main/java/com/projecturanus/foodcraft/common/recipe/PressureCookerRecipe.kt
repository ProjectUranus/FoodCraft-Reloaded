package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import com.projecturanus.foodcraft.common.util.getValue
import com.projecturanus.foodcraft.common.util.once
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.JsonUtils
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.JsonContext
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.items.IItemHandler

class PressureCookerRecipe(context: JsonContext, json: JsonObject) : DummyRecipe<PressureCookerRecipe>(context, json), FluidRecipe<PressureCookerRecipe> {
    override lateinit var ingredients: List<Ingredient>
    lateinit var output: ItemStack

    /**
     * If sets to false, this recipe will require heating
     * If sets to true, this recipe will require cooldown
     * If sets to null, this recipe will have no requirement of temperature
     */
    override var fluidInput: FluidStack? = null

    val register by once { PRESSURE_COOKER_RECIPES.register(this) }

    override fun init() {
        ingredients = JsonUtils.getJsonArray(json, "ingredients").map {
            CraftingHelper.getIngredient(it, context)
        }
        output = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context)
        if (JsonUtils.hasField(json, "fluidInput")) {
            val fluidInputObj = JsonUtils.getJsonObject(json, "fluidInput")
            fluidInput = FluidRegistry.getFluidStack(JsonUtils.getString(fluidInputObj, "fluid"), JsonUtils.getInt(fluidInputObj, "amount"))!!
        }
        register
    }

    override fun matches(inv: IItemHandler): Boolean = false

    override fun getRecipeOutput(): ItemStack = output.copy()

    override fun getRegistryType(): Class<PressureCookerRecipe> = PressureCookerRecipe::class.java

}
