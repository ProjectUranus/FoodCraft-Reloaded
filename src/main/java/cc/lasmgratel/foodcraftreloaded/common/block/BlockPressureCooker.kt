package cc.lasmgratel.foodcraftreloaded.common.block

import cc.lasmgratel.foodcraftreloaded.common.block.entity.TileEntityPressureCooker
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

class BlockPressureCooker : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityPressureCooker()
}
