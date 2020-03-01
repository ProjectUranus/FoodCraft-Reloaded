package cc.lasmgratel.foodcraftreloaded.common.heat;

import cc.lasmgratel.foodcraftreloaded.common.capability.ITemperature;

public interface HeatHandler extends ITemperature
{
    void update(double bonusRate);

    void setTemperature(double heat);

    void addHeat(double delta);

    double getHeatPower();

    double getMaxHeatPower();
}
