package no.ntnu.idata2304.group1.data.network.requests;

import no.ntnu.idata2304.group1.data.network.Message;

public class AddMessage extends Message {
    enum Command {
        ROOM, NODE, LOG;
    }

    private final Command command;

    public AddMessage(Command command) {
        super(Message.Types.ADD);
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
