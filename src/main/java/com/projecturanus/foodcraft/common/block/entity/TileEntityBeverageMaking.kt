package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.recipe.BEVERAGE_MAKING_RECIPES
import com.projecturanus.foodcraft.common.recipe.BeverageMakingRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.set
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class TileEntityBeverageMaking : TileEntityFluidRecipeMachine<BeverageMakingRecipe>(BEVERAGE_MAKING_RECIPES, 8000, 2, 0..0, 1..1, 5) {
    val heatHandler = FuelHeatHandler()
    val coolHandler = FuelHeatHandler()

    override fun onLoad() {
        super.onLoad()
        heatHandler.radiation = 0.15
        heatHandler.heatPower = 0.4
        heatHandler.temperature = ITemperature.ZERO_CELCIUS
        heatHandler.minHeat = ITemperature.ZERO_CELCIUS
        heatHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + 100)

        heatHandler.depleteListener = {
            if (!inventory[3].isEmpty)
                heatHandler.addFuel(inventory[3])
            if (inventory[3].isEmpty)
                inventory[3] = ItemStack.EMPTY
        }

        coolHandler.radiation = 0.05
        coolHandler.heatPower = 0.13
        coolHandler.cool = true
        coolHandler.minHeat = ITemperature.ZERO_CELCIUS - 80
        coolHandler.temperature = ITemperature.ZERO_CELCIUS
        coolHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + 30)

        coolHandler.depleteListener = {
            if (!inventory[4].isEmpty)
                coolHandler.addCoolant(inventory[4])
            if (inventory[4].isEmpty)
                inventory[4] = ItemStack.EMPTY
        }

        inventory.contentChangedListener += {
            val stack = inventory[it]
                when (it) {
                    3 -> {
                        if (!stack.isEmpty && heatHandler.burnTime <= 0.0)
                            heatHandler.addFuel(stack)
                    }
                    4 -> {
                        if (!stack.isEmpty && coolHandler.burnTime <= 0.0)
                            coolHandler.addCoolant(stack)
                    }
            }
        }
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        super.readFromNBT(nbt)
        heatHandler.deserializeNBT(nbt.getCompoundTag("heat"))
        coolHandler.deserializeNBT(nbt.getCompoundTag("cool"))
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        val compound = super.writeToNBT(compound)
        compound.setTag("heat", heatHandler.serializeNBT())
        compound.setTag("cool", coolHandler.serializeNBT())
        return compound
    }

    override fun reset() {
    }

    override fun beforeProgress() {
        heatHandler.update(0.0)
        coolHandler.update(0.0)
    }

    override fun canProgress(): Boolean {
        if (recipe?.cool == true) {
            if (coolHandler.temperature < ITemperature.ZERO_CELCIUS - 10)
                return true
        } else if (recipe?.cool == false) {
            if (heatHandler.temperature > ITemperature.ZERO_CELCIUS + 60)
                return true
        } else {
            return true
        }
        return false
    }

    override fun canFinish(): Boolean = progress >= 200
}
