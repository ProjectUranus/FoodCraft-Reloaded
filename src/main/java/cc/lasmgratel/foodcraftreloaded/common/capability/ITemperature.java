package cc.lasmgratel.foodcraftreloaded.common.capability;

/**
 * Indicates if something has a temperature.
 * @author rubensworks
 */
public interface ITemperature extends org.cyclops.commoncapabilities.api.capability.temperature.ITemperature {

    /**
     * Zero degrees Celcius in Kelvin.
     */
    double ZERO_CELCIUS = 273.15;

    /**
     * @return The current temperature in degrees Kelvin.
     */
    double getTemperature();

    /**
     * @return The maximum temperature the target can have.
     */
    double getMaximumTemperature();

    /**
     * @return The minimum temperature the target can have.
     */
    double getMinimumTemperature();

    /**
     * A default temperature is active in the target's default state,
     * for example if a machine has just been created and has its default temperature.
     * @return The default temperature the target has.
     */
    double getDefaultTemperature();

}
