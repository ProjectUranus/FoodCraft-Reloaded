package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.container.slot.SlotOutput
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.oredict.OreDictionary


class ContainerChoppingBoard(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerMachine(playerInventory, tileEntity) {
    companion object {
        val KITCHEN_KNIFE_ORE by lazy { OreDictionary.getOreID("kitchenKnife") }
    }

    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!
        addSlotToContainer(SlotItemHandler(itemHandler, 0, 70, 26))
        addSlotToContainer(SlotItemHandler(itemHandler, 1, 97, 26))
        addSlotToContainer(SlotItemHandler(itemHandler, 2, 124, 26))
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 3, 97, 58))

        addSlotToContainer(object : SlotItemHandler(itemHandler, 4, 31, 26) {
            override fun isItemValid(stack: ItemStack): Boolean {
                return KITCHEN_KNIFE_ORE in OreDictionary.getOreIDs(stack)
            }
        })

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
            in 4..30 -> { // Player inventory (exclude hotbar)
                if (mergeItemStack(slot.stack, 0, 1, false))
                    return ItemStack.EMPTY
                else if (mergeItemStack(slot.stack, 28, 37, false))
                    return ItemStack.EMPTY
            }
            in 31..39 -> { // Hotbar
                if (mergeItemStack(slot.stack, 0, 1, false))
                    return ItemStack.EMPTY
                else if (mergeItemStack(slot.stack, 1, 28, false))
                    return ItemStack.EMPTY
            }
        }
        if (stack.isEmpty) slot.putStack(ItemStack.EMPTY)
        return stack
    }

    override fun getName(): String = "tile.$MODID.chopping_board.name"
}
