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

class PanRecipe(context: JsonContext, json: JsonObject): DummyRecipe<PanRecipe>(context, json) {
    lateinit var inputIngredient: Ingredient
    lateinit var output: ItemStack

    override val ingredients: List<Ingredient>
        by lazy { listOf(inputIngredient) }

    /**
     * Ensure it is registered only once
     */
    val register by once { PAN_RECIPES.register(this) }

    var minTime = 0f
    var maxTime = 0f

    override fun init() {
        minTime = JsonUtils.getFloat(json, "minTime", 0f)
        maxTime = JsonUtils.getFloat(json, "maxTime", Float.MAX_VALUE)
        inputIngredient = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "ingredient"), context)
        output = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context)

        register
    }

    override fun matches(inv: IItemHandler): Boolean = inputIngredient.apply(inv[0])

    override fun getRecipeOutput(): ItemStack = output.copy()

    override fun getRegistryType(): Class<PanRecipe> = PanRecipe::class.java

}
