package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.recipe.MILL_RECIPES
import com.projecturanus.foodcraft.common.recipe.MillRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.set
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class TileEntityMill : TileEntityRecipeMachine<MillRecipe>(MILL_RECIPES, 0..0, 1..1, 3) {
    val heatHandler = FuelHeatHandler()

    override fun onLoad() {
        super.onLoad()
        heatHandler.radiation = 0.15
        heatHandler.heatPower = 0.6
        heatHandler.temperature = ITemperature.ZERO_CELCIUS
        heatHandler.minHeat = ITemperature.ZERO_CELCIUS
        heatHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + 140)

        heatHandler.depleteListener = {
            if (!inventory[2].isEmpty)
                heatHandler.addFuel(inventory[2])
            if (inventory[2].isEmpty)
                inventory[2] = ItemStack.EMPTY
        }

        inventory.contentChangedListener += {
            val stack = inventory[it]
            when (it) {
                2 -> {
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
        return heatHandler.temperature > ITemperature.ZERO_CELCIUS + 90
    }

    override fun canFinish(): Boolean = progress >= 200

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
