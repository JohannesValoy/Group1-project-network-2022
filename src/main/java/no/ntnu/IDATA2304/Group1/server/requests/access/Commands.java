package no.ntnu.idata2304.group1.server.requests.access;

import java.lang.reflect.InvocationTargetException;
import org.json.JSONObject;
import no.ntnu.idata2304.group1.server.database.SQLCommandFactory;

public class Commands {

    private Commands() {};

    /**
     * @param object
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static JSONObject get(JSONObject object)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String SQLRequest = SQLCommandFactory.createSQLCommand(object);
        return new JSONObject();
    }

    public static JSONObject add() {
        return new JSONObject();
    }
}
