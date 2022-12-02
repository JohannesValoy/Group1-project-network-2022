package no.ntnu.idata2304.group1.sensor.sensors;

import no.ntnu.idata2304.group1.data.Sensor;

/**
 * Imitates a humidity sensor placed in a room. The values will be in percent
 */
public class RoomHumiditySensor extends BoundedSensor {
    private static final double NORMAL_ROOM_HUMIDITY = 50;
    private static final double MIN_ROOM_HUMIDITY = 30;
    private static final double MAX_ROOM_HUMIDITY = 70;

    /**
     * Create a sensor which will imitate humidity readings within a room
     *
     * @param name the name
     */
    public RoomHumiditySensor(String name) {
        super(Sensor.Types.HUMIDITY, name, NORMAL_ROOM_HUMIDITY, MIN_ROOM_HUMIDITY,
                MAX_ROOM_HUMIDITY);
    }
}
