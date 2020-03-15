package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.entity.TileEntityBeverageMaking
import com.projecturanus.foodcraft.common.network.CHANNAL
import com.projecturanus.foodcraft.common.network.S2CBeverageHeatUpdate
import com.projecturanus.foodcraft.common.network.S2CFluidUpdate
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler


class ContainerBeverageMaking(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerMachine(playerInventory, tileEntity), FluidContainer {
    var progress = 0
    var heatTemperature = 0.0
    var coolTemperature = 0.0
    override var fluidStack: FluidStack? = null

    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!
        addSlotToContainer(SlotItemHandler(itemHandler, 0, 37, 31))
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 1, 85, 31))
        addSlotToContainer(SlotItemHandler(itemHandler, 2, 37, 59))
        addSlotToContainer(SlotItemHandler(itemHandler, 3, 118, 20))
        addSlotToContainer(SlotItemHandler(itemHandler, 4, 118, 52))

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
        val tile = tileEntity as TileEntityBeverageMaking
        listeners.forEach {
            it.sendWindowProperty(this, 0, tile.progress)
            if (it is EntityPlayerMP) {
                CHANNAL.sendTo(S2CBeverageHeatUpdate(tileEntity.heatHandler.temperature, tileEntity.coolHandler.temperature), it)
                CHANNAL.sendTo(S2CFluidUpdate(tileEntity.tank.fluid), it)
            }
        }
    }

    override fun updateProgressBar(id: Int, data: Int) {
        when (id) {
            0 -> progress = data
        }
    }

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        return super.transferStackInSlot(playerIn, index)
    }

    override fun getName(): String = "tile.$MODID.beverage_making.name"
}
