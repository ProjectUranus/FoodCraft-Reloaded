package cc.lasmgratel.foodcraftreloaded.common.block

import cc.lasmgratel.foodcraftreloaded.common.item.ItemCrop
import net.minecraft.block.BlockCrops
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockCrop : BlockCrops() {
    lateinit var cropItem: ItemCrop

    override fun getSeed() = cropItem

    override fun getCrop() = cropItem

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (state.getValue(AGE) == 7 && playerIn.canPlayerEdit(pos, facing, playerIn.getHeldItem(hand))) {
            worldIn.setBlockState(pos, state.withProperty(AGE, 0))
            dropBlockAsItem(worldIn, pos, state, -1)
            return true
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
    }
}
