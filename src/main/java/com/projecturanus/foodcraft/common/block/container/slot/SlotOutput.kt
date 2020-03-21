package com.projecturanus.foodcraft.common.block.container.slot

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class SlotOutput(val player: EntityPlayer, itemHandler: IItemHandler, index: Int, xPosition: Int, yPosition: Int) : SlotItemHandler(itemHandler, index, xPosition, yPosition) {
    var removeCount = 0

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    override fun isItemValid(stack: ItemStack): Boolean {
        return false
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    override fun decrStackSize(amount: Int): ItemStack {
        if (this.hasStack) {
            this.removeCount += Math.min(amount, this.stack.count)
        }
        return super.decrStackSize(amount)
    }

    override fun onTake(thePlayer: EntityPlayer, stack: ItemStack): ItemStack {
        this.onCrafting(stack)
        super.onTake(thePlayer, stack)
        return stack
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    override fun onCrafting(stack: ItemStack, amount: Int) {
        this.removeCount += amount
        this.onCrafting(stack)
    }

    override fun onCrafting(stack: ItemStack)
    {
        stack.onCrafting(this.player.world, this.player, this.removeCount);
        removeCount = 0
    }

}
