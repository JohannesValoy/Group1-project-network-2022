package no.ntnu.idata2304.group1.data.requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GetMessage extends Message {
    // TODO: Is there a better way to restrict the type of the parameters?
    public enum Types {
        ROOM_TEMP(), ROOM_HUMIDITY();
    }

    private GetMessage.Types command;
    private HashMap<String, Object> parameters;

    // TODO: Find a way to pass parameters to the request

    // private HashMap<String, Object> parameters;

    public GetMessage(GetMessage.Types command) {
        super(Message.Types.GET);
        this.command = command;
        this.parameters = new HashMap<>();
        parameters.put("rooms", new ArrayList<String>());
    }

    public GetMessage.Types getCommand() {
        return command;
    }

    public void addRoom(String roomName) {
        ((ArrayList<String>) parameters.get("rooms")).add(roomName);
    }

    public Iterator<String> getRooms() {
        return ((ArrayList<String>) parameters.get("rooms")).iterator();
    }
}
