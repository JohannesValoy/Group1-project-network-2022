package no.ntnu.idata2304.group1.data.networkpackages.requests;

import no.ntnu.idata2304.group1.data.networkpackages.Message;

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
