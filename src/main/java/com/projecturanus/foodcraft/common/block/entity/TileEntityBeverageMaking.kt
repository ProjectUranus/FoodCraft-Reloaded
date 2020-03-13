package com.projecturanus.foodcraft.common.block.entity

import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import org.cyclops.commoncapabilities.api.ingredient.storage.DefaultIngredientComponentStorageHandler

class TileEntityBeverageMaking : TileEntityMachine(5) {
    val tank = FluidTank(8000)
    val ingredientHandler = DefaultIngredientComponentStorageHandler()

    override fun onLoad() {
        super.onLoad()
        tank.setTileEntity(this)
    }

    override fun update() {
        super.update()

    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when(capability) {
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY -> tank as T?
            else -> super.getCapability(capability, facing)
        }
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
    }
}
