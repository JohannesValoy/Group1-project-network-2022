package no.ntnu.idata2304.group1.data.requests;

import java.util.ArrayList;
import java.util.HashMap;
import no.ntnu.idata2304.group1.data.RoomRecord;

public class ResponseMessage extends Message {
    private HashMap<String, ArrayList<RoomRecord>> data;

    public ResponseMessage(Object data) {
        super(Message.Types.OK);
        this.data = new HashMap<>();
    }

    public Object getData() {
        return data;
    }

    public void addData(String room, RoomRecord data) {
        if (room == null || room.isEmpty() || room.contains(" ")) {
            throw new IllegalArgumentException("Key cannot be null, empty or contain spaces");
        }
        if (!this.data.containsKey(room)) {
            this.data.put(room, new ArrayList<>());
        }
        this.data.get(room).add(data);

    }
}
