package com.projecturanus.foodcraft.common.block.entity

class TileEntityChoppingBoard : TileEntityMachine(5) {
    override fun onLoad() {
        super.onLoad()
        inventory.contentChangedListener += {

        }
    }

    override fun update() {

    }
}
