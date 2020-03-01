package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.init.FcTabMachine
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

val WORKING = PropertyBool.create("working")
val MACHINE_MATERIAL = object : Material(MapColor.IRON) {
    init {
        setRequiresTool()
        setImmovableMobility()
    }

    override fun isOpaque() = false
}

abstract class BlockMachine : BlockHorizontal(Material.IRON), ITileEntityProvider {

    init {
        creativeTab = FcTabMachine
        defaultState = blockState.baseState.withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, false)
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, FACING, WORKING)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        var facing = EnumFacing.byIndex((meta and 3) + 4)

        if (facing.axis == EnumFacing.Axis.Y) facing = EnumFacing.NORTH
        return this.defaultState.withProperty(FACING, facing).withProperty(WORKING, meta and 4 == 0)
    }

    override fun getMetaFromState(state: IBlockState): Int {
        var i = state.getValue(FACING).index

        if (!(state.getValue(WORKING))) {
            i = i or 4
        }
        return i
    }

    override fun getStateForPlacement(worldIn: World?, pos: BlockPos?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState? {
        return this.defaultState.withProperty(FACING, placer.horizontalFacing.opposite).withProperty(WORKING, false)
    }

    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.horizontalFacing.opposite).withProperty(WORKING, false), 2)
    }

    override fun withRotation(state: IBlockState, rot: Rotation): IBlockState? {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)))
    }

    override fun withMirror(state: IBlockState, mirrorIn: Mirror): IBlockState? {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)))
    }


    override fun isOpaqueCube(state: IBlockState) = false
    override fun isFullBlock(state: IBlockState) = false
    override fun isFullCube(state: IBlockState) = false

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer? {
        return BlockRenderLayer.CUTOUT
    }
}
