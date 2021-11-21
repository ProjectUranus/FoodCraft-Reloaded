package com.projecturanus.foodcraft.worldgen

import net.minecraft.block.BlockSapling
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockPos.MutableBlockPos
import net.minecraft.world.World
import net.minecraft.world.gen.feature.WorldGenAbstractTree
import java.util.*
import kotlin.math.abs

class WorldGenFruitTree(val minTreeHeight: Int, val wood: IBlockState, val leaves: IBlockState, val fruitLeaves: IBlockState, val fruitChance: Double) : WorldGenAbstractTree(true) {

    override fun generate(worldIn: World, rand: Random, position: BlockPos): Boolean {
        val i = rand.nextInt(3) + minTreeHeight
        var flag = true
        return if (position.y >= 1 && position.y + i + 1 <= worldIn.height) {
            for (j in position.y..position.y + 1 + i) {
                var k = 1
                if (j == position.y) {
                    k = 0
                }
                if (j >= position.y + 1 + i - 2) {
                    k = 2
                }
                val mutablePos = MutableBlockPos()
                var l = position.x - k
                while (l <= position.x + k && flag) {
                    var i1 = position.z - k
                    while (i1 <= position.z + k && flag) {
                        if (j >= 0 && j < worldIn.height) {
                            if (!isReplaceable(worldIn, mutablePos.setPos(l, j, i1))) {
                                flag = false
                            }
                        } else {
                            flag = false
                        }
                        ++i1
                    }
                    ++l
                }
            }
            if (!flag) {
                false
            } else {
                var state = worldIn.getBlockState(position.down())
                if (state.block.canSustainPlant(
                        state,
                        worldIn,
                        position.down(),
                        EnumFacing.UP,
                        Blocks.SAPLING as BlockSapling
                    ) && position.y < worldIn.height - i - 1
                ) {
                    state.block.onPlantGrow(state, worldIn, position.down(), position)
                    for (i3 in position.y - 3 + i..position.y + i) {
                        val i4 = i3 - (position.y + i)
                        val j1 = 1 - i4 / 2
                        for (k1 in position.x - j1..position.x + j1) {
                            val l1 = k1 - position.x
                            for (i2 in position.z - j1..position.z + j1) {
                                val j2 = i2 - position.z
                                if (abs(l1) != j1 || abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0) {
                                    val blockpos = BlockPos(k1, i3, i2)
                                    state = worldIn.getBlockState(blockpos)
                                    if (state.block.isAir(state, worldIn, blockpos) || state.block.isLeaves(
                                            state,
                                            worldIn,
                                            blockpos
                                        ) || state.material === Material.VINE
                                    ) {
                                        val leaves = if (worldIn.rand.nextDouble() <= fruitChance) {
                                            this.leaves
                                        } else {
                                            this.fruitLeaves
                                        }
                                        setBlockAndNotifyAdequately(worldIn, blockpos, leaves)
                                    }
                                }
                            }
                        }
                    }
                    for (j3 in 0 until i) {
                        val upN = position.up(j3)
                        state = worldIn.getBlockState(upN)
                        if (state.block.isAir(state, worldIn, upN) || state.block.isLeaves(
                                state,
                                worldIn,
                                upN
                            ) || state.material === Material.VINE
                        ) {
                            setBlockAndNotifyAdequately(worldIn, position.up(j3), this.wood)
                        }
                    }
                    true
                } else {
                    false
                }
            }
        } else {
            false
        }
    }
}
