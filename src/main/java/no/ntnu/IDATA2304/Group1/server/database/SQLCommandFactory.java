package no.ntnu.idata2304.group1.server.database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.runtime.SwitchBootstraps;
import org.json.JSONArray;
import org.json.JSONObject;

public class SQLCommandFactory {

    /**
     * Supported Commands to SQL
     */
    public enum Commands {
        GET();
    }


    private SQLCommandFactory() {};

    public static String createSQLCommand(Commands command, JSONObject jsonObject)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String string;
        switch (command) {
            case SQLCommandFactory.Commands.GET:
                string = getSQL(jsonObject);
                break;
        }
    }

    private static String getSQL(JSONObject object) {
        try {
            JSONArray rooms = object.getJSONArray("room");

        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }


}
