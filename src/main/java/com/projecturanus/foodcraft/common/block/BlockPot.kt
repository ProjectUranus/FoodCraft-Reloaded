package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.block.entity.TileEntityPot
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockPot : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityPot()
}
