package no.ntnu.idata2304.group1.server.database;

import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * A class for creating SQL commands for different requests
 */
public class SQLCommandFactory {

    /**
     * Enum for linking the different kind of tables in the database
     */
    private enum Tables {
        NODE("nodes"), ROOMS("rooms"), TEMP("logs"), USERS("users");

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
        if (rooms == null) {
            throw new IllegalArgumentException("The rooms cannot be null");
        }
        StringBuilder builder = new StringBuilder(
                "SELECT * FROM " + Tables.TEMP.getTable() + " INNER JOIN " + Tables.ROOMS.getTable()
                        + " ON " + Tables.ROOMS.getTable() + ".id = " + Tables.TEMP.getTable()
                        + ".roomid " + "INNER JOIN " + Tables.NODE.getTable() + " ON "
                        + Tables.NODE.getTable() + ".id = " + Tables.TEMP.getTable()
                        + ".nodeid AND " + Tables.NODE.getTable() + ".type LIKE 'temperature' ");
        String sqlQuery = "";
        if (rooms.hasNext()) {
            builder.append("WHERE rooms.name IN (");
        }
        while (rooms.hasNext()) {
            String room = rooms.next();
            if (checkValidString(room)) {
                throw new IllegalArgumentException("Room name '" + room + "'' is not valid");
            }
            builder.append("\"" + room + "\"");
            if (rooms.hasNext()) {
                builder.append(", ");
            }
        }
        sqlQuery = builder.append(")").toString();
        return sqlQuery;
    }

    public static String getTemperature(Iterator<String> rooms, Date from, Date to)
            throws IllegalArgumentException {
        if (rooms == null) {
            throw new IllegalArgumentException("The rooms cannot be null");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("The dates cannot be null");
        }
        StringBuilder builder = new StringBuilder(getTemperature(rooms));
        builder.append(" AND ");
        builder.append(Tables.TEMP.getTable() + ".date BETWEEN " + from.getTime() + " AND "
                + to.getTime());
        return builder.toString();
    }

    public static String checkNodeKey(String key) {
        if (checkValidString(key)) {
            throw new IllegalArgumentException("The key is invalid");
        }
        return "SELECT * FROM " + Tables.NODE.getTable() + " WHERE " + Tables.NODE.getTable()
                + ".key LIKE \"" + key + "\"";
    }

    private static boolean checkValidString(String string) {
        // REGEX: that checks if a string does not contain " or ' or ; or -- or /*
        return string != null && !string.isBlank() && Pattern.matches("^[^\"';-]*$", string);
    }

    public static String addLog(String apiKey, double value) {

        StringBuilder builder = new StringBuilder("INSERT INTO " + Tables.TEMP.getTable()
                + " (nodeid, roomid, reading, date) VALUES (");
        builder.append("(SELECT id FROM " + Tables.NODE.getTable() + " WHERE "
                + Tables.NODE.getTable() + ".key = \"" + apiKey + "\"), ");
        builder.append("(SELECT roomid FROM " + Tables.NODE.getTable() + " WHERE "
                + Tables.NODE.getTable() + ".key = \"" + apiKey + "\"), ");
        builder.append(value + ", ");
        builder.append(System.currentTimeMillis() + ")");
        return builder.toString();
    }
}
