package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import com.projecturanus.foodcraft.common.recipe.BEVERAGE_MAKING_RECIPES
import com.projecturanus.foodcraft.common.recipe.BeverageMakingRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.set
import com.projecturanus.foodcraft.common.util.shrink
import com.projecturanus.foodcraft.fluid.FluidMilk
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.FluidTank
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper
import net.minecraftforge.oredict.OreDictionary
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

class TileEntityBeverageMaking : TileEntityMachine(5) {
    companion object {
        val ALLOW_FLUIDS by lazy {
            BEVERAGE_MAKING_RECIPES.valuesCollection.mapNotNull { it.fluidInput?.fluid }
        }
        val MILK_ORE_ID by lazy { OreDictionary.getOreID("listAllmilk") }
        val WATER_ORE_ID by lazy { OreDictionary.getOreID("listAllwater") }
    }

    val tank = FluidTank(8000)
    val heatHandler = FuelHeatHandler()
    val coolHandler = FuelHeatHandler()
    var currentItemBurnTime: Int = 0
    var currentItemCoolTime: Int = 0
    var recipe: BeverageMakingRecipe? = null
    var progress = 0

    override fun onLoad() {
        super.onLoad()
        heatHandler.radiation = 0.15
        heatHandler.heatPower = 0.4
        heatHandler.temperature = ITemperature.ZERO_CELCIUS
        heatHandler.minHeat = ITemperature.ZERO_CELCIUS
        heatHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + 100)
        heatHandler.depleteListener = {
            if (!inventory[3].isEmpty)
                currentItemBurnTime = heatHandler.addFuel(inventory[3])
            if (inventory[3].isEmpty)
                inventory[3] = ItemStack.EMPTY
        }

        coolHandler.radiation = 0.05
        coolHandler.heatPower = 0.13
        coolHandler.cool = true
        coolHandler.minHeat = ITemperature.ZERO_CELCIUS - 80
        coolHandler.temperature = ITemperature.ZERO_CELCIUS
        coolHandler.setMaxHeat(ITemperature.ZERO_CELCIUS + 30)
        coolHandler.depleteListener = {
            if (!inventory[4].isEmpty)
                currentItemCoolTime = coolHandler.addCoolant(inventory[4])
            if (inventory[4].isEmpty)
                inventory[4] = ItemStack.EMPTY
        }

        tank.setTileEntity(this)
        tank.setCanFill(true)
        tank.setCanDrain(true)
        inventory.contentChangedListener += {
            val stack = inventory[it]
                when (it) {
                    0, 1 -> {
                        recipe = lookRecipes()
                    }
                    2 -> { // Tank Input
                        if (!stack.isEmpty) {
                            val fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
                            if (fluidHandler != null) {
                                val fluid = fluidHandler.drain(1, false)?.fluid ?: fluidHandler.drain(1000, false)?.fluid
                                if (fluid != null && ALLOW_FLUIDS.contains(fluid) && (tank.fluidAmount == 0 || tank.fluid?.fluid == fluid)) {
                                    tank.fill(fluidHandler.drain(4000, true), true)
                                    if (fluidHandler is FluidBucketWrapper)
                                        inventory[2] = fluidHandler.container
                                }
                            } else if (OreDictionary.getOreIDs(stack).contains(MILK_ORE_ID)) {
                                while (tank.fluidAmount < tank.capacity && !stack.isEmpty) {
                                    val result = tank.fill(FluidStack(FluidMilk, 1000), true)
                                    if (result > 0) {
                                        inventory.shrink(2)
                                    }
                                }
                            } else if (OreDictionary.getOreIDs(stack).contains(WATER_ORE_ID)) {
                                while (tank.fluidAmount < tank.capacity && !stack.isEmpty) {
                                    val result = tank.fill(FluidStack(FluidRegistry.WATER, 1000), true)
                                    if (result > 0)
                                        inventory.shrink(2)
                                }
                            }
                        }
                        if (stack.isEmpty && stack != ItemStack.EMPTY)
                            inventory[2] = ItemStack.EMPTY
                        recipe = lookRecipes()
                }
                    3 -> {
                        if (!stack.isEmpty && heatHandler.burnTime <= 0.0)
                            currentItemBurnTime = heatHandler.addFuel(stack)
                    }
                    4 -> {
                        if (!stack.isEmpty && coolHandler.burnTime <= 0.0)
                            currentItemCoolTime = coolHandler.addCoolant(stack)
                    }
            }
        }
    }

    override fun update() {
        super.update()

        // Don't run tick on client
        if (world.isRemote) return

        heatHandler.update(0.0)
        coolHandler.update(0.0)
        if (recipe != null) {
            // Finish
            if (progress >= 200) {
                // Reset
                progress = 0
                inventory[0].shrink(1)
                tank.drain(recipe?.fluidInput, true)
                inventory.insertItem(1, recipe?.output ?: ItemStack.EMPTY, false)
                recipe = null
                lookRecipes()
            } else {
                if (recipe?.cool == true) {
                    if (coolHandler.temperature < ITemperature.ZERO_CELCIUS - 10)
                        progress++
                } else if (recipe?.cool == false) {
                    if (heatHandler.temperature > ITemperature.ZERO_CELCIUS + 60)
                        progress++
                } else {
                    progress++
                }
            }
        }
    }

    fun lookRecipes(): BeverageMakingRecipe? {
        for (recipe in BEVERAGE_MAKING_RECIPES.valuesCollection) {
            if (recipe.inputIngredient.apply(inventory[0]) && (inventory[1].isEmpty || (inventory[1].isItemEqual(recipe.output) && inventory[1].count + recipe.output.count <= inventory[1].maxStackSize))) {
                if (recipe.fluidInput == null) {
                    return recipe
                } else {
                    if (tank.drain(recipe.fluidInput, false)?.amount ?: 0 >= recipe.fluidInput?.amount ?: Int.MAX_VALUE) {
                        tank.drain(recipe.fluidInput, false)
                        return recipe
                    }
                }
            }
        }
        return null
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when(capability) {
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY -> tank as T?
            else -> super.getCapability(capability, facing)
        }
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        super.deserializeNBT(nbt)
        tank.readFromNBT(nbt.getCompoundTag("tank"))
        heatHandler.deserializeNBT(nbt.getCompoundTag("heat"))
        coolHandler.deserializeNBT(nbt.getCompoundTag("cool"))
        inventory.deserializeNBT(nbt.getCompoundTag("inventory"))
    }

    override fun serializeNBT(): NBTTagCompound {
        val compound = super.serializeNBT()
        compound.setTag("tank", tank.writeToNBT(NBTTagCompound()))
        compound.setTag("heat", heatHandler.serializeNBT())
        compound.setTag("cool", coolHandler.serializeNBT())
        compound.setTag("inventory", inventory.serializeNBT())
        return compound
    }
}
