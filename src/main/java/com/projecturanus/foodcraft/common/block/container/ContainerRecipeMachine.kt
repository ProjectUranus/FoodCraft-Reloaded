package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.common.block.entity.TileEntityFluidRecipeMachine
import com.projecturanus.foodcraft.common.block.entity.TileEntityRecipeMachine
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack

abstract class ContainerRecipeMachine<T>(playerInventory: InventoryPlayer, override val tileEntity: T) : ContainerMachine(playerInventory, tileEntity) where T : TileEntityRecipeMachine<*> {
    var progress = 0

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
        listeners.forEach {
            it.sendWindowProperty(this, 0, tileEntity.progress)
        }
    }

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        val slot = inventorySlots[index]
        var stack = ItemStack.EMPTY
        // Checks for an empty slot
        if (slot == null || !slot.hasStack) return stack
        stack = slot.stack.copy()
        when (index) {
            in (0 until tileEntity.slots) -> {
                if (!mergeItemStack(slot.stack, tileEntity.slots, tileEntity.slots + PLAYER_INV_SIZE, false))
                    return ItemStack.EMPTY
                slot.onSlotChange(slot.stack, stack)
            }
            in (tileEntity.slots) until (tileEntity.slots + PLAYER_INV_SIZE) -> {
                if (tileEntity is TileEntityFluidRecipeMachine<*>) {
                    val fluidRecipeMachine = tileEntity as TileEntityFluidRecipeMachine<*>
                    if (mergeItemStack(slot.stack, fluidRecipeMachine.fluidHandlerSlot, fluidRecipeMachine.fluidHandlerSlot + 1, false)) {
                        slot.onSlotChange(slot.stack, stack)
                        return ItemStack.EMPTY
                    }
                }

                if (!mergeItemStack(slot.stack, tileEntity.inputSlots.first, tileEntity.inputSlots.last + 1, false))
                    return ItemStack.EMPTY
                slot.onSlotChange(slot.stack, stack)
            }
        }
        return ItemStack.EMPTY
    }

    override fun updateProgressBar(id: Int, data: Int) {
        when (id) {
            0 -> progress = data
        }
    }
}
