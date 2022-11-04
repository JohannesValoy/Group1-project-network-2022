package no.ntnu.idata2304.group1.data.network;

import java.io.Serializable;

/**
 * A class for sending messages between server and client
 */
public abstract class Message implements Serializable {
    /**
     * The type different classifications of messages
     */
    public enum Types {
        GET(), ADD(), REMOVE(), UPDATE(), ERROR(), OK();

        private Types() {}

    }

    /**
     * The type of message
     */
    private final Types type;

    /**
     * Creates a new Message
     * 
     * @param type The type of message
     */
    protected Message(Types type) {
        this.type = type;
    }

    /**
     * Gets the type of message
     * 
     * @return The type of message
     */
    public Message.Types getType() {
        return type;
    }

}
