package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler


class ContainerFryingPan(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerMachine(playerInventory, tileEntity) {
    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!
        addSlotToContainer(SlotItemHandler(itemHandler, 0, 58, 31))
        addSlotToContainer(SlotItemHandler(itemHandler, 1, 95, 60))
        addSlotToContainer(SlotItemHandler(itemHandler, 2, 37, 59))
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 3, 130, 31))

        for (x in 0 until 3) {
            for (y in 0 until 9) {
                addSlotToContainer(Slot(playerInventory, y + x * 9 + 9, 8 + y * 18, 84 + x * 18))
            }
        }

        for (y in 0 until 9) {
            addSlotToContainer(Slot(playerInventory, y, 8 + y * 18, 142))
        }
    }

    override fun getName(): String = "tile.$MODID.frying_pan.name"
}
