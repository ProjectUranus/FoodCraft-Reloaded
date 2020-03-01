package cc.lasmgratel.foodcraftreloaded.common.init

import cc.lasmgratel.foodcraftreloaded.common.item.FCRItemFood
import net.minecraft.init.MobEffects.*
import net.minecraft.potion.PotionEffect

val STAPLE_POTIONS = lazy { listOf(HASTE, FIRE_RESISTANCE, INVISIBILITY, JUMP_BOOST, SPEED, NIGHT_VISION, WATER_BREATHING).map { PotionEffect(it!!, 600, 1) } }


fun FCRItemFood.snack() {
    saturation = 3f
    creativeTab = FcTabSnack
}

fun FCRItemFood.staple() {
    potions = STAPLE_POTIONS
    creativeTab = FcTabStaple
}
