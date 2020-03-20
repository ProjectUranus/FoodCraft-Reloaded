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

class MillRecipe(context: JsonContext, json: JsonObject) : DummyRecipe<MillRecipe>(context, json), FcRecipe<MillRecipe> {
    lateinit var inputIngredient: Ingredient
    lateinit var output: ItemStack

    override val ingredients: List<Ingredient>
        by lazy { listOf(inputIngredient) }

    val register by once { MILL_RECIPES.register(this) }

    override fun init() {
        inputIngredient = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "ingredient"), context)
        output = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context)
        register
    }

    override fun matches(inv: IItemHandler): Boolean = inputIngredient.apply(inv[0])

    override fun getRecipeOutput(): ItemStack = output.copy()

    override fun getRegistryType(): Class<MillRecipe> = MillRecipe::class.java

}
