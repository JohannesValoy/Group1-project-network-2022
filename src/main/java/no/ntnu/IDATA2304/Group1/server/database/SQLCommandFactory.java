package no.ntnu.idata2304.group1.server.database;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class SQLCommandFactory {


    private enum Tables {
        NODE("node"), ROOMS("rooms"), TEMP("logs"), USERS("users");

        private String table;

        Tables(String tableName) {
            this.table = tableName;
        }

        public String getTable() {
            return table;
        }
    }


    private SQLCommandFactory() {};


    public static String getTemperature(Iterator<String> rooms) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder("SELECT * FROM " + Tables.TEMP.getTable()
                + " INNER JOIN " + Tables.ROOMS.getTable() + " ON " + Tables.ROOMS.getTable()
                + ".id = " + Tables.TEMP.getTable() + ".roomid");
        String sqlQuery = "";
        builder.append("WHERE rooms.name IN (");
        while (rooms.hasNext()) {
            String room = rooms.next();
            if (room.isEmpty() || room.isBlank() || room.contains(" ")) {
                throw new IllegalArgumentException("Room name " + room + " is not valid");
            }
            builder.append("\"" + room + "\"");
            if (rooms.hasNext()) {
                builder.append(", ");
            }
        }
        sqlQuery = builder.append(")").toString();
        return sqlQuery;
    }

    public static String login(JSONObject object) throws IllegalArgumentException {
        String sqlQuery = "";
        if (object.has("APIKey")) {
            String key = object.getString("APIKey");
            if (key.isEmpty() || key.isBlank() || key.contains(" ")) {
                throw new IllegalArgumentException("APIKey is not valid");
            }
            sqlQuery = "SELECT * FROM nodes WHERE apikey = '" + object.getString("APIKey");

        } else if (object.has("username") && object.has("password")) {
            String userString = object.getString("username");
            String passwordString = object.getString("password");
            if (userString.isEmpty() || userString.isBlank() || userString.contains(" ")) {
                throw new IllegalArgumentException("Username is not valid");
            }
            if (passwordString.isEmpty() || passwordString.isBlank()
                    || passwordString.contains(" ")) {
                throw new IllegalArgumentException("Password is not valid");
            }
            sqlQuery = "SELECT * FROM " + Tables.USERS.getTable() + " WHERE username = '"
                    + object.getString("username") + "' AND password = '"
                    + object.getString("password") + "'";
        } else {
            throw new IllegalArgumentException(
                    "This JSON object does not contain a APIKey or username and password.");
        }
        return sqlQuery;
    }
}
