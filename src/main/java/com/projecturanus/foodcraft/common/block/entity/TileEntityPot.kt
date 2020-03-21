package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.config.FcConfig
import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.init.FCRItems
import com.projecturanus.foodcraft.common.recipe.POT_RECIPES
import com.projecturanus.foodcraft.common.recipe.PotRecipe
import com.projecturanus.foodcraft.common.util.containIngredients
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.shrink
import net.minecraft.item.ItemStack
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class TileEntityPot : TileEntityHeatRecipeMachine<PotRecipe>(POT_RECIPES, 0..11, 12..12, 14) {
    var waitingExtract = false

    init {
        inventory.contentChangedListener += {
            if (it == 12 && inventory[12].isEmpty) { // Player took item out of output slot
                reset()
            }
        }
    }

    override fun update() {
        // Don't run tick on client
        if (world.isRemote) return

        beforeProgress()

        if (recipe != null) {
            // Finish
            if (canFinish()) {
                consumeInput()
                working = false
                val stack = recipe?.getRecipeOutput()?.copy()?: ItemStack.EMPTY
                inventory.insertItem(12, stack, false)
                waitingExtract = true
                markDirty()
            } else if (waitingExtract && progress > recipe!!.maxTime) { // Overcooked
                inventory.shrink(12)
                inventory.insertItem(13, ItemStack(FCRItems.OVERCOOKED_FOOD), false)
                reset()
            } else {
                if (canProgress())
                    progress++
                working = true
            }
        } else if (!waitingExtract && progress > 0) {
            progress = 0
            working = false
        }
    }

    override fun findRecipe(): PotRecipe? {
        val validRecipes = recipeRegistry.valuesCollection.asSequence().filter { recipe ->
            inventory[0..3].containIngredients(recipe.staples) && inventory[4..11].containIngredients(recipe.ingredients)
        }
        // Any slot available to insert
        if (!validRecipes.any {
                val stack = it.getRecipeOutput().copy()
                outputSlots.any { slot ->
                    inventory.insertItem(slot, stack, true).isEmpty
                }
            })
            return null
        return validRecipes.firstOrNull()
    }

    override fun reset() {
        waitingExtract = false
        progress = 0
        markDirty()
        recipe = findRecipe()
    }

    override fun beforeProgress() {
        heatHandler.update(0.0)
    }

    override fun canProgress(): Boolean = heatHandler.temperature > ITemperature.ZERO_CELCIUS + FcConfig.machineConfig.potHeat

    override fun canFinish(): Boolean = progress > recipe?.minTime ?: Int.MAX_VALUE && progress < recipe?.maxTime ?: -1 && !waitingExtract

    override fun createFuelHandler(): FuelHeatHandler {
        val heatHandler = FuelHeatHandler()
        heatHandler.radiation = FcConfig.machineConfig.potRadiation
        heatHandler.heatPower = FcConfig.machineConfig.potPower
        heatHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + FcConfig.machineConfig.potHeat + 110)
        return heatHandler
    }
}
