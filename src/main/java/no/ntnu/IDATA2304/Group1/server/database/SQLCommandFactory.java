package no.ntnu.idata2304.group1.server.database;

import java.util.Date;
import java.util.Iterator;

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
}
