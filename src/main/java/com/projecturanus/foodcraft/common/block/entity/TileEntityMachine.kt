package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.inventory.ObservableItemHandler
import net.minecraft.block.state.IBlockState
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler

abstract class TileEntityMachine(val slots: Int) : TileEntity(), ITickable {
    open val inventory: ObservableItemHandler = ObservableItemHandler(slots)

    override fun onLoad() {
        super.onLoad()
    }

    override fun update() {

    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when (capability) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, InjectedCapabilities.INVENTORY_STATE -> inventory as T
            else -> null
        }
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
            capability == InjectedCapabilities.INVENTORY_STATE ||
            capability == InjectedCapabilities.WORKER ||
            super.hasCapability(capability, facing)
    }

    override fun shouldRefresh(world: World?, pos: BlockPos?, oldState: IBlockState, newSate: IBlockState): Boolean {
        return oldState.block !== newSate.block
    }

    override fun readFromNBT(compound: NBTTagCompound) {
        super.readFromNBT(compound)
        inventory.deserializeNBT(compound.getCompoundTag("inventory"))
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        compound.setTag("inventory", inventory.serializeNBT())
        return super.writeToNBT(compound)
    }
}
