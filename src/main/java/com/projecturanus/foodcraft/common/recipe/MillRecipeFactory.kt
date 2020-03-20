package com.projecturanus.foodcraft.common.recipe

import com.google.gson.JsonObject
import net.minecraft.item.crafting.IRecipe
import net.minecraftforge.common.crafting.IRecipeFactory
import net.minecraftforge.common.crafting.JsonContext

class MillRecipeFactory : IRecipeFactory {
    override fun parse(context: JsonContext, json: JsonObject): IRecipe = MillRecipe(context, json).wrapper
}
