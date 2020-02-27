package cc.lasmgratel.foodcraftreloaded.common.item

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistry

open class FCRItemFood : ItemFood(1, 0f, false) {
    open var alwaysEdible = false
    open var healAmount = 1
    open var saturation = 0.6f
    open var isWolfFood = false
    open var duration = 32
    open var potion: PotionEffect? = null
    open var potionProbability = 0f

    override fun getHealAmount(stack: ItemStack): Int {
        return healAmount
    }

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        val stack = playerIn.getHeldItem(handIn)

        return if (playerIn.canEat(alwaysEdible)) {
            playerIn.activeHand = handIn
            ActionResult(EnumActionResult.SUCCESS, stack)
        } else {
            ActionResult(EnumActionResult.FAIL, stack)
        }
    }

    override fun setAlwaysEdible(): FCRItemFood {
        alwaysEdible = true
        return this
    }

    override fun isWolfsFavoriteMeat(): Boolean {
        return isWolfFood
    }

    override fun getSaturationModifier(stack: ItemStack): Float {
        return saturation
    }

    override fun getMaxItemUseDuration(stack: ItemStack): Int {
        return duration
    }

    override fun onItemUseFinish(stack: ItemStack, worldIn: World, entityLiving: EntityLivingBase): ItemStack {
        return super.onItemUseFinish(stack, worldIn, entityLiving)
    }

    override fun setPotionEffect(effect: PotionEffect, probability: Float): FCRItemFood {
        potion = effect
        potionProbability = probability
        return this
    }

    override fun onFoodEaten(stack: ItemStack, worldIn: World, player: EntityPlayer) {
        if (!worldIn.isRemote && potion != null && worldIn.rand.nextFloat() < potionProbability)
            player.addPotionEffect(PotionEffect(potion!!))
    }
}

fun food(registry: IForgeRegistry<Item>? = null, init: FCRItemFood.() -> Unit): FCRItemFood {
    val food = FCRItemFood()
    food.init()
    registry?.register(food)
    return food
}
