package cc.lasmgratel.foodcraftreloaded.common.item

import cc.lasmgratel.foodcraftreloaded.MODID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.util.ResourceLocation
import java.awt.Color

class ItemIcecream(color: Color) : ItemMasked(color, ModelResourceLocation(ResourceLocation(MODID, "ice_cream"), "inventory"))
