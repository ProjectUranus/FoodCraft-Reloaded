package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.observable.ObservableItemHandler
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class TileEntityStove : TileEntity(), ITickable {
    val inventory: ObservableItemHandler = ObservableItemHandler(1)
    val heatHandler: FuelHeatHandler = FuelHeatHandler()
    var currentItemBurnTime: Int = 0

    override fun onLoad() {
        super.onLoad()
        inventory.validation = { _, stack ->
            TileEntityFurnace.isItemFuel(stack)
        }
        heatHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + 1000)
        heatHandler.radiation = 0.2
        heatHandler.heatPower = 0.5
        heatHandler.depleteListener = {
            if (!inventory[0].isEmpty)
                currentItemBurnTime = heatHandler.addFuel(inventory[0])
        }
        inventory.contentChangedListener += {
            if (!inventory[0].isEmpty && heatHandler.burnTime <= 0.0)
                currentItemBurnTime = heatHandler.addFuel(inventory[0])
        }

        // Detect heat consumer on the top of this stove
        // And auto bind them
        val consumer = world.getTileEntity(pos.up())
        if (consumer is TileEntityHeatRecipeMachine<*>) {
            consumer.heatHandler.boundHandlers.clear()
            consumer.heatHandler.bind(this.heatHandler)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when (capability) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, InjectedCapabilities.INVENTORY_STATE -> inventory as T
            InjectedCapabilities.TEMPERATURE -> heatHandler as T
            InjectedCapabilities.WORKER -> heatHandler as T
            else -> null
        }
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
            capability == InjectedCapabilities.INVENTORY_STATE ||
            capability == InjectedCapabilities.TEMPERATURE ||
            capability == InjectedCapabilities.WORKER ||
            super.hasCapability(capability, facing)
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        inventory.deserializeNBT(compound.getCompoundTag("inventory"))
        heatHandler.deserializeNBT(compound.getCompoundTag("heatHandler"))
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag("inventory", inventory.serializeNBT())
        compound.setTag("heatHandler", heatHandler.serializeNBT())
        return super.writeToNBT(compound)
    }

    override fun getUpdateTag(): NBTTagCompound {
        return writeToNBT(NBTTagCompound())
    }

    override fun update() {
        heatHandler.update(0.0)
    }
}
