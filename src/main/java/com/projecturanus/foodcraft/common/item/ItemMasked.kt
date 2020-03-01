package com.projecturanus.foodcraft.common.item

import com.projecturanus.foodcraft.common.util.Maskable
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.Color

open class ItemMasked(val colorInternal: Color, val modelResourceLocation: ModelResourceLocation) : Maskable, FCRItemFood() {
    var hasSuffix = false
    var realTranslationKey = ""
    var baseTranslationKey = ""

    override fun getColor() = colorInternal
    override fun getModelLocation(): ModelResourceLocation = modelResourceLocation

    @SideOnly(Side.CLIENT)
    override fun getItemStackDisplayName(stack: ItemStack): String {
        return if (hasSuffix) I18n.format(baseTranslationKey, I18n.format(realTranslationKey)) else super.getItemStackDisplayName(stack)
    }
}
