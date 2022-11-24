package no.ntnu.idata2304.group1.sensor.sensors;

/**
 * Represents (or imitates) one physical sensor on the platform
 */
public interface Sensor {
    /**
     * Read the current value of the sensor
     * 
     * @return The current sensor value
     */
    double readValue();
}
