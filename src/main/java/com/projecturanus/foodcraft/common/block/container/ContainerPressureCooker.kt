package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.slot.SlotOutput
import com.projecturanus.foodcraft.common.block.entity.TileEntityPressureCooker
import com.projecturanus.foodcraft.common.network.CHANNAL
import com.projecturanus.foodcraft.common.network.S2CContainerHeatUpdate
import com.projecturanus.foodcraft.common.network.S2CFluidUpdate
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler


class ContainerPressureCooker(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerRecipeMachine<TileEntityPressureCooker>(playerInventory, tileEntity as TileEntityPressureCooker), HeatContainer, FluidContainer {
    override var heat = 0.0
    override var fluidStack: FluidStack? = null

    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!
        addSlotToContainer(SlotItemHandler(itemHandler, 0, 47, 31))
        addSlotToContainer(SlotItemHandler(itemHandler, 1, 71, 31))
        addSlotToContainer(SlotItemHandler(itemHandler, 2, 95, 31))
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 3, 145, 31))

        addSlotToContainer(SlotItemHandler(itemHandler, 4, 37, 59))
        addSlotToContainer(SlotItemHandler(itemHandler, 5, 95, 59))

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
                CHANNAL.sendTo(S2CContainerHeatUpdate(tileEntity.heatHandler.temperature), it)
                CHANNAL.sendTo(S2CFluidUpdate(tileEntity.fluidTank.fluid), it)
            }
        }
    }

    override fun getName(): String = "tile.$MODID.pressure_cooker.name"
}
