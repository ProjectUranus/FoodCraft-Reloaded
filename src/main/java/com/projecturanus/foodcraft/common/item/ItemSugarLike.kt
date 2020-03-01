package com.projecturanus.foodcraft.common.item

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemBlock
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable

class ItemSugarLike(val block: Block) : FCRItemFood(), IPlantable {
    override fun getPlantType(world: IBlockAccess?, pos: BlockPos?): EnumPlantType = EnumPlantType.Water

    override fun getPlant(world: IBlockAccess?, pos: BlockPos?): IBlockState = block.defaultState

    /**
     * Called when a Block is right-clicked with this Item
     */
    override fun onItemUse(player: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult? {
        val itemstack = player.getHeldItem(hand)

        return if (!itemstack.isEmpty && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(block, pos, false, facing, player)) {
            var state = block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand)
            if (!worldIn.setBlockState(pos, state, 11)) {
                EnumActionResult.FAIL
            } else {
                state = worldIn.getBlockState(pos)
                if (state.block === block) {
                    ItemBlock.setTileEntityNBT(worldIn, player, pos, itemstack)
                    state.block.onBlockPlacedBy(worldIn, pos, state, player, itemstack)
                    if (player is EntityPlayerMP) {
                        CriteriaTriggers.PLACED_BLOCK.trigger(player, pos, itemstack)
                    }
                }
                val soundType = state.block.getSoundType(state, worldIn, pos, player)
                worldIn.playSound(player, pos, soundType.placeSound, SoundCategory.BLOCKS, (soundType.getVolume() + 1.0f) / 2.0f, soundType.getPitch() * 0.8f)
                itemstack.shrink(1)
                EnumActionResult.SUCCESS
            }
        } else {
            EnumActionResult.FAIL
        }
    }

}
