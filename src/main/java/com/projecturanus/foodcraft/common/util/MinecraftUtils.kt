package com.projecturanus.foodcraft.common.util

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.IItemHandlerModifiable

fun Iterable<ItemStack>.containIngredients(ingredients: List<Ingredient>) : Boolean {
    return ingredients.filter { it != Ingredient.EMPTY }.all { ingredient ->
        this.any { ingredient.apply(it) }
    }
}

operator fun IItemHandler.get(range: IntRange) = range.map { get(it) }

operator fun IItemHandler.get(slot: Int) = getStackInSlot(slot)
operator fun IItemHandlerModifiable.set(slot: Int, stack: ItemStack) {
    setStackInSlot(slot, stack)
}

// Safe operation for shrinking
fun IItemHandlerModifiable.shrink(slot: Int, amount: Int = 1) {
    if (get(slot).item.hasContainerItem(get(slot)) && get(slot).count == 1) {
        set(slot, get(slot).item.getContainerItem(get(slot)))
    } else {
        get(slot).shrink(amount)
    }
    if (get(slot).isEmpty && get(slot) != ItemStack.EMPTY)
        set(slot, ItemStack.EMPTY)
}

fun IItemHandlerModifiable.shrinkWithInv(slot: Int, player: EntityPlayer, amount: Int = 1) {
    if (get(slot).item.hasContainerItem(get(slot))) {
        player.addItemStackToInventory(get(slot).item.getContainerItem(get(slot)).also { it.count = amount })
    }
    get(slot).shrink(amount)
}

fun IItemHandler.iterator() = object : ListIterator<ItemStack> {
    var index = 0

    override fun hasNext(): Boolean = slots > index

    override fun next(): ItemStack = this@iterator[index++]

    override fun hasPrevious(): Boolean = index > 0

    override fun nextIndex(): Int = index + 1

    override fun previous(): ItemStack = this@iterator[--index]

    override fun previousIndex(): Int = index - 1

}

fun IItemHandlerModifiable.iterator() = object : MutableListIterator<ItemStack> {
    var index = 0

    override fun hasNext(): Boolean = slots > index

    override fun next(): ItemStack = this@iterator[index++]

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
