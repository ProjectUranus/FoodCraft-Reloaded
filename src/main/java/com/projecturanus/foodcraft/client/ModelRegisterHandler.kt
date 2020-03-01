package com.projecturanus.foodcraft.client

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.init.*
import com.projecturanus.foodcraft.common.util.Colorable
import com.projecturanus.foodcraft.common.util.Maskable
import net.minecraft.block.BlockLeaves
import net.minecraft.block.BlockSapling
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.color.IItemColor
import net.minecraft.item.Item
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side


@Mod.EventBusSubscriber(value = [Side.CLIENT], modid = MODID)
object ModelRegisterHandler {
    @JvmStatic
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        DEFAULT_MODEL_ITEMS.forEach(ModelRegisterHandler::registerDefault)
        (DRINKS.asSequence().map { it as Item } + ICECREAMS + JAMS + LIQUEURS + KITCHEN_KNIFES).forEach {
            ModelLoader.setCustomModelResourceLocation(it, 0, (it as Maskable).modelLocation)
        }
        LEAVES.forEach {
            ModelLoader.setCustomStateMapper(it) {  mapOf(
                it.defaultState to ModelResourceLocation(it.registryName, "normal"),
                it.defaultState.withProperty(BlockLeaves.DECAYABLE, false).withProperty(BlockLeaves.CHECK_DECAY, true) to ModelResourceLocation(it.registryName, "normal"),
                it.defaultState.withProperty(BlockLeaves.DECAYABLE, false).withProperty(BlockLeaves.CHECK_DECAY, false) to ModelResourceLocation(it.registryName, "normal"),
                it.defaultState.withProperty(BlockLeaves.DECAYABLE, true).withProperty(BlockLeaves.CHECK_DECAY, false) to ModelResourceLocation(it.registryName, "normal")
            ) }
        }
        SAPLINGS.forEach { block ->
            ModelLoader.setCustomStateMapper(block) { BlockSapling.STAGE.allowedValues.asSequence().map { value -> it.defaultState.withProperty(BlockSapling.STAGE, value) to ModelResourceLocation(it.registryName, "normal") }.toMap() }
            registerDefault(Item.getItemFromBlock(block))
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun registerColors(event: ColorHandlerEvent.Item) {
        event.itemColors.registerItemColorHandler(IItemColor { stack, tintIndex ->
            if (stack.item is Colorable)
                (stack.item as Colorable).getColor(tintIndex)
            else -1
        }, *(DRINKS.asSequence().map { it as Item } + ICECREAMS + JAMS + LIQUEURS + KITCHEN_KNIFES).toList().toTypedArray())
    }

    @JvmStatic
    fun registerDefault(item: Item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation(item.registryName!!, "inventory"))
    }
}
