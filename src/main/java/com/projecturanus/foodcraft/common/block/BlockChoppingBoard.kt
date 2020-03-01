package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.block.entity.TileEntityChoppingBoard
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockChoppingBoard : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityChoppingBoard()

}
