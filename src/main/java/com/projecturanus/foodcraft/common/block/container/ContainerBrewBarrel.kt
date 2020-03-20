package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.entity.TileEntityBrewBarrel
import com.projecturanus.foodcraft.common.network.CHANNAL
import com.projecturanus.foodcraft.common.network.S2CFluidUpdate
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler


class ContainerBrewBarrel(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerRecipeMachine<TileEntityBrewBarrel>(playerInventory, tileEntity as TileEntityBrewBarrel), FluidContainer {
    override var fluidStack: FluidStack? = null

    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!
        addSlotToContainer(SlotItemHandler(itemHandler, 0, 54, 32))
        addSlotToContainer(SlotItemHandler(itemHandler, 1, 78, 32))
        addSlotToContainer(SlotItemHandler(itemHandler, 2, 101, 32))

        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 3, 138, 32))
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 4, 138, 59))

        addSlotToContainer(SlotItemHandler(itemHandler, 5, 37, 59))

        for (x in 0 until 3) {
            for (y in 0 until 9) {
                addSlotToContainer(Slot(playerInventory, y + x * 9 + 9, 8 + y * 18, 84 + x * 18))
            }
        }

        for (y in 0 until 9) {
            addSlotToContainer(Slot(playerInventory, y, 8 + y * 18, 142))
        }
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        listeners.forEach {
            if (it is EntityPlayerMP) {
                CHANNAL.sendTo(S2CFluidUpdate(tileEntity.fluidTank.fluid), it)
            }
        }
    }

    override fun getName(): String = "tile.$MODID.brew_barrel.name"
}
