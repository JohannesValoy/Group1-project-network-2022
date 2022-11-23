package no.ntnu.idata2304.group1.data.network.responses;

import no.ntnu.idata2304.group1.data.network.Message;

/**
 * The type Response message.
 */
abstract public class ResponseMessage extends Message {
    private final Object data;

    /**
     * Creates a new ResponseMessage
     *
     * @param data the data
     */
    protected ResponseMessage(Object data) {
        super(Message.Types.OK);
        this.data = data;
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
