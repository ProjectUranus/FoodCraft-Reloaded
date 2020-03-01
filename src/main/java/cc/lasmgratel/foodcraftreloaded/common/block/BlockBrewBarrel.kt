package cc.lasmgratel.foodcraftreloaded.common.block

import cc.lasmgratel.foodcraftreloaded.common.block.entity.TileEntityBrewBarrel
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockBrewBarrel: BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityBrewBarrel()
}
