package com.projecturanus.foodcraft.common.init

import com.projecturanus.foodcraft.MODID
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = MODID)
object LootTableInjectionHandler {
    @JvmStatic
    @SubscribeEvent
    fun injectSnacks(event: LootTableLoadEvent) {
        if (event.name.namespace == "minecraft" && (event.name.path.startsWith("chests")))
            event.table.addPool(event.lootTableManager.getLootTableFromLocation(ResourceLocation(MODID, "snacks")).getPool("snacks_pool"));
    }
}
