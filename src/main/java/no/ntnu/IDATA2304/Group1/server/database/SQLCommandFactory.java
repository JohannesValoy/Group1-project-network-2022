package no.ntnu.idata2304.group1.server.database;

import org.json.JSONArray;
import org.json.JSONObject;

public class SQLCommandFactory {


    enum Tables {
        NODE("node"), ROOM("room"), TEMP("temprature");

        private String table;

        Tables(String tablename) {
            this.table = tablename;
        }

    }

    private SQLCommandFactory() {};

    // TODO: Translate a JSONObject over to a SQL Command
    public static String createSQLCommand() {
        String string;
        throw new RuntimeException("Not Implemented");
    }

    // TODO: Translate the GET command to SQL and fetch the response
    private static String get(JSONObject object) {
        try {
            JSONArray rooms = object.getJSONArray("room");

        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }


}
