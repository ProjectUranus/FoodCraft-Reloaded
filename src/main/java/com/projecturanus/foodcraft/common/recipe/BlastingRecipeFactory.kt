package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.JsonUtils
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.IRecipeFactory
import net.minecraftforge.common.crafting.JsonContext
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.common.registry.GameRegistry

class BlastingRecipeFactory : IRecipeFactory {
    override fun parse(context: JsonContext, json: JsonObject): IRecipe? {
        val ingredient = CraftingHelper.getIngredient(json["ingredient"], context)

        val result = ItemStack(ForgeRegistries.ITEMS.getValue(ResourceLocation(JsonUtils.getString(json, "result"))))
        val exp = JsonUtils.getFloat(json, "experience", 0.1f)
        ingredient.matchingStacks.forEach {
            GameRegistry.addSmelting(it, result, exp)
        }
        return NoRecipe
    }
}
