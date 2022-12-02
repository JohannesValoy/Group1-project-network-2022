package no.ntnu.idata2304.group1.data.network.responses;

import no.ntnu.idata2304.group1.data.network.Message;

/**
 * A class for sending error messages between server and client
 */
public class ErrorMessage extends Message {
    private final String message;

    /**
     * Creates a new ErrorMessage
     *
     * @param message the message to send
     */
    public ErrorMessage(String message) {
        super(Type.ERROR);
        this.message = message;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return message;
    }
}
