package no.ntnu.idata2304.group1.data.networkpackages.requests;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import no.ntnu.idata2304.group1.data.networkpackages.Message;

/**
 * A class for sending get commands to the server
 */
public class GetMessage extends Message {
    /**
     * The type of data to get
     */
    public enum Types {
        ROOM_TEMP(), ROOM_HUMIDITY();
    }

    private GetMessage.Types command;
    private ArrayList<String> rooms;
    private Date from;
    private Date to;

    // TODO: Find a way to pass parameters to the request

    /**
     * Creates a new GetMessage
     * 
     * @param command The command to send
     */
    public GetMessage(GetMessage.Types command) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = new ArrayList<>();
    }

    /**
     * Creates a new GetMessage
     * 
     * @param command The command to send
     * @param rooms The rooms to get data from
     */
    public GetMessage(GetMessage.Types command, ArrayList<String> rooms) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = rooms;
    }

    /**
     * Creates a new GetMessage
     * 
     * @param command The command to send
     * @param rooms The rooms to get data from
     * @param from The start date
     * @param to The end date
     */
    public GetMessage(GetMessage.Types command, ArrayList<String> rooms, Date from, Date to) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = rooms;
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new GetMessage
     * 
     * @param command The command to send
     * @param room The room to get data from
     */
    public GetMessage(GetMessage.Types command, String room) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = new ArrayList<>();
        this.addRoom(room);
    }

    /**
     * Gets the data type to get
     */
    public GetMessage.Types getCommand() {
        return command;
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
