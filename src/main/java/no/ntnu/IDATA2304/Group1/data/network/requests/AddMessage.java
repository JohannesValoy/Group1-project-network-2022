package no.ntnu.idata2304.group1.data.network.requests;

import no.ntnu.idata2304.group1.data.network.Message;

public class AddMessage extends Message {
    public enum Command {
        LOG;
    }

    private final Command command;
    private final double value;
    private final String apiKey;


    public AddMessage(Command command, String apiKey, double value) {
        super(Message.Types.ADD);
        this.command = command;
        this.apiKey = apiKey;
        this.value = value;
    }

    public Command getCommand() {
        return command;
    }

    public double getValue() {
        return value;
    }

    public String getApiKey() {
        return apiKey;
    }
}
