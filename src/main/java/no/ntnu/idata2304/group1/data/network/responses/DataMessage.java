package no.ntnu.idata2304.group1.data.network.responses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.network.requests.get.GetMessage.DataTypes;

/**
 * The type Response message.
 */
public class DataMessage extends OKMessage {

    private final Class type;
    private final List<Object> data;
    /*
     * Creates a new ResponseMessage**
     * 
     * @param data the data
     */

    public DataMessage(Class dataClass, List data) {
        super(true);
        this.type = dataClass;
        this.data = new ArrayList<>(data);
    }

    public DataMessage(List<Room> data) {
        super(true);
        this.type = DataTypes.ROOMS.getClass();
        this.data = new ArrayList<>();
        for (Iterator<Room> iterator = data.iterator(); iterator.hasNext();) {
            Room next = iterator.next();
            this.data.add(next);
        }
    }


    /**
     * Gets the data.
     *
     * @return data
     */
    public Iterator<Object> getData() {
        return data.iterator();
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Class getDataType() {
        return type;
    }
}
