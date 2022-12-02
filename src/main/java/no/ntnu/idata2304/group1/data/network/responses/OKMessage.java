package no.ntnu.idata2304.group1.data.network.responses;

import no.ntnu.idata2304.group1.data.network.Message;

/**
 * The type Ok message.
 */
public class OKMessage extends Message {

    /**
     * If it contains data.
     */
    boolean containsData = false;

    /**
     * Instantiates a new Ok message.
     */
    public OKMessage() {
        super(Type.OK);
    }

    /**
     * Instantiates a new Ok message.
     *
     * @param containsData the contains data
     */
    protected OKMessage(boolean containsData) {
        super(Type.OK);
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
