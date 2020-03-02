package com.projecturanus.foodcraft.common.integration.theoneprobe

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.util.iterator
import mcjty.theoneprobe.api.IProbeHitData
import mcjty.theoneprobe.api.IProbeInfo
import mcjty.theoneprobe.api.IProbeInfoProvider
import mcjty.theoneprobe.api.ProbeMode
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler

object MachineProvider : IProbeInfoProvider {
    override fun addProbeInfo(mode: ProbeMode, probeInfo: IProbeInfo, player: EntityPlayer, world: World, blockState: IBlockState, data: IProbeHitData) {
        val tileEntity = world.getTileEntity(data.pos)
        if (blockState.block.registryName?.namespace != MODID || tileEntity == null)
            return
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, data.sideHit)?.iterator()?.forEach {
            probeInfo.item(it)
        }
    }

    override fun getID(): String = "$MODID:machine_info"
}
