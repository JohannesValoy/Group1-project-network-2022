package no.ntnu.idata2304.group1.sensor.sensors;

import no.ntnu.idata2304.group1.data.Sensor;

/**
 * Imitates a temperature sensor placed in a room
 */
public class RoomTemperatureSensor extends BoundedSensor {
    private static final double NORMAL_ROOM_TEMPERATURE = 23.3;
    private static final double MIN_ROOM_TEMPERATURE = 18;
    private static final double MAX_ROOM_TEMPERATURE = 26;

    /**
     * Create a sensor which will imitate temperature readings within a room
     */
    public RoomTemperatureSensor(String name) {
        super(Sensor.Types.TEMPERATURE, name, NORMAL_ROOM_TEMPERATURE, MIN_ROOM_TEMPERATURE,
                MAX_ROOM_TEMPERATURE);
    }
}
