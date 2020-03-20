package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.recipe.PRESSURE_COOKER_RECIPES
import com.projecturanus.foodcraft.common.recipe.PressureCookerRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.set
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class TileEntityPressureCooker : TileEntityFluidRecipeMachine<PressureCookerRecipe>(PRESSURE_COOKER_RECIPES, 4000, 4, 0..2, 3..3, 6) {
    val heatHandler = FuelHeatHandler()

    override fun onLoad() {
        super.onLoad()
        heatHandler.radiation = 0.15
        heatHandler.heatPower = 0.65
        heatHandler.temperature = ITemperature.ZERO_CELCIUS
        heatHandler.minHeat = ITemperature.ZERO_CELCIUS
        heatHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + 300)

        heatHandler.depleteListener = {
            if (!inventory[5].isEmpty)
                heatHandler.addFuel(inventory[5])
            if (inventory[5].isEmpty)
                inventory[5] = ItemStack.EMPTY
        }

        inventory.contentChangedListener += {
            val stack = inventory[it]
            when (it) {
                5 -> {
                    if (!stack.isEmpty && heatHandler.burnTime <= 0.0)
                        heatHandler.addFuel(stack)
                }
            }
        }
    }

    override fun reset() {
    }

    override fun beforeProgress() {
        heatHandler.update(0.0)
    }

    override fun canProgress(): Boolean {
        return heatHandler.temperature > ITemperature.ZERO_CELCIUS + 100
    }

    override fun canFinish(): Boolean = progress >= 400

    override fun readFromNBT(nbt: NBTTagCompound) {
        super.readFromNBT(nbt)
        heatHandler.deserializeNBT(nbt.getCompoundTag("heat"))
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        val compound = super.writeToNBT(compound)
        compound.setTag("heat", heatHandler.serializeNBT())
        return compound
    }
}
