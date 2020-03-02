package com.projecturanus.foodcraft.common.integration.theoneprobe

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.capability.ITemperature
import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import com.projecturanus.foodcraft.common.heat.FuelHeatHandler
import mcjty.theoneprobe.api.IProbeHitData
import mcjty.theoneprobe.api.IProbeInfo
import mcjty.theoneprobe.api.IProbeInfoProvider
import mcjty.theoneprobe.api.ProbeMode
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

object HeatInfoProvider : IProbeInfoProvider {
    override fun addProbeInfo(mode: ProbeMode, probeInfo: IProbeInfo, player: EntityPlayer, world: World, blockState: IBlockState, data: IProbeHitData) {
        world.getTileEntity(data.pos)?.getCapability(InjectedCapabilities.TEMPERATURE, data.sideHit)?.let {
            probeInfo.text(String.format("%.2f℃", it.temperature - ITemperature.ZERO_CELCIUS))
            probeInfo.text(String.format("Max temperature: %.2f℃", it.maximumTemperature - ITemperature.ZERO_CELCIUS))
            if (it is FuelHeatHandler) {
                probeInfo.text(String.format("Burn time: %d", it.burnTime))
            }
        }
    }

    override fun getID(): String = "$MODID:heat_info"
}
