package com.projecturanus.foodcraft.common.integration.theoneprobe

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.entity.TileEntityBeverageMaking
import com.projecturanus.foodcraft.common.util.get
import mcjty.theoneprobe.api.IProbeHitData
import mcjty.theoneprobe.api.IProbeInfo
import mcjty.theoneprobe.api.IProbeInfoProvider
import mcjty.theoneprobe.api.ProbeMode
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

object BeverageMakingProvider : IProbeInfoProvider {
    override fun addProbeInfo(mode: ProbeMode, probeInfo: IProbeInfo, player: EntityPlayer, world: World, blockState: IBlockState, data: IProbeHitData) {
        val tileEntity = world.getTileEntity(data.pos)
        if (tileEntity !is TileEntityBeverageMaking) return
        val column = probeInfo.vertical()
        column.horizontal().item(tileEntity.inventory[0]).progress(tileEntity.progress, 200).item(tileEntity.inventory[1])
    }

    override fun getID(): String = "$MODID:beverage_making"
}
