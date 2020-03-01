package com.projecturanus.foodcraft.common.init

import com.projecturanus.foodcraft.MODID
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

val FcTabMachine: CreativeTabs = object : CreativeTabs("$MODID.machine") {
    override fun createIcon(): ItemStack {
        return ItemStack(FCRItems.CIRCUIT)
    }
}
val FcTabPlant: CreativeTabs = object : CreativeTabs("$MODID.plant") {
    //植物
//    val tabIconItem: Item? get() = FoodcraftItems.ItemLajiao

    override fun createIcon(): ItemStack {
        return ItemStack(FCRItems.FACING_HEAVEN_PEPPER)
    }
}
val FcTabDrink: CreativeTabs = object : CreativeTabs("$MODID.drink") {
    //饮料
//    val tabIconItem: Item? get() = FoodcraftItems.ItemPutaozhi

    override fun createIcon(): ItemStack {
        return ItemStack(FCRItems.GRAPE_JUICE)
    }
}
val FcTabStaple: CreativeTabs = object : CreativeTabs("$MODID.staple") {
    //主食
//    val tabIconItem: Item? get() = FoodcraftItems.ItemChaotudousifan

    override fun createIcon(): ItemStack {
        return ItemStack(FCRItems.FRIED_SHREDDED_POTATO_RICE)
    }
}
val FcTabIngredient: CreativeTabs = object : CreativeTabs("$MODID.ingredient") {
    //食材
//    val tabIconItem: Item? get() = FoodcraftItems.ItemMianfen

    override fun createIcon(): ItemStack {
        return ItemStack(FCRItems.FLOUR)
    }
}
val FcTabSnack: CreativeTabs = object : CreativeTabs("$MODID.snack") {
    //零食

    override fun createIcon() = ItemStack(FCRItems.FRIED_DUMPLING)
}
