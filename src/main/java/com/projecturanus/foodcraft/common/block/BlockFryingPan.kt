package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.block.entity.TileEntityFryingPan
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockFryingPan : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityFryingPan()
}
