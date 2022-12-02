package no.ntnu.idata2304.group1.data.network;

import java.io.Serializable;

/**
 * A class for sending messages between server and client
 */
public abstract class Message implements Serializable {
    /**
     * The type different classifications of messages
     */
    public enum Type {
        /**
         * Get type.
         */
        GET(),
        /**
         * Add type.
         */
        ADD(),
        /**
         * Remove type.
         */
        REMOVE(),
        /**
         * Update type.
         */
        UPDATE(),
        /**
         * Error type.
         */
        ERROR(),
        /**
         * Ok type.
         */
        OK();

        private Type() {}

    }

    /**
     * The type of message
     */
    private final Type type;

    /**
     * Creates a new Message
     *
     * @param type The type of message
     */
    protected Message(Type type) {
        this.type = type;
    }

    /**
     * Gets the type of message
     *
     * @return The type of message
     */
    public Type getType() {
        return type;
    }

}
