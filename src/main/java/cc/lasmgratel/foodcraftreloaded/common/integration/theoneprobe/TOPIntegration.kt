package cc.lasmgratel.foodcraftreloaded.common.integration.theoneprobe

import mcjty.theoneprobe.api.ITheOneProbe
import net.minecraftforge.fml.common.Optional

object TOPIntegration {
    @Optional.Method(modid = "theoneprobe")
    fun register(theOneProbe: ITheOneProbe) {
        theOneProbe.registerProvider(HeatInfoProvider)
    }
}
