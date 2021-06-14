package com.projecturanus.foodcraft.common.item

import com.projecturanus.foodcraft.MODID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import java.awt.Color

open class ItemDrink(color: Color) : ItemMasked(color, ModelResourceLocation(ResourceLocation(MODID, "drink"), "inventory")) {
    override fun getItemUseAction(stack: ItemStack) = EnumAction.DRINK

    override fun onFoodEaten(stack: ItemStack, world: World, player: EntityPlayer) {
        if (!world.isRemote) {
            if (world.rand.nextFloat() < potionProbability) {
                if (potion != null)
                    player.addPotionEffect(PotionEffect(potion!!))
                else if (potions.value.isNotEmpty()) {
                    player.addPotionEffect(PotionEffect(potions.value[world.rand.nextInt(potions.value.size)]))
                }
            }
            //player.addItemStackToInventory(ItemStack(FCRItems.GLASS_CUP))
        }
    }
}
