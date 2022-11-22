package no.ntnu.idata2304.group1.data.network.responses;

import no.ntnu.idata2304.group1.data.network.Message;

/**
 * The type Ok message.
 */
public class OKMessage extends Message {

    /**
     * Instantiates a new Ok message.
     */
    public OKMessage() {
        super(Message.Types.OK);
    }
}
