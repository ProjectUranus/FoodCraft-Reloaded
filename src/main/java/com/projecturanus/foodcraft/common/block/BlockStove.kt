package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.block.entity.TileEntityStove
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockStove : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityStove()
}
