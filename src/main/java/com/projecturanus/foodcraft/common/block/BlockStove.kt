package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.FoodCraftReloaded
import com.projecturanus.foodcraft.common.STOVE
import com.projecturanus.foodcraft.common.block.entity.TileEntityStove
import net.minecraft.block.BlockFurnace
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityFurnace
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class BlockStove : BlockMachine() {
    override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity = TileEntityStove()

    override fun setDefaultTemperature(worldIn: World, pos: BlockPos, temperature: Double) {
        getTileEntity<TileEntityStove>(worldIn, pos).heatHandler.minHeat = temperature
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

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
     * this method is unrelated to [randomTick] and [.needsRandomTick], and will always be called regardless
     * of whether the block can receive random update ticks
     * @see BlockFurnace
     */
    @SideOnly(Side.CLIENT)
    override fun randomDisplayTick(stateIn: IBlockState, worldIn: World, pos: BlockPos, rand: Random) {
        val enumfacing = stateIn.getValue(BlockFurnace.FACING) as EnumFacing
        val d0 = pos.x.toDouble() + 0.5
        val d1 = pos.y.toDouble() + rand.nextDouble() * 6.0 / 16.0
        val d2 = pos.z.toDouble() + 0.5
        val d3 = 0.52
        val d4 = rand.nextDouble() * 0.6 - 0.3
        if (rand.nextDouble() < 0.1) {
            worldIn.playSound(pos.x.toDouble() + 0.5, pos.y.toDouble(), pos.z.toDouble() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false)
        }
        when (enumfacing) {
            EnumFacing.WEST -> {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52, d1, d2 + d4, 0.0, 0.0, 0.0)
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52, d1, d2 + d4, 0.0, 0.0, 0.0)
            }
            EnumFacing.EAST -> {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52, d1, d2 + d4, 0.0, 0.0, 0.0)
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52, d1, d2 + d4, 0.0, 0.0, 0.0)
            }
            EnumFacing.NORTH -> {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52, 0.0, 0.0, 0.0)
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52, 0.0, 0.0, 0.0)
            }
            EnumFacing.SOUTH -> {
                worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52, 0.0, 0.0, 0.0)
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52, 0.0, 0.0, 0.0)
            }
        }
    }
}
