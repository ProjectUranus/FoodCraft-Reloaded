package cc.lasmgratel.foodcraftreloaded.client

import cc.lasmgratel.foodcraftreloaded.MODID
import cc.lasmgratel.foodcraftreloaded.common.init.CROPS
import cc.lasmgratel.foodcraftreloaded.common.init.INGREDIENTS
import cc.lasmgratel.foodcraftreloaded.common.init.SNACKS
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
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
        (SNACKS.asSequence() + CROPS + INGREDIENTS).forEach(ModelRegisterHandler::registerDefault)
    }

    @JvmStatic
    fun registerDefault(item: Item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation(item.registryName!!, "inventory"))
    }
}
