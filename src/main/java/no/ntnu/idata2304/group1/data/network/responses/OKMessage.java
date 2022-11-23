package no.ntnu.idata2304.group1.data.network.responses;

import no.ntnu.idata2304.group1.data.network.Message;

/**
 * The type Ok message.
 */
public class OKMessage extends Message {

    boolean containsData = false;

    /**
     * Instantiates a new Ok message.
     */
    public OKMessage() {
        super(Message.Types.OK);
    }

    public OKMessage(boolean containsData) {
        super(Message.Types.OK);
        this.containsData = containsData;
    }

    /**
     * Contains data boolean.
     *
     * @return the boolean
     */
    public boolean containsData() {
        return containsData;
    }
}
