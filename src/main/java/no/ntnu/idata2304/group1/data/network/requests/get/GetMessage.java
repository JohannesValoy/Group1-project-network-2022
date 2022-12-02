package no.ntnu.idata2304.group1.data.network.requests.get;

import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.Sensor;
import no.ntnu.idata2304.group1.data.network.Message;

/**
 * A class for sending get commands to the server
 */
public abstract class GetMessage extends Message {

    /**
     * The type of get command to send
     */
    public enum DataTypes {
        /**
         * Rooms data types.
         */
        ROOMS(Room.class),
        /**
         * Sensor data types.
         */
        Sensor(Sensor.class),
        /**
         * Data data types.
         */
        DATA(Room.class);

        private Class type;

        DataTypes(Class type) {
            this.type = type;
        }

        /**
         * Gets class data.
         *
         * @return the class type
         */
        public Class getDataClass() {
            return type;
        }
    }

    private final GetMessage.DataTypes command;

    /**
     * Instantiates a new Get message.
     *
     * @param type the type
     */
    protected GetMessage(DataTypes type) {
        super(Type.GET);
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
