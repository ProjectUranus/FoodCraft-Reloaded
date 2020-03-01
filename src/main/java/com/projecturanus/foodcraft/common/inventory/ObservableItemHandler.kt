package com.projecturanus.foodcraft.common.inventory

import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.items.ItemStackHandler

class ObservableItemHandler : ItemStackHandler {
    val loadListener: MutableList<() -> Unit> = mutableListOf()
    val contentChangedListener: MutableList<(Int) -> Unit> = mutableListOf()

    constructor() : super()
    constructor(size: Int) : super(size)
    constructor(stacks: NonNullList<ItemStack>?) : super(stacks)

    override fun onLoad() {
        loadListener.forEach { it() }
    }

    override fun onContentsChanged(slot: Int) {
        contentChangedListener.forEach { it(slot) }
    }
}
