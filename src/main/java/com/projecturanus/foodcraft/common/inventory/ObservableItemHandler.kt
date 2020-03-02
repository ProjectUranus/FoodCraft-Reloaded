package com.projecturanus.foodcraft.common.inventory

import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.items.ItemStackHandler
import org.cyclops.commoncapabilities.api.capability.inventorystate.IInventoryState

class ObservableItemHandler : ItemStackHandler, IInventoryState {
    val loadListener: MutableList<() -> Unit> = mutableListOf()
    val contentChangedListener: MutableList<(Int) -> Unit> = mutableListOf()
    var hashInternal: Int = 0
    var validation: ((Int, ItemStack) -> Boolean)? = null

    constructor() : super()
    constructor(size: Int) : super(size)
    constructor(stacks: NonNullList<ItemStack>?) : super(stacks)

    override fun onLoad() {
        loadListener.forEach { it() }
    }

    override fun onContentsChanged(slot: Int) {
        contentChangedListener.forEach { it(slot) }
        hashInternal++
    }

    override fun getHash(): Int = hashInternal
    override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
        return validation?.invoke(slot, stack) ?: true
    }
}
