package no.ntnu.idata2304.group1.data.network.requests.get;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import no.ntnu.idata2304.group1.data.Sensor;

/**
 * The type Get logs message.
 */
public class GetLogsMessage extends GetMessage {

    private final Sensor.Types dataType;
    private ArrayList<String> rooms;
    private Date from;
    private Date to;
    private int limit = 20;

    /**
     * Creates a new GetLogsMessage
     *
     * @param dataType The dataType to get
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
     * @param rooms       The rooms to get data from
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
     * @param rooms    The rooms to get data from
     * @param from     The start date
     * @param to       The end date
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
     * @param room     The room to get data from
     */
    public GetLogsMessage(Sensor.Types dataType, String room) {
        super(GetMessage.DataTypes.DATA);
        this.dataType = dataType;
        this.rooms = new ArrayList<>();
        this.addRoom(room);
    }

    /**
     * Gets the data type it's supposed to get
     *
     * @return the data type
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

    /**
     * Gets rooms.
     *
     * @return the rooms
     */
    public Iterator<String> getRooms() {
        return rooms.iterator();
    }

    /**
     * Sets the limit of the amount of data to get
     *
     * @param limit number higher than 0
     * @throws IllegalArgumentException if limit is lower than 1
     */
    public void setLimit(int limit) throws IllegalArgumentException {
        if (0 >= limit) {
            throw new IllegalArgumentException("Limit must above 0");
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

    /**
     * Gets from.
     *
     * @return the Date from
     */
    public Date getFrom() {
        return from;
    }

    /**
     * Gets to.
     *
     * @return the Date to
     */
    public Date getTo() {
        return to;
    }
}
