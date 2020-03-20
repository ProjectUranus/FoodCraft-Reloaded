package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.getValue
import com.projecturanus.foodcraft.common.util.once
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.JsonUtils
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.JsonContext
import net.minecraftforge.items.IItemHandler

class ChoppingBoardRecipe(context: JsonContext, json: JsonObject): DummyRecipe<ChoppingBoardRecipe>(context, json) {
    private lateinit var recipeOutput: ItemStack
    override val ingredients = ArrayList<Ingredient>(3)

    /**
     * Ensure it is registered only once
     */
    val register by once { CHOPPING_BOARD_RECIPES.register(this) }

    override fun init() {
        val pattern = JsonUtils.getString(json, "pattern")
        val keys = JsonUtils.getJsonObject(json, "key")
        if (pattern.length != 3) throw JsonSyntaxException("Invalid pattern: length must be 3")
        if (pattern.isBlank()) throw JsonSyntaxException("Invalid pattern: empty pattern not allowed")
        pattern.forEach {
            if (ingredients.size >= 3) return@forEach

            ingredients += if (it == ' ')
                Ingredient.EMPTY
            else
                CraftingHelper.getIngredient(JsonUtils.getJsonObject(keys, it.toString()), context)
        }
        recipeOutput = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context)
        register
    }

    override fun matches(inv: IItemHandler): Boolean {
        if (inv.slots != 3) return false

        var ret = true

        ingredients.forEachIndexed { index, ingredient ->
            ret = ret and ingredient.apply(inv[index])
        }

        return ret
    }

    override fun getRecipeOutput(): ItemStack = recipeOutput.copy()
    override fun getRegistryType(): Class<ChoppingBoardRecipe> = ChoppingBoardRecipe::class.java

}
