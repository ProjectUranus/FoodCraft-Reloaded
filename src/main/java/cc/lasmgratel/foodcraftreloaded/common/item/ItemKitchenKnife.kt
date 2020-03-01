package cc.lasmgratel.foodcraftreloaded.common.item

import cc.lasmgratel.foodcraftreloaded.MODID
import cc.lasmgratel.foodcraftreloaded.common.util.Maskable
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import java.awt.Color

class ItemKitchenKnife(val maxUses: Int, val colorInternal: Color) : Item(), Maskable {
    init {
        maxStackSize = 1
        maxDamage = maxUses
    }

    override fun hitEntity(stack: ItemStack, target: EntityLivingBase?, attacker: EntityLivingBase): Boolean {
        stack.damageItem(2, attacker)
        return true
    }

    override fun getColor(tintIndex: Int): Int {
        return if (tintIndex == 0) color.rgb else -1
    }

    override fun getColor(): Color = colorInternal
    override fun getModelLocation(): ModelResourceLocation = ModelResourceLocation(ResourceLocation(MODID, "kitchen_knife"), "inventory")
}
