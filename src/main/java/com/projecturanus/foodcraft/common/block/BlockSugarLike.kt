package com.projecturanus.foodcraft.common.block

import net.minecraft.block.BlockReed
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import java.util.*

class BlockSugarLike : BlockReed() {
    override fun getItem(worldIn: World, pos: BlockPos, state: IBlockState): ItemStack {
        return ItemStack(this)
    }

    override fun getItemDropped(state: IBlockState?, rand: Random?, fortune: Int): Item? {
        return Item.getItemFromBlock(this)
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random) {
        if (worldIn.getBlockState(pos.down()).block === this || checkForDrop(worldIn, pos, state)) {
            if (worldIn.isAirBlock(pos.up())) {
                var i = 1
                while (worldIn.getBlockState(pos.down(i)).block === this) {
                    ++i
                }
                if (i < 3) {
                    val j = (state.getValue(AGE) as Int).toInt()
                    if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                        if (j == 15) {
                            worldIn.setBlockState(pos.up(), this.defaultState)
                            worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(0)), 4)
                        } else {
                            worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(j + 1)), 4)
                        }
                        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos))
                    }
                }
            }
        }
    }
}
