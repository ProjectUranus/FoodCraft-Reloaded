package com.projecturanus.foodcraft.common.integration.theoneprobe

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.capability.InjectedCapabilities
import mcjty.theoneprobe.api.IProbeHitData
import mcjty.theoneprobe.api.IProbeInfo
import mcjty.theoneprobe.api.IProbeInfoProvider
import mcjty.theoneprobe.api.ProbeMode
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

object HeatInfoProvider : IProbeInfoProvider {
    override fun addProbeInfo(mode: ProbeMode, probeInfo: IProbeInfo, player: EntityPlayer, world: World, blockState: IBlockState, data: IProbeHitData) {
        world.getTileEntity(data.pos)?.getCapability(InjectedCapabilities.TEMPERATURE, data.sideHit)?.temperature?.let {
            probeInfo.text(it.toString())
        }
    }

    override fun getID(): String = "$MODID:heat_info"
}
