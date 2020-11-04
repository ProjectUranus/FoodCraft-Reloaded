package com.projecturanus.foodcraft.common.item

import com.projecturanus.foodcraft.common.init.FcTabPlant
import net.minecraft.block.BlockCrops
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable
import net.minecraftforge.registries.IForgeRegistry

class ItemCrop(val plantBlock: BlockCrops) : FCRItemFood(), IPlantable {
    override fun getPlantType(world: IBlockAccess?, pos: BlockPos?): EnumPlantType = EnumPlantType.Crop

    override fun getPlant(world: IBlockAccess?, pos: BlockPos?): IBlockState = plantBlock.defaultState

    override fun onItemUse(playerIn: EntityPlayer, worldIn: World, pos: BlockPos, hand: EnumHand, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val stack = playerIn.getHeldItem(hand)
        return if (side != EnumFacing.UP) {
            EnumActionResult.PASS
        } else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            EnumActionResult.FAIL
        } else if (worldIn.getBlockState(pos).block.canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), plantBlock.defaultState.withProperty(BlockCrops.AGE, 0))
            stack.shrink(1)
            EnumActionResult.SUCCESS
        } else {
            EnumActionResult.PASS
        }
    }
}

fun crop(registry: IForgeRegistry<Item>? = null, blockCrops: BlockCrops, init: ItemCrop.() -> Unit): ItemCrop {
    val crop = ItemCrop(blockCrops)
    crop.init()
    crop.creativeTab = FcTabPlant
    registry?.register(crop)
    return crop
}
