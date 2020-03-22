package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.block.WORKING
import com.projecturanus.foodcraft.common.block.setBlockStateKeep
import com.projecturanus.foodcraft.common.recipe.FcRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.shrink
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.registries.IForgeRegistry
import kotlin.properties.Delegates

abstract class TileEntityRecipeMachine<T>(val recipeRegistry: IForgeRegistry<T>, val inputSlots: IntRange, val outputSlots: IntRange, slots: Int) : TileEntityMachine(slots) where T : FcRecipe<T> {
    abstract val minProgress: Int

    var recipe: T? = null
    var progress = 0
    var working by Delegates.observable(false) { property, old, new ->
        if (old != new) {
            world.setBlockStateKeep(pos, world.getBlockState(pos).withProperty(WORKING, new))
        }
    }

    val inputSlotsList = inputSlots.toList()
    val outputSlotsList = outputSlots.toList()

    override fun onLoad() {
        inventory.contentChangedListener += {
            if (it in inputSlots || it in outputSlots) {
                // When there is only one item left in machine, do not lookup recipes again
                if (recipe == null) recipe = findRecipe()
            }
        }
        inventory.validation = { slot, stack ->
            when (slot) {
                in outputSlots -> false
                else -> true
            }
        }
    }

    override fun update() {
        super.update()

        // Don't run tick on client
        if (world.isRemote) return

        beforeProgress()

        if (recipe != null) {
            // Finish
            if (canFinish()) {
                // Reset
                progress = 0

                consumeInput()
                reset()
                working = false
                var stack = recipe?.getRecipeOutput()?.copy()?: ItemStack.EMPTY
                outputSlots.forEach { slot ->
                    stack = inventory.insertItem(slot, stack, false)
                }
                markDirty()
                recipe = findRecipe()
            } else {
                if (canProgress())
                    progress++
                working = true
            }
        } else if (progress > 0) {
            progress = 0
            working = false
        }
    }

    /**
     * Consume input stacks after progression
     */
    open fun consumeInput() {
        if (recipe == null) return
        recipe?.ingredients?.forEachIndexed { index, ingredient ->
            if (ingredient == Ingredient.EMPTY) return@forEachIndexed

            inventory.shrink(inputSlotsList[index], 1)
        }
    }

    open fun findRecipe(): T? {
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
        return validRecipes.firstOrNull()
    }

    override fun readFromNBT(nbt: NBTTagCompound) {
        super.readFromNBT(nbt)
        progress = nbt.getInteger("progress")
        inventory.deserializeNBT(nbt.getCompoundTag("inventory"))
        recipe = findRecipe()
    }

    override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound {
        val compound = super.writeToNBT(compound)
        compound.setTag("inventory", inventory.serializeNBT())
        compound.setInteger("progress", progress)
        return compound
    }

    /**
     * Reset your custom stuff here
     * Take item
     */
    abstract fun reset()
    abstract fun beforeProgress()
    abstract fun canProgress(): Boolean
    open fun canFinish(): Boolean = progress >= minProgress
}
