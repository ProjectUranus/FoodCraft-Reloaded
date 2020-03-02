package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.inventory.ObservableItemHandler
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler

abstract class TileEntityMachine(val slots: Int) : TileEntity(), ITickable {
    open val inventory: ObservableItemHandler = ObservableItemHandler(slots)
    override fun update() {

    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when (capability) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, InjectedCapabilities.INVENTORY_STATE -> inventory as T
            else -> null
        }
    }
}
