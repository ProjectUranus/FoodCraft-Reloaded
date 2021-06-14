package com.projecturanus.foodcraft.common.block.container

import com.projecturanus.foodcraft.MODID
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.IWorldNameable
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

const val PLAYER_INV_SIZE = 36

open class ContainerMachine(val playerInventory: InventoryPlayer, open val tileEntity: TileEntity) : Container(), IWorldNameable {
    override fun canInteractWith(playerIn: EntityPlayer): Boolean = true

    override fun hasCustomName(): Boolean {
        return false
    }

    override fun getName(): String = "container.$MODID.dummy"

    override fun getDisplayName(): ITextComponent =
        if (hasCustomName()) TextComponentString(this.name) else TextComponentTranslation(this.name)

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
    }

    @SideOnly(Side.CLIENT)
    override fun updateProgressBar(id: Int, data: Int) {
    }

    override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
        return ItemStack.EMPTY
    }
}
