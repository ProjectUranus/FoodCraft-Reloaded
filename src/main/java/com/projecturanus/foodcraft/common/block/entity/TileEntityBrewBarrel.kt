package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.config.FcConfig
import com.projecturanus.foodcraft.common.recipe.BREW_BARREL_RECIPES
import com.projecturanus.foodcraft.common.recipe.BrewBarrelRecipe

class TileEntityBrewBarrel : TileEntityFluidRecipeMachine<BrewBarrelRecipe>(BREW_BARREL_RECIPES, 8000, 5, 0..2, 3..4, 6) {
    override val minProgress = FcConfig.machineConfig.brewBarrelProgress

    override fun reset() {
    }

    override fun beforeProgress() {
    }

    override fun canProgress(): Boolean = true
}
