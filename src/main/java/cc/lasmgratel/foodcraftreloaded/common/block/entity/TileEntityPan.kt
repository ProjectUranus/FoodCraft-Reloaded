package cc.lasmgratel.foodcraftreloaded.common.block.entity

import cc.lasmgratel.foodcraftreloaded.common.inventory.ObservableItemHandler
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler

class TileEntityPan : TileEntityMachine() {
    val inventory = ObservableItemHandler(4)

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return when (capability) {
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY -> inventory as T
            else -> null
        }
    }
}
