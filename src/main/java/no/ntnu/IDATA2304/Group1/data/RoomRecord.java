package no.ntnu.idata2304.group1.data;

import java.sql.Timestamp;
import java.util.Date;

/**
 * A class for storing room data
 */
public class RoomRecord {
    final Date date;
    final double temperature;

    /**
     * Creates a new RoomRecord
     * 
     * @param date The date of the record
     * @param temperature The temperature of the room
     */
    public RoomRecord(Date date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }


    /**
     * Gets the date of the record
     * 
     * @return The date of the record
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the temperature of the room
     * 
     * @return The temperature of the room
     */
    public double getTemperature() {
        return temperature;
    }
}
