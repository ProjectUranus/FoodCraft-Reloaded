package com.projecturanus.foodcraft.common.heat;

import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;

import javax.annotation.Nonnull;

public interface HeatHandler extends ITemperature
{
    void update(double bonusRate);

    void setTemperature(double heat);

    void addHeat(double delta);

    double getHeatPower();

    double getMaxHeatPower();

    void bind(@Nonnull ITemperature temperatureSource);
    void unbind(@Nonnull ITemperature temperatureSource);

    void bind(@Nonnull HeatHandler heatHandler);
    void unbind(@Nonnull HeatHandler heatHandler);
}
