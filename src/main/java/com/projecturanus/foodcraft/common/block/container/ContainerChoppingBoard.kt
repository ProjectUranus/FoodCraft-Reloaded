package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
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

    override fun getName(): String = "tile.$MODID.chopping_board.name"
}
