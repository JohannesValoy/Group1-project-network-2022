package no.ntnu.idata2304.group1.data;

import java.util.Date;

/**
 * A class for storing sensor data
 */
public record SensorRecord(Date date, double value) {
    /**
     * Gets the date of the record
     *
     * @return The date of the record
     */
    @Override
    public Date date() {
        return date;
    }

    /**
     * Gets the reading of the sensor
     *
     * @return The reading of the sensor
     */
    @Override
    public double value() {
        return value;
    }
}
