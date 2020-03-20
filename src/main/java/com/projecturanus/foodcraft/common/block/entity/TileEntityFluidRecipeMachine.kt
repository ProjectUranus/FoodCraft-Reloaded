package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.recipe.FluidRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.observable.ObservableFluidTank
import com.projecturanus.foodcraft.common.util.set
import com.projecturanus.foodcraft.common.util.shrink
import com.projecturanus.foodcraft.fluid.FluidMilk
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.CapabilityFluidHandler
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryManager
import java.util.concurrent.ConcurrentHashMap

abstract class TileEntityFluidRecipeMachine<T>(recipeRegistry: IForgeRegistry<T>, fluidCapacity: Int, val fluidHandlerSlot: Int,
                                               inputSlots: IntRange, outputSlots: IntRange, slots: Int)
    : TileEntityRecipeMachine<T>(recipeRegistry, inputSlots, outputSlots, slots)
    where T : FluidRecipe<T> {
    companion object {
        val ALLOW_FLUIDS_MAP = ConcurrentHashMap<ResourceLocation, Set<Fluid>>()

        val MILK_ORE_ID by lazy { OreDictionary.getOreID("listAllmilk") }
        val WATER_ORE_ID by lazy { OreDictionary.getOreID("listAllwater") }
    }

    val allowFluids by lazy { ALLOW_FLUIDS_MAP[RegistryManager.ACTIVE.getName(recipeRegistry)] ?: emptySet() }

    open val fluidTank = ObservableFluidTank(fluidCapacity)

    open fun loadFluid(slot: Int) {
        val stack = inventory[slot]
        if (!stack.isEmpty) {
            val fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
            if (fluidHandler != null) {
                val fluid = fluidHandler.drain(1, false)?.fluid ?: fluidHandler.drain(1000, false)?.fluid
                if (fluid != null && allowFluids.contains(fluid) && (fluidTank.fluidAmount == 0 || fluidTank.fluid?.fluid == fluid)) {
                    fluidTank.fill(fluidHandler.drain(4000, true), true)
                    if (fluidHandler is FluidBucketWrapper) {
                        inventory[slot] = fluidHandler.container
                        markDirty()
                    }
                }
            } else if (OreDictionary.getOreIDs(stack).contains(MILK_ORE_ID)) {
                while (fluidTank.fluidAmount < fluidTank.capacity && !stack.isEmpty) {
                    val result = fluidTank.fill(FluidStack(FluidMilk, 1000), true)
                    if (result > 0) {
                        inventory.shrink(slot)
                        markDirty()
                    }
                }
            } else if (OreDictionary.getOreIDs(stack).contains(WATER_ORE_ID)) {
                while (fluidTank.fluidAmount < fluidTank.capacity && !stack.isEmpty) {
                    val result = fluidTank.fill(FluidStack(FluidRegistry.WATER, 1000), true)
                    if (result > 0) {
                        inventory.shrink(slot)
                        markDirty()
                    }
                }
            }
        }
        if (stack.isEmpty && stack != ItemStack.EMPTY)
            inventory[slot] = ItemStack.EMPTY
        recipe = findRecipe()
    }

    override fun consumeInput() {
        super.consumeInput()
        fluidTank.drain(recipe?.fluidInput, true)
    }

    override fun findRecipe(): T? {
        val validRecipes = recipeRegistry.valuesCollection.asSequence().filter { recipe ->
            var valid = true
            for (i in recipe.ingredients.indices) {
                if (!valid) break
                if (!recipe.ingredients[i].apply(inventory[inputSlotsList[i]]))
                    valid = false
            }
            valid
        }
        // Any slot available to insert
        if (!validRecipes.any {
                val stack = it.getRecipeOutput().copy()
                outputSlots.any { slot ->
                    inventory.insertItem(slot, stack, true).isEmpty
                }
            })
            return null

        if (validRecipes.any { it.fluidInput != null }) {
            val recipe = validRecipes.find { it.fluidInput != null }!!
            if (fluidTank.drain(recipe.fluidInput, false)?.amount ?: 0 >= recipe.fluidInput?.amount ?: Int.MAX_VALUE) {
                return recipe
            }
        } else {
            return validRecipes.firstOrNull()
        }
        return null
    }

    override fun onLoad() {
        super.onLoad()
        fluidTank.setTileEntity(this)
        fluidTank.setCanFill(true)
        fluidTank.setCanDrain(true)
        fluidTank.contentChangedListener += {
            loadFluid(fluidHandlerSlot)
        }
        inventory.contentChangedListener += {
            if (it == fluidHandlerSlot) {
                loadFluid(it)
            }
        }
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        fluidTank.readFromNBT(nbt.getCompoundTag("tank"))
        super.readFromNBT(nbt)
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        val compound = super.writeToNBT(compound)
        compound.setTag("tank", fluidTank.writeToNBT(NBTTagCompound()))
        return compound
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when(capability) {
            CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY -> fluidTank as T?
            else -> super.getCapability(capability, facing)
        }
    }

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
    }
}
