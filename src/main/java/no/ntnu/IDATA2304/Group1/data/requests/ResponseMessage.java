package no.ntnu.idata2304.group1.data.requests;

import java.util.HashMap;

public class ResponseMessage extends Message {
    private HashMap<String, Object> data;

    public ResponseMessage(Object data) {
        super(Message.Types.OK);
        this.data = new HashMap<>();
    }

    public Object getData() {
        return data;
    }

    public void addData(String key, Object value) {
        data.put(key, value);
    }
}
