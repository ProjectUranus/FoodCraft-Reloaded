package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import com.projecturanus.foodcraft.common.util.get
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

class BeverageMakingRecipe(context: JsonContext, json: JsonObject) : DummyRecipe<BeverageMakingRecipe>(context, json) {
    lateinit var inputIngredient: Ingredient
    lateinit var output: ItemStack

    /**
     * If sets to false, this recipe will require heating
     * If sets to true, this recipe will require cooldown
     * If sets to null, this recipe will have no requirement of temperature
     */
    var cool: Boolean? = null
    var fluidInput: FluidStack? = null

    val register by once { BEVERAGE_MAKING_RECIPES.register(this) }

    override fun init() {
        inputIngredient = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "ingredient"), context)
        output = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context)
        if (JsonUtils.hasField(json, "fluidInput")) {
            val fluidInputObj = JsonUtils.getJsonObject(json, "fluidInput")
            fluidInput = FluidRegistry.getFluidStack(JsonUtils.getString(fluidInputObj, "fluid"), JsonUtils.getInt(fluidInputObj, "amount"))!!
        }
        cool = json["cool"]?.asBoolean
        register
    }

    override fun matches(inv: IItemHandler): Boolean = inputIngredient.apply(inv[0])

    override fun getRecipeOutput(): ItemStack = output

    override fun getRegistryType(): Class<BeverageMakingRecipe> = BeverageMakingRecipe::class.java

}
