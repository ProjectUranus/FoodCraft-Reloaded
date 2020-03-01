package com.projecturanus.foodcraft.common.heat;

import com.projecturanus.foodcraft.common.capability.ITemperature;

public interface HeatHandler extends ITemperature
{
    void update(double bonusRate);

    void setTemperature(double heat);

    void addHeat(double delta);

    double getHeatPower();

    double getMaxHeatPower();
}
