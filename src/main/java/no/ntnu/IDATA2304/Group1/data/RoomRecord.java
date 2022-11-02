package no.ntnu.idata2304.group1.data;

import java.sql.Timestamp;
import java.util.Date;

public class RoomRecord {
    final Date date;
    final double temperature;

    public RoomRecord(Date date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public RoomRecord(Timestamp date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }
}
