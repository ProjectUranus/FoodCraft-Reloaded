package com.projecturanus.foodcraft.common.integration.theoneprobe

import mcjty.theoneprobe.api.ITheOneProbe
import net.minecraftforge.fml.common.Optional
import java.util.function.Function

class TOPIntegration : Function<ITheOneProbe, Void?> {
    companion object {
        lateinit var theOneProbe: ITheOneProbe
    }

    @Optional.Method(modid = "theoneprobe")
    override fun apply(theOneProbe: ITheOneProbe): Void? {
        Companion.theOneProbe = theOneProbe
        theOneProbe.registerProvider(StoveProvider)
        theOneProbe.registerProvider(MachineProvider)
        return null
    }
}
