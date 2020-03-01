package com.projecturanus.foodcraft.common.item

import com.projecturanus.foodcraft.MODID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.util.ResourceLocation
import java.awt.Color

class ItemJam(color: Color) : ItemMasked(color, ModelResourceLocation(ResourceLocation(MODID, "jam"), "inventory")) {
    override fun getColor(tintIndex: Int): Int = if (tintIndex == 0) color.rgb else -1
}
