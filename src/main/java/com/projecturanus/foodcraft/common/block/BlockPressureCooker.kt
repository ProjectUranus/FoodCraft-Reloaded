package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.block.entity.TileEntityPressureCooker
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockPressureCooker : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityPressureCooker()
}
