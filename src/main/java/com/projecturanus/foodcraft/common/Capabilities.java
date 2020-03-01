package com.projecturanus.foodcraft.common;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.cyclops.commoncapabilities.api.capability.wrench.IWrench;

public class Capabilities {
    @CapabilityInject(IWrench.class)
    public static Capability<IWrench> WRENCH_CAPABILITY;
}
