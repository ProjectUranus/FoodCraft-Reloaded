package com.projecturanus.foodcraft.common.block.entity

import com.projecturanus.foodcraft.common.recipe.CHOPPING_BOARD_RECIPES
import com.projecturanus.foodcraft.common.recipe.ChoppingBoardRecipe
import com.projecturanus.foodcraft.common.util.get
import com.projecturanus.foodcraft.common.util.set
import net.minecraft.item.ItemStack

class TileEntityChoppingBoard : TileEntityRecipeMachine<ChoppingBoardRecipe>(CHOPPING_BOARD_RECIPES, 0..2, 3..3, 5) {
    override fun onLoad() {
        super.onLoad()
    }

    override fun reset() {
        if (inventory[4].isItemStackDamageable) {
            inventory[4].itemDamage++
            if (inventory[4].itemDamage > inventory[4].maxDamage)
                inventory[4] = ItemStack.EMPTY
        }
    }

    override fun beforeProgress() {
    }

    override fun canProgress(): Boolean = !inventory[4].isEmpty

    override fun canFinish(): Boolean = progress >= 20
}
