package no.ntnu.idata2304.group1.data.network.requests.get;

import javafx.scene.Node;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.network.Message;

/**
 * A class for sending get commands to the server
 */
public abstract class GetMessage extends Message {

    /**
     * The type of get command to send
     */
    public enum DataTypes {
        ROOMS(Room.class), NODES(Node.class), DATA(Room.class);

        private Class type;

        DataTypes(Class type) {
            this.type = type;
        }

        public Class getDataClass() {
            return type;
        }
    }

    private final GetMessage.DataTypes command;

    protected GetMessage(DataTypes type) {
        super(Message.Types.GET);
        this.command = type;
    }

    /**
     * Gets the command to send
     * 
     * @return The command to send
     */
    public GetMessage.DataTypes getCommand() {
        return command;
    }
}
