package com.projecturanus.foodcraft.common.block

import net.minecraft.block.BlockLeaves
import net.minecraft.block.BlockPlanks
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.util.*

class BlockFruitLeaves(val fruit: Item) : BlockLeaves() {
    lateinit var baseTranslationKey: String
    lateinit var realTranslationKey: String

    init {
        defaultState = blockState.baseState.withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true)
    }

    override fun onSheared(item: ItemStack, world: IBlockAccess, pos: BlockPos?, fortune: Int): List<ItemStack> {
        return listOf(ItemStack(fruit, quantityDroppedWithBonus(fortune, if (world is World) world.rand else Random())))
    }

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        return fruit
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return when (meta) {
            0 -> defaultState.withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true)
            1 -> defaultState.withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, false)
            2 -> defaultState.withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true)
            else -> defaultState.withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, false)
        }
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    override fun getMetaFromState(state: IBlockState): Int {
        val decay = state.getValue(CHECK_DECAY)
        val decayable = state.getValue(DECAYABLE)
        return if (decay && !decayable) 0 else if (!decay && decayable) 1 else if (decay && decayable) 2 else 3
    }

    override fun getDrops(drops: NonNullList<ItemStack>, world: IBlockAccess, pos: BlockPos?, state: IBlockState?, fortune: Int) {
        val rand = if (world is World) world.rand else Random()
        drops.add(ItemStack(fruit, quantityDropped(rand)))
    }

    override fun quantityDropped(random: Random): Int {
        return if (random.nextInt(20) == 0) 3 else 1
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, CHECK_DECAY, DECAYABLE)
    }

    override fun getWoodType(meta: Int): BlockPlanks.EnumType = BlockPlanks.EnumType.JUNGLE

    @SideOnly(Side.CLIENT)
    override fun getLocalizedName(): String = I18n.format(baseTranslationKey, I18n.format(realTranslationKey))
}
