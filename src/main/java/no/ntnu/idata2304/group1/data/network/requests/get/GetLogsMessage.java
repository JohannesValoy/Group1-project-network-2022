package no.ntnu.idata2304.group1.data.network.requests.get;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import no.ntnu.idata2304.group1.data.Sensor;
import no.ntnu.idata2304.group1.data.SensorRecord;

public class GetLogsMessage extends GetMessage {
    /**
     * The type of data to get
     */

    private final Sensor.Types dataType;
    private ArrayList<String> rooms;
    private Date from;
    private Date to;
    private int limit = 20;

    /**
     * Creates a new GetLogsMessage
     * 
     * @param dataType The dataType to send
     */
    public GetLogsMessage(Sensor.Types dataType) {
        super(GetMessage.DataTypes.DATA);
        this.dataType = dataType;
        this.rooms = new ArrayList<>();
    }

    /**
     * Creates a new GetLogsMessage
     * 
     * @param temperature The dataType to send
     * @param rooms The rooms to get data from
     */
    public GetLogsMessage(Sensor.Types temperature, ArrayList<String> rooms) {
        super(GetMessage.DataTypes.DATA);
        this.dataType = temperature;
        this.rooms = rooms;
    }

    /**
     * Creates a new GetLogsMessage
     * 
     * @param dataType The dataType to send
     * @param rooms The rooms to get data from
     * @param from The start date
     * @param to The end date
     */
    public GetLogsMessage(Sensor.Types dataType, ArrayList<String> rooms, Date from, Date to) {
        super(GetMessage.DataTypes.DATA);
        this.dataType = dataType;
        this.rooms = rooms;
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new GetLogsMessage
     * 
     * @param dataType The dataType to send
     * @param room The room to get data from
     */
    public GetLogsMessage(Sensor.Types dataType, String room) {
        super(GetMessage.DataTypes.DATA);
        this.dataType = dataType;
        this.rooms = new ArrayList<>();
        this.addRoom(room);
    }

    /**
     * Gets the data type to get
     */
    public Sensor.Types getDataType() {
        return dataType;
    }

    /**
     * Adds a room to the list of rooms to get data from
     * 
     * @param room The room to add
     */
    public void addRoom(String room) {
        if (room == null || room.isEmpty() || room.contains(" ")) {
            throw new IllegalArgumentException("Room cannot be null, empty or contain spaces");
        }
        if (rooms.contains(room)) {
            throw new IllegalArgumentException("Room already added");
        }
        rooms.add(room);
    }

    public Iterator<String> getRooms() {
        return rooms.iterator();
    }

    /**
     * Sets the limit of the amount of data to get
     * 
     * @param limit number between 0 and 100
     */
    public void setLimit(int limit) throws IllegalArgumentException {
        if (0 > limit || limit > 100) {
            throw new IllegalArgumentException("Limit must be between 0 and 100");
        }
        this.limit = limit;
    }

    /**
     * Gets the limit of the amount of data to get
     * 
     * @return The limit
     */
    public int getLimit() {
        return limit;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
