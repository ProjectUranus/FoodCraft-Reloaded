package com.projecturanus.foodcraft.common.item

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.init.FcTabMachine
import com.projecturanus.foodcraft.common.util.Maskable
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import java.awt.Color

class ItemKitchenKnife(maxUses: Int, val colorInternal: Color) : Item(), Maskable {
    init {
        maxStackSize = 1
        maxDamage = maxUses
        creativeTab = FcTabMachine
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
