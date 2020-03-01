package cc.lasmgratel.foodcraftreloaded.common.item

import cc.lasmgratel.foodcraftreloaded.common.block.entity.TileEntityMachine
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.RayTraceResult
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench
import org.cyclops.commoncapabilities.api.capability.wrench.WrenchTarget

class ItemWrench : Item() {
    class WrenchCapability : IWrench {
        override fun beforeUse(player: EntityPlayer, target: WrenchTarget) {
        }

        override fun afterUse(player: EntityPlayer, target: WrenchTarget) {

        }

        override fun canUse(player: EntityPlayer?, target: WrenchTarget?): Boolean =
            target?.type == RayTraceResult.Type.BLOCK && target.world?.getTileEntity(target.pos) is TileEntityMachine
    }

    class WrenchCapabilityProvider : ICapabilityProvider {
        val wrench = WrenchCapability()

        override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    override fun initCapabilities(stack: ItemStack, nbt: NBTTagCompound?): ICapabilityProvider? {
        return super.initCapabilities(stack, nbt)
    }
}
