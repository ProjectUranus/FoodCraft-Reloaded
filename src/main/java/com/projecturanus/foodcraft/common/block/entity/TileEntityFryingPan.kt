package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.config.FcConfig
import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.recipe.FRYING_PAN_RECIPES
import com.projecturanus.foodcraft.common.recipe.FryingPanRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.set
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class TileEntityFryingPan : TileEntityFluidRecipeMachine<FryingPanRecipe>(FRYING_PAN_RECIPES, 4000, 2, 0..0, 1..1, 4) {
    val heatHandler = FuelHeatHandler()

    override fun onLoad() {
        super.onLoad()
        heatHandler.radiation = FcConfig.machineConfig.fryingPanRadiation
        heatHandler.heatPower = FcConfig.machineConfig.fryingPanPower
        heatHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + FcConfig.machineConfig.fryingPanHeat + 20)

        heatHandler.depleteListener = {
            if (!inventory[3].isEmpty)
                heatHandler.addFuel(inventory[3])
            if (inventory[3].isEmpty)
                inventory[3] = ItemStack.EMPTY
        }

        inventory.contentChangedListener += {
            val stack = inventory[it]
            when (it) {
                3 -> {
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
        return heatHandler.temperature >= ITemperature.ZERO_CELCIUS + FcConfig.machineConfig.fryingPanHeat
    }

    override fun canFinish(): Boolean = progress >= FcConfig.machineConfig.fryingPanProgress

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
