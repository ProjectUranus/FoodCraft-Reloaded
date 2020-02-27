package cc.lasmgratel.foodcraftreloaded.common.item

import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable

class ItemCrop : Item(), IPlantable {
    override fun getPlantType(world: IBlockAccess?, pos: BlockPos?): EnumPlantType {
        TODO("Not yet implemented")
    }

    override fun getPlant(world: IBlockAccess?, pos: BlockPos?): IBlockState {
        TODO("Not yet implemented")
    }
}
