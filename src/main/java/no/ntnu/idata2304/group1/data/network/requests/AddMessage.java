package no.ntnu.idata2304.group1.data.network.requests;

import no.ntnu.idata2304.group1.data.network.Message;

/**
 * The type Add message.
 */
public class AddMessage extends Message {
    /**
     * The enum Command.
     */
    public enum Command {
        /**
         * Log command.
         */
        LOG;
    }

    private final Command command;
    private final double value;
    private final String apiKey;


    /**
     * Instantiates a new Add message.
     *
     * @param command the command
     * @param apiKey  the api key
     * @param value   the value
     */
    public AddMessage(Command command, String apiKey, double value) {
        super(Message.Types.ADD);
        this.command = command;
        this.apiKey = apiKey;
        this.value = value;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * Gets api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }
}
