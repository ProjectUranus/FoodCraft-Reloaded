package com.projecturanus.foodcraft.common.item

import com.projecturanus.foodcraft.MODID
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects.*
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import java.awt.Color

class ItemLiqueur(color: Color) : ItemMasked(color, ModelResourceLocation(ResourceLocation(MODID, "liqueur"), "inventory")) {
    override fun getItemUseAction(stack: ItemStack) = EnumAction.DRINK

    override fun onFoodEaten(stack: ItemStack, world: World, player: EntityPlayer) {
        if (!world.isRemote) {
            when (world.rand.nextInt(2)) {
                1 -> {
                    player.addPotionEffect(PotionEffect(JUMP_BOOST, 600, 3));
                    player.addPotionEffect(PotionEffect(SPEED, 600, 3));
                    player.addPotionEffect(PotionEffect(HASTE, 600, 3));
                    player.addPotionEffect(PotionEffect(INSTANT_HEALTH, 600, 3));
                }
                else -> {
                    player.addPotionEffect(PotionEffect(HUNGER, 600, 3));
                    player.addPotionEffect(PotionEffect(MINING_FATIGUE, 600, 3));
                    player.addPotionEffect(PotionEffect(SLOWNESS, 600, 3));
                    player.addPotionEffect(PotionEffect(NAUSEA, 600, 3));
                }
            }
        }
    }
}
