package com.projecturanus.foodcraft.common.integration.theoneprobe

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.entity.TileEntityStove
import com.projecturanus.foodcraft.common.util.get
import mcjty.theoneprobe.api.IProbeHitData
import mcjty.theoneprobe.api.IProbeInfo
import mcjty.theoneprobe.api.IProbeInfoProvider
import mcjty.theoneprobe.api.ProbeMode
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature

object StoveProvider : IProbeInfoProvider {
    override fun addProbeInfo(mode: ProbeMode, probeInfo: IProbeInfo, player: EntityPlayer, world: World, blockState: IBlockState, data: IProbeHitData) {
        val tileEntity = world.getTileEntity(data.pos)
        if (tileEntity !is TileEntityStove) return
        probeInfo.item(tileEntity.inventory[0])
        probeInfo.horizontal()
            .text("Burn Time:")
            .progress(tileEntity.heatHandler.burnTime.toInt(), tileEntity.currentItemBurnTime)
        probeInfo.text(String.format("%.2f℃", tileEntity.heatHandler.temperature - ITemperature.ZERO_CELCIUS))
        probeInfo.text(String.format("Max temperature: %.2f℃", tileEntity.heatHandler.maximumTemperature - ITemperature.ZERO_CELCIUS))
    }

    override fun getID(): String = "$MODID:stove"
}
