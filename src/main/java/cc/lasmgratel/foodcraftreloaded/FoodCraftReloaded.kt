package cc.lasmgratel.foodcraftreloaded

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import org.apache.logging.log4j.LogManager

const val MODID = "foodcraftreloaded"
val logger = LogManager.getLogger(MODID)

@Mod(modid = MODID, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object FoodCraftReloaded {
    fun preInit(event: FMLPreInitializationEvent) {

    }
}
