package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.FoodCraftReloaded
import com.projecturanus.foodcraft.common.BREW_BARREL
import com.projecturanus.foodcraft.common.block.entity.TileEntityBrewBarrel
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockBrewBarrel: BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityBrewBarrel()

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val stack = playerIn.getHeldItem(hand)
        if (!playerIn.isSneaking) {
            playerIn.openGui(FoodCraftReloaded, BREW_BARREL, worldIn, pos.x, pos.y, pos.z)
        }
        return true
    }
}
