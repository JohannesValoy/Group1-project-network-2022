package no.ntnu.idata2304.group1.data.network.responses;

import java.util.HashMap;
import java.util.Map;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.network.Message;

/**
 * The type Response room message.
 */
public class ResponseRoomMessage extends Message {
    private final HashMap<String, Room> data;

    /**
     * Creates a new ResponseMessage
     *
     * @param map The data to send
     */
    public ResponseRoomMessage(Map<String, Room> map) {
        super(Message.Types.OK);
        this.data = new HashMap<>();
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

}
