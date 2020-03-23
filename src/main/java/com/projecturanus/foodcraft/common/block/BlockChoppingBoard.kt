package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.FoodCraftReloaded
import com.projecturanus.foodcraft.common.CHOPPING_BOARD
import com.projecturanus.foodcraft.common.block.entity.TileEntityChoppingBoard
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World

class BlockChoppingBoard : BlockMachine() {
    val collisionBox = AxisAlignedBB(0.0, 0.0, 2.5 / 16, 1.0, 0.8 / 16, 13.5 / 16)

    override fun getSelectedBoundingBox(blockState: IBlockState, worldIn: World, pos: BlockPos): AxisAlignedBB? {
        return collisionBox
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityChoppingBoard()

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val stack = playerIn.getHeldItem(hand)
        if (!playerIn.isSneaking) {
            playerIn.openGui(FoodCraftReloaded, CHOPPING_BOARD, worldIn, pos.x, pos.y, pos.z)
        }
        return true
    }
}
