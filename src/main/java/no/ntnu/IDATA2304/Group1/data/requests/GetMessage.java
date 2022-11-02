package no.ntnu.idata2304.group1.data.requests;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class GetMessage extends Message {
    public enum Types {
        ROOM_TEMP(), ROOM_HUMIDITY();
    }

    private GetMessage.Types command;
    private ArrayList<String> rooms;
    private Date from;
    private Date to;

    // TODO: Find a way to pass parameters to the request

    public GetMessage(GetMessage.Types command) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = new ArrayList<>();
    }

    public GetMessage(GetMessage.Types command, ArrayList<String> rooms) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = rooms;
    }

    public GetMessage(GetMessage.Types command, ArrayList<String> rooms, Date from, Date to) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = rooms;
        this.from = from;
        this.to = to;
    }

    public GetMessage(GetMessage.Types command, String room) {
        super(Message.Types.GET);
        this.command = command;
        this.rooms = new ArrayList<>();
        this.rooms.add(room);
    }

    public GetMessage.Types getCommand() {
        return command;
    }

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
