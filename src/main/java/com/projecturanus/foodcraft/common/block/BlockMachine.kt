package com.projecturanus.foodcraft.common.block

import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.capability.fromMinecraftTemperature
import com.projecturanus.foodcraft.common.heat.HeatHandler
import com.projecturanus.foodcraft.common.init.FcTabMachine
import com.projecturanus.foodcraft.common.util.iterator
import com.projecturanus.foodcraft.logger
import net.minecraft.block.BlockHorizontal
import net.minecraft.block.ITileEntityProvider
import net.minecraft.block.material.MapColor
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.items.CapabilityItemHandler

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

    override fun breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
        val itemHandler = worldIn.getTileEntity(pos)?.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)

        if (itemHandler != null) {
            itemHandler.iterator().forEach {
                if (!it.isEmpty)
                    InventoryHelper.spawnItemStack(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), it)
            }
            worldIn.updateComparatorOutputLevel(pos, this)
        }

        worldIn.removeTileEntity(pos)
        super.breakBlock(worldIn, pos, state)
    }

    override fun onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.horizontalFacing.opposite).withProperty(WORKING, false), 2)
        val poses = arrayOf(pos.up(), pos.down(), pos.east(), pos.west(), pos.north(), pos.south())
        for (movedPos in poses) {
            val handler = worldIn.getTileEntity(movedPos)?.getCapability(InjectedCapabilities.TEMPERATURE, null)
            val thisHandler = worldIn.getTileEntity(pos)?.getCapability(InjectedCapabilities.TEMPERATURE, null)
            if (thisHandler is HeatHandler) {
                if (handler is HeatHandler) {
                    thisHandler.bind(handler)
                    logger.info("$movedPos is bound to $pos")
                }
            }
        }

        if (!worldIn.isRemote)
            setDefaultTemperature(worldIn, pos, fromMinecraftTemperature(worldIn.getBiome(pos).getTemperature(pos).toDouble()))
    }

    open fun setDefaultTemperature(worldIn: World, pos: BlockPos, temperature: Double) {
    }

    override fun withRotation(state: IBlockState, rot: Rotation): IBlockState? {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)))
    }

    override fun withMirror(state: IBlockState, mirrorIn: Mirror): IBlockState? {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)))
    }

    fun <T : TileEntity> getTileEntity(world: World, pos: BlockPos) = world.getTileEntity(pos) as T

    override fun onNeighborChange(world: IBlockAccess, pos: BlockPos, neighbor: BlockPos) {
        super.onNeighborChange(world, pos, neighbor)
        val neighborHandler = world.getTileEntity(neighbor)?.getCapability(InjectedCapabilities.TEMPERATURE, null)
        val heatHandler = world.getTileEntity(pos)?.getCapability(InjectedCapabilities.TEMPERATURE, null) as HeatHandler?
        if (neighborHandler != null && heatHandler != null) {
            if (neighborHandler is HeatHandler)
                heatHandler.bind(neighborHandler as HeatHandler)
            else
                heatHandler.bind(neighborHandler)
            logger.info("$neighbor is bound to $pos")
        }
    }

    override fun isOpaqueCube(state: IBlockState) = false
    override fun isFullBlock(state: IBlockState) = false
    override fun isFullCube(state: IBlockState) = false

    @SideOnly(Side.CLIENT)
    override fun getRenderLayer(): BlockRenderLayer? {
        return BlockRenderLayer.CUTOUT
    }
}

fun World.setBlockStateKeep(pos: BlockPos, state: IBlockState) {
    val tileEntity = getTileEntity(pos)
    setBlockState(pos, state, 3)
    if (tileEntity != null) {
        tileEntity.validate()
        setTileEntity(pos, tileEntity)
    }
}
