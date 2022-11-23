package no.ntnu.idata2304.group1.data.network.requests.get;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class GetLogsMessage extends GetMessage {
    /**
     * The type of data to get
     */
    public enum Logs {
        TEMPERATURE(), HUMIDITY();
    }

    private final Logs dataType;
    private ArrayList<String> rooms;
    private Date from;
    private Date to;

    /**
     * Creates a new GetLogsMessage
     * 
     * @param dataType The dataType to send
     */
    public GetLogsMessage(Logs dataType) {
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
    public GetLogsMessage(Logs temperature, ArrayList<String> rooms) {
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
    public GetLogsMessage(Logs dataType, ArrayList<String> rooms, Date from, Date to) {
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
    public GetLogsMessage(Logs dataType, String room) {
        super(GetMessage.DataTypes.DATA);
        this.dataType = dataType;
        this.rooms = new ArrayList<>();
        this.addRoom(room);
    }

    /**
     * Gets the data type to get
     */
    public Logs getDataType() {
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

}
