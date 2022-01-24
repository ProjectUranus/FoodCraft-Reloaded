package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.entity.TileEntityStove
import com.projecturanus.foodcraft.common.heat.PropertyTemperature
import com.projecturanus.foodcraft.common.network.CHANNAL
import com.projecturanus.foodcraft.common.network.S2CContainerHeatUpdate
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler

class ContainerStove(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerMachine(playerInventory, tileEntity),
    PropertyTemperature {
    override var minHeat = 0.0
    override var heat = 0.0
    override var maxHeat = 0.0

    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
        addSlotToContainer(SlotItemHandler(itemHandler, 0, 80, 54))
        for (x in 0 until 3) {
            for (y in 0 until 9) {
                addSlotToContainer(Slot(playerInventory, y + x * 9 + 9, 8 + y * 18, 84 + x * 18))
            }
        }

        for (y in 0 until 9) {
            addSlotToContainer(Slot(playerInventory, y, 8 + y * 18, 142))
        }
    }

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        val slot = inventorySlots[index]
        var stack = ItemStack.EMPTY
        // Checks for an empty slot
        if (slot == null || !slot.hasStack) return stack
        stack = slot.stack.copy()
        when (index) {
            0 -> { // Fuel slot
                if (!mergeItemStack(slot.stack, 1, 37, true))
                    return ItemStack.EMPTY
                slot.onSlotChange(slot.stack, stack)
            }
            in 1..27 -> { // Player inventory (exclude hotbar)
                if (mergeItemStack(slot.stack, 0, 1, false))
                    return ItemStack.EMPTY
                else if (mergeItemStack(slot.stack, 28, 37, false))
                    return ItemStack.EMPTY
            }
            in 28..36 -> { // Hotbar
                if (mergeItemStack(slot.stack, 0, 1, false))
                    return ItemStack.EMPTY
                else if (mergeItemStack(slot.stack, 1, 28, false))
                    return ItemStack.EMPTY
            }
        }
        if (stack.isEmpty) slot.putStack(ItemStack.EMPTY)
        return stack
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        listeners.asSequence().filter { it is EntityPlayerMP }.forEach {
            CHANNAL.sendTo(S2CContainerHeatUpdate((tileEntity as TileEntityStove).heatHandler), it as EntityPlayerMP)
        }
    }

    override fun getName(): String = "tile.$MODID.stove.name"
}
