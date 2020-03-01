package com.projecturanus.foodcraft.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;

public class InjectedCapabilities {
    @CapabilityInject(ITemperature.class)
    public static Capability<ITemperature> TEMPERATURE = null;
}
