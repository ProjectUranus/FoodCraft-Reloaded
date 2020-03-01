package cc.lasmgratel.foodcraftreloaded.common.block

import cc.lasmgratel.foodcraftreloaded.common.block.entity.TileEntityFryingPan
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockFryingPan : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityFryingPan()
}
