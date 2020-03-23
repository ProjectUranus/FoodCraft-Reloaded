package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.FoodCraftReloaded
import com.projecturanus.foodcraft.common.POT
import com.projecturanus.foodcraft.common.block.entity.TileEntityPot
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

class BlockPot : BlockMachine() {
    val collisionBox = AxisAlignedBB(0.0, 0.0, 2.5, 16.0, 0.8, 13.5)

    override fun getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos): AxisAlignedBB? {
        return collisionBox
    }

    override fun addCollisionBoxToList(state: IBlockState, worldIn: World, pos: BlockPos, entityBox: AxisAlignedBB, collidingBoxes: MutableList<AxisAlignedBB>, entityIn: Entity?, isActualState: Boolean) {
        collidingBoxes += collisionBox
    }

    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityPot()

    override fun setDefaultTemperature(worldIn: World, pos: BlockPos, temperature: Double) {
        getTileEntity<TileEntityPot>(worldIn, pos).heatHandler.minHeat = temperature
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val stack = playerIn.getHeldItem(hand)
        if (!playerIn.isSneaking) {
            playerIn.openGui(FoodCraftReloaded, POT, worldIn, pos.x, pos.y, pos.z)
        }
        return true
    }
}
