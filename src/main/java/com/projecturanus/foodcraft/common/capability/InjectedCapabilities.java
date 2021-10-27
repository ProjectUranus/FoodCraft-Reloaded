package com.projecturanus.foodcraft.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.cyclops.commoncapabilities.api.capability.inventorystate.IInventoryState;
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;
import org.cyclops.commoncapabilities.api.capability.work.IWorker;
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench;

public class InjectedCapabilities {
    @CapabilityInject(ITemperature.class)
    public static Capability<ITemperature> TEMPERATURE = null;

    @CapabilityInject(IInventoryState.class)
    public static Capability<IInventoryState> INVENTORY_STATE = null;

    @CapabilityInject(IWrench.class)
    public static Capability<IWrench> WRENCH = null;

    @CapabilityInject(IWorker.class)
    public static Capability<IWorker> WORKER = null;
}
