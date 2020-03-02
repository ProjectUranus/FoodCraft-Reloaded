package com.projecturanus.foodcraft.common.util

import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable

operator fun IItemHandler.get(slot: Int) = getStackInSlot(slot)

fun IItemHandler.iterator() = object : ListIterator<ItemStack> {
    var index = 0

    override fun hasNext(): Boolean = slots > index

    override fun next(): ItemStack = this@iterator[++index]

    override fun hasPrevious(): Boolean = index > 0

    override fun nextIndex(): Int = index + 1

    override fun previous(): ItemStack = this@iterator[--index]

    override fun previousIndex(): Int = index - 1

}

fun IItemHandlerModifiable.iterator() = object : MutableListIterator<ItemStack> {
    var index = 0

    override fun hasNext(): Boolean = slots > index

    override fun next(): ItemStack = this@iterator[++index]

    override fun hasPrevious(): Boolean = index > 0

    override fun nextIndex(): Int = index + 1

    override fun previous(): ItemStack = this@iterator[--index]

    override fun previousIndex(): Int = index - 1

    override fun add(element: ItemStack) {
        throw IllegalAccessException("Cannot append stack to ItemHandler")
    }

    override fun remove() {
        throw IllegalAccessException("Cannot remove stack from ItemHandler")
    }

    override fun set(element: ItemStack) {
        setStackInSlot(index, element)
    }
}
