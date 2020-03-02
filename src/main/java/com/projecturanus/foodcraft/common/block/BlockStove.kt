package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.FoodCraftReloaded
import com.projecturanus.foodcraft.common.STOVE
import com.projecturanus.foodcraft.common.block.entity.TileEntityStove
import com.projecturanus.foodcraft.common.capability.ITemperature
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockStove : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityStove()

    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack)

        getTileEntity<TileEntityStove>(worldIn, pos).heatHandler.minHeat = ITemperature.fromMinecraftTemperature(worldIn.getBiome(pos).getTemperature(pos).toDouble())
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val stack = playerIn.getHeldItem(hand)
        if (!playerIn.isSneaking) {
            playerIn.openGui(FoodCraftReloaded, STOVE, worldIn, pos.x, pos.y, pos.z)
        } else if (TileEntityFurnace.getItemBurnTime(stack) > 0) {
            val stove = getTileEntity<TileEntityStove>(worldIn, pos)
            stove.inventory.insertItem(0, stack, false).isEmpty
        }
        return true
    }
}
