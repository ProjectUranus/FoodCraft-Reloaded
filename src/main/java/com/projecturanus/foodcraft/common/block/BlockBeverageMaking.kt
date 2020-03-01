package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.block.entity.TileEntityBeverageMaking
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockBeverageMaking : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityBeverageMaking()
}
