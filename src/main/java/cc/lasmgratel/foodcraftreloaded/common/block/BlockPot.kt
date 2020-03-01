package cc.lasmgratel.foodcraftreloaded.common.block

import cc.lasmgratel.foodcraftreloaded.common.block.entity.TileEntityPot
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockPot : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityPot()
}
