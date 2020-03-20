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

class PotRecipe(context: JsonContext, json: JsonObject): DummyRecipe<PotRecipe>(context, json) {
    private lateinit var recipeOutput: ItemStack
    lateinit var staples: List<Ingredient>
    override lateinit var ingredients: List<Ingredient>

    /**
     * Ensure it is registered only once
     */
    val register by once { POT_RECIPES.register(this) }

    var minTime = 0f
    var maxTime = 0f

    override fun init() {
        minTime = JsonUtils.getFloat(json, "minTime", 0f)
        maxTime = JsonUtils.getFloat(json, "maxTime", Float.MAX_VALUE)
        ingredients = JsonUtils.getJsonArray(json, "ingredients").map {
            CraftingHelper.getIngredient(it, context)
        }
        staples = JsonUtils.getJsonArray(json, "staples").map {
            CraftingHelper.getIngredient(it, context)
        }
        recipeOutput = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context)
        register
    }

    override fun matches(inv: IItemHandler): Boolean {
        if (inv.slots != 8) return false

        var ret = true

        ingredients.forEachIndexed { index, ingredient ->
            ret = ret and ingredient.apply(inv[index])
        }

        return ret
    }

    override fun getRecipeOutput(): ItemStack = recipeOutput
    override fun getRegistryType(): Class<PotRecipe> = PotRecipe::class.java

}
