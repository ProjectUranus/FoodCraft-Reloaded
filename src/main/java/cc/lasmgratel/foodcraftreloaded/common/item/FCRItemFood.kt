package cc.lasmgratel.foodcraftreloaded.common.item

import cc.lasmgratel.foodcraftreloaded.MODID
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
    open var potions: Lazy<List<PotionEffect>> = lazyOf(listOf())
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

    override fun onFoodEaten(stack: ItemStack, world: World, player: EntityPlayer) {
        if (!world.isRemote && world.rand.nextFloat() < potionProbability)
            if (potion != null)
                player.addPotionEffect(PotionEffect(potion!!))
            else if (potions.value.isNotEmpty())
                player.addPotionEffect(PotionEffect(potions.value[world.rand.nextInt(potions.value.size)]))
    }
}

fun food(registry: IForgeRegistry<Item>? = null, name: String? = null, healAmount: Int = 0, saturation: Float = 0f, init: FCRItemFood.() -> Unit): FCRItemFood {
    val food = FCRItemFood()
    name?.let { food.setRegistryName(MODID, it); food.translationKey = "$MODID.$name" }
    if (healAmount != 0)
        food.healAmount = healAmount
    if (saturation != 0f)
        food.saturation = saturation
    food.init()
    registry?.register(food)
    return food
}

