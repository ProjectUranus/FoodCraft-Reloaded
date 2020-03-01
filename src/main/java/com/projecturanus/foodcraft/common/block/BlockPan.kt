package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.block.entity.TileEntityPan
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockPan : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityPan()
}
