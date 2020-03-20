package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.entity.TileEntityMill
import com.projecturanus.foodcraft.common.network.CHANNAL
import com.projecturanus.foodcraft.common.network.S2CContainerHeatUpdate
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Slot
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.SlotItemHandler


class ContainerMill(playerInventory: InventoryPlayer, tileEntity: TileEntity) : ContainerRecipeMachine<TileEntityMill>(playerInventory, tileEntity as TileEntityMill), HeatContainer {
    override var heat = 0.0

    init {
        val itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)!!
        addSlotToContainer(SlotItemHandler(itemHandler, 0, 49, 19))
        addSlotToContainer(SlotOutput(playerInventory.player, itemHandler, 1, 112, 19))
        addSlotToContainer(SlotItemHandler(itemHandler, 2, 80, 54))

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
            }
        }
    }

    override fun getName(): String = "tile.$MODID.mill.name"
}
