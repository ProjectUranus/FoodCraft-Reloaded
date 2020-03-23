package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.FoodCraftReloaded
import com.projecturanus.foodcraft.common.PAN
import com.projecturanus.foodcraft.common.block.entity.TileEntityPan
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockPan : BlockMachine() {
    val collisionBox = AxisAlignedBB(3.0 / 16, 1.0 / 16, 2.5 / 16, 13.0 / 16, 0.8 / 16, 13.5 / 16)

    override fun getSelectedBoundingBox(blockState: IBlockState, worldIn: World, pos: BlockPos): AxisAlignedBB? {
        return collisionBox
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityPan()

    override fun setDefaultTemperature(worldIn: World, pos: BlockPos, temperature: Double) {
        getTileEntity<TileEntityPan>(worldIn, pos).heatHandler.minHeat = temperature
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val stack = playerIn.getHeldItem(hand)
        if (!playerIn.isSneaking) {
            playerIn.openGui(FoodCraftReloaded, PAN, worldIn, pos.x, pos.y, pos.z)
        }
        return true
    }
}
