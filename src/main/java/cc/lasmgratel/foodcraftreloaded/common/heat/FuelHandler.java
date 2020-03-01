package cc.lasmgratel.foodcraftreloaded.common.heat;

public interface FuelHandler
{
    void update(double bonusRate);

    double getBurnTime();

    void setBurnTime(double burnTime);

    double getMaxBurnTime();

    void addBurnTime(double delta);
}
