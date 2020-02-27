package cc.lasmgratel.foodcraftreloaded.common.init

import cc.lasmgratel.foodcraftreloaded.MODID
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

val FcTabMachine: CreativeTabs = object : CreativeTabs("$MODID.machine") {
    override fun createIcon(): ItemStack {
        return ItemStack.EMPTY
    }
}
val FcTabPlant: CreativeTabs = object : CreativeTabs("$MODID.plant") {
    //植物
//    val tabIconItem: Item? get() = FoodcraftItems.ItemLajiao

    override fun createIcon(): ItemStack {
        return ItemStack.EMPTY
    }
}
val FcTabDrink: CreativeTabs = object : CreativeTabs("$MODID.drink") {
    //饮料
//    val tabIconItem: Item? get() = FoodcraftItems.ItemPutaozhi

    override fun createIcon(): ItemStack {
        return ItemStack.EMPTY
    }
}
val FcTabStaple: CreativeTabs = object : CreativeTabs("$MODID.staple") {
    //主食
//    val tabIconItem: Item? get() = FoodcraftItems.ItemChaotudousifan

    override fun createIcon(): ItemStack {
        return ItemStack.EMPTY
    }
}
val FcTabIngredient: CreativeTabs = object : CreativeTabs("$MODID.ingredient") {
    //食材
//    val tabIconItem: Item? get() = FoodcraftItems.ItemMianfen

    override fun createIcon(): ItemStack {
        return ItemStack.EMPTY
    }
}
val FcTabSnack: CreativeTabs = object : CreativeTabs("$MODID.snack") {
    //零食

    override fun createIcon() = ItemStack(FCRItems.FRIED_DUMPLING)
}
