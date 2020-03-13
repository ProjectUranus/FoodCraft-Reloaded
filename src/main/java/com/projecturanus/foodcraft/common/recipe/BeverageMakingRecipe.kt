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
import net.minecraftforge.items.IItemHandler

class BeverageMakingRecipe(context: JsonContext, json: JsonObject) : DummyRecipe<BeverageMakingRecipe>(context, json) {
    lateinit var inputIngredient: Ingredient
    lateinit var output: ItemStack

    val register by once { BEVERAGE_MAKING_RECIPES.register(this) }

    override fun init() {
        inputIngredient = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "ingredient"), context)
        output = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context)
        register
    }

    override fun matches(inv: IItemHandler): Boolean = inputIngredient.apply(inv[0])

    override fun getRecipeOutput(): ItemStack = output

    override fun getRegistryType(): Class<BeverageMakingRecipe> = BeverageMakingRecipe::class.java

}
