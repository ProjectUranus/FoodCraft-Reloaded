package com.projecturanus.foodcraft.common.item

import com.projecturanus.foodcraft.MODID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.util.ResourceLocation
import java.awt.Color

class ItemIcecream(color: Color) : ItemMasked(color, ModelResourceLocation(ResourceLocation(MODID, "ice_cream"), "inventory"))
