package com.projecturanus.foodcraft.common.block

import net.minecraft.block.*
import net.minecraft.block.BlockSapling.STAGE
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenTrees
import net.minecraftforge.event.terraingen.TerrainGen
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class BlockFruitSapling(val leavesState: IBlockState) : BlockBush(), IGrowable {
    lateinit var baseTranslationKey: String
    lateinit var realTranslationKey: String

    init {
        this.defaultState = blockState.baseState.withProperty(STAGE, 0)
    }

    private fun isTwoByTwoOfType(worldIn: World, pos: BlockPos, p_181624_3_: Int, p_181624_4_: Int, type: BlockPlanks.EnumType): Boolean {
        return isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_), type) && isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_), type) && isTypeAt(worldIn, pos.add(p_181624_3_, 0, p_181624_4_ + 1), type) && isTypeAt(worldIn, pos.add(p_181624_3_ + 1, 0, p_181624_4_ + 1), type)
    }

    /**
     * Check whether the given BlockPos has a Sapling of the given type
     */
    fun isTypeAt(worldIn: World, pos: BlockPos?, type: BlockPlanks.EnumType): Boolean {
        val iblockstate = worldIn.getBlockState(pos)
        return iblockstate.block === this && iblockstate.getValue(BlockSapling.TYPE) == type
    }

    override fun canGrow(worldIn: World?, pos: BlockPos?, state: IBlockState?, isClient: Boolean): Boolean {
        return true
    }

    override fun canUseBonemeal(worldIn: World, rand: Random?, pos: BlockPos?, state: IBlockState?): Boolean {
        return worldIn.rand.nextFloat() < 0.45f
    }

    override fun grow(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState) {
        grow(worldIn, pos, state, rand)
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        if (!worldIn.isRemote) {
            super.updateTick(worldIn, pos, state, rand)
            if (!worldIn.isAreaLoaded(pos, 1)) return  // Forge: prevent loading unloaded chunks when checking neighbor's light
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                this.grow(worldIn, pos, state, rand)
            }
        }
    }

    fun grow(worldIn: World, pos: BlockPos?, state: IBlockState, rand: Random?) {
        if ((state.getValue(STAGE) as Int).toInt() == 0) {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4)
        } else {
            generateTree(worldIn, pos!!, state, rand!!, leavesState)
        }
    }

    init {
        defaultState = blockState.baseState.withProperty(STAGE, 0)
        tickRandomly = true
    }

    override fun getSubBlocks(itemIn: CreativeTabs?, items: NonNullList<ItemStack>) {
        items += ItemStack(this)
    }

    override fun createBlockState(): BlockStateContainer? {
        return BlockStateContainer(this, STAGE)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(STAGE, meta)
    }

    override fun getMetaFromState(state: IBlockState): Int = state.getValue(STAGE)

    @SideOnly(Side.CLIENT)
    override fun getLocalizedName(): String = I18n.format(baseTranslationKey, I18n.format(realTranslationKey))
}

fun generateTree(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random, leavesState: IBlockState, isSapling: Boolean = true) {
    if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) return
    val logState = Blocks.LOG.defaultState.withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE)
    val air = Blocks.AIR.defaultState

    worldIn.setBlockState(pos, air, 4)
    val genTrees = WorldGenTrees(true, 4 + rand.nextInt(7), logState, leavesState, false)
    if (!genTrees.generate(worldIn, rand, pos) && isSapling) {
        worldIn.setBlockState(pos, state, 4)
    }

}
