package com.projecturanus.foodcraft.common.item

import net.minecraft.block.Block
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

/**
 * Delegate translation to block
 */
class ItemBlockTranslation(block: Block) : ItemBlock(block) {
    @SideOnly(Side.CLIENT)
    override fun getItemStackDisplayName(stack: ItemStack): String =
        block.localizedName
}
