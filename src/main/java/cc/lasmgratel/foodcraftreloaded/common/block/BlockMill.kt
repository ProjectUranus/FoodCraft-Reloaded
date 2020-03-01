package cc.lasmgratel.foodcraftreloaded.common.block

import cc.lasmgratel.foodcraftreloaded.common.block.entity.TileEntityMill
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockMill : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityMill()
}
