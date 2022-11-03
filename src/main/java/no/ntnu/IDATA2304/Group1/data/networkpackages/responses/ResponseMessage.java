package no.ntnu.idata2304.group1.data.networkpackages.responses;

import no.ntnu.idata2304.group1.data.networkpackages.Message;

abstract public class ResponseMessage extends Message {
    private final Object data;

    /**
     * Creates a new ResponseMessage
     * 
     * @param map The data to send
     */
    protected ResponseMessage(Object data) {
        super(Message.Types.OK);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

}
