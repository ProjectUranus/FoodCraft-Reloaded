package com.projecturanus.foodcraft.common.integration.theoneprobe

import com.projecturanus.foodcraft.MODID
import com.projecturanus.foodcraft.common.block.entity.TileEntityRecipeMachine
import com.projecturanus.foodcraft.common.util.get
import mcjty.theoneprobe.api.IProbeHitData
import mcjty.theoneprobe.api.IProbeInfo
import mcjty.theoneprobe.api.IProbeInfoProvider
import mcjty.theoneprobe.api.ProbeMode
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

object MachineProvider : IProbeInfoProvider {
    override fun addProbeInfo(mode: ProbeMode, probeInfo: IProbeInfo, player: EntityPlayer, world: World, blockState: IBlockState, data: IProbeHitData) {
        val tileEntity = world.getTileEntity(data.pos)
        if (tileEntity !is TileEntityRecipeMachine<*>) return
        val column = probeInfo.vertical()

        val row = column.horizontal()
        tileEntity.inventory[tileEntity.inputSlots].asSequence().filter { !it.isEmpty }.forEach { row.item(it) }
        row.progress(tileEntity.progress, tileEntity.minProgress)
        tileEntity.inventory[tileEntity.outputSlots].asSequence().filter { !it.isEmpty }.forEach { row.item(it) }
    }

    override fun getID(): String = "$MODID:machine_info"
}
