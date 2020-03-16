package com.projecturanus.foodcraft.common.util.observable

import net.minecraftforge.fluids.FluidTank

class ObservableFluidTank(capacity: Int) : FluidTank(capacity) {
    val contentChangedListener: MutableList<() -> Unit> = mutableListOf()
    override fun onContentsChanged() {
        contentChangedListener.forEach { it.invoke() }
    }
}
