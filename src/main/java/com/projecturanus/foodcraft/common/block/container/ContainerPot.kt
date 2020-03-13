package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler


class ContainerPot(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerMachine(playerInventory, tileEntity) {
    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!
        for (i in 0..3) {
            addSlotToContainer(SlotItemHandler(itemHandler, i, 17 + i * 18, 19))
        }
        for (i in 0..7) {
            addSlotToContainer(SlotItemHandler(itemHandler, 4 + i, 17 + i * 18, 45))
        }
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 12, 125, 19))
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 13, 143, 19))

        for (x in 0 until 3) {
            for (y in 0 until 9) {
                addSlotToContainer(Slot(playerInventory, y + x * 9 + 9, 8 + y * 18, 84 + x * 18))
            }
        }

        for (y in 0 until 9) {
            addSlotToContainer(Slot(playerInventory, y, 8 + y * 18, 142))
        }
    }

    override fun getName(): String = "tile.$MODID.pot.name"
}
