package no.ntnu.idata2304.group1.server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.Sensor;

/**
 * A class for creating SQL commands for different requests
 */
public class SQLCommandFactory {

    private final static Logger LOGGER = Logger.getLogger(SQLCommandFactory.class.getName());

    private final static String SELECT = "SELECT * FROM ";

    private final static String GETROOMDATA = "SELECT * FROM " + Tables.TEMP.getTable()
            + " INNER JOIN " + Tables.ROOMS.getTable() + " ON " + Tables.ROOMS.getTable() + ".id = "
            + Tables.TEMP.getTable() + ".roomid" + " INNER JOIN " + Tables.NODE.getTable() + " ON "
            + Tables.NODE.getTable() + ".id = " + Tables.TEMP.getTable() + ".nodeid AND "
            + Tables.NODE.getTable() + ".type LIKE ? WHERE rooms.RoomName LIKE ?";

    /**
     * Enum for linking the different kind of tables in the database
     */
    private enum Tables {
        /**
         * Node tables.
         */
        NODE("nodes"),
        /**
         * Rooms tables.
         */
        ROOMS("rooms"),
        /**
         * Temp tables.
         */
        TEMP("logs"),
        /**
         * Users tables.
         */
        USERS("users");

        private String table;

        Tables(String tableName) {
            this.table = tableName;
        }

        /**
         * Gets table.
         *
         * @return the table
         */
        public String getTable() {
            return table;
        }
    }


    private SQLCommandFactory() {};


    public static List<Room> getRoomData(Iterator<String> rooms, int limit, Date from, Date to,
            Sensor.Types type) throws SQLException {


        ArrayList<Room> roomList = new ArrayList<>();
        StringBuilder builder = new StringBuilder(GETROOMDATA);
        if (from != null && to != null) {
            builder.append(
                    " AND " + Tables.TEMP.getTable() + ".timestamp BETWEEN " + from + " AND " + to);
        } else if (from != null) {
            builder.append(" AND " + Tables.TEMP.getTable() + ".timestamp > " + from);
        } else if (to != null) {
            builder.append(" AND " + Tables.TEMP.getTable() + ".timestamp < " + to);
        }
        builder.append(" LIMIT " + limit);
        try (DBConnector connector = DBConnectorPool.getInstance().getConnector();
                PreparedStatement statement = connector.prepareStatement(builder.toString())) {
            statement.setString(1, type.getName());
            // See if I can do this better
            if (!rooms.hasNext()) {
                rooms = getRooms(null).iterator();
            }
            while (rooms.hasNext()) {
                String room = rooms.next();
                statement.setString(2, room);
                if (room == null) {
                    throw new IllegalArgumentException("The rooms cannot contain null");
                }
                ResultSet result = statement.executeQuery();
                Room roomData = SQLConverter.convertToRoom(result);
                if (roomData != null) {
                    roomList.add(roomData);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Could not get room data: " + e.getMessage());
            throw e;
        }

        return roomList;
    }

    /**
     * Check node key string.
     *
     * @param key the key
     * @return the string
     */
    public static String checkNodeKey(String key) {
        if (checkValidString(key)) {
            throw new IllegalArgumentException("The key is invalid");
        }
        return SELECT + Tables.NODE.getTable() + " WHERE " + Tables.NODE.getTable() + ".key LIKE \""
                + key + "\"";
    }

    private static boolean checkValidString(String string) {
        // REGEX: that checks if a string does not contain " or ' or ; or -- or /*
        return string != null && !string.isBlank() && !Pattern.matches("^[^\"';-]*$", string);
    }

    /**
     * Add log string.
     *
     * @param apiKey the api key
     * @param value the value
     * @return the string
     */
    public static String addLog(String apiKey, double value) throws IllegalArgumentException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder builder = new StringBuilder("INSERT INTO " + Tables.TEMP.getTable()
                + " (nodeid, roomid, reading, timeStamp) VALUES (");
        builder.append("(SELECT id FROM " + Tables.NODE.getTable() + " WHERE "
                + Tables.NODE.getTable() + ".key = \"" + apiKey + "\"), ");
        builder.append("(SELECT roomid FROM " + Tables.NODE.getTable() + " WHERE "
                + Tables.NODE.getTable() + ".key = \"" + apiKey + "\"), ");
        builder.append(value + ", ");
        builder.append("\"" + format.format(new Date()) + "\")");
        return builder.toString();
    }

    public static List<String> getRooms(String filter) throws SQLException {
        String sqlQuery = SELECT + Tables.ROOMS.getTable();
        List<String> rooms = null;
        if (filter != null) {
            sqlQuery += " WHERE " + Tables.ROOMS.getTable() + ".roomName LIKE \"" + filter + "\"";
        }
        try (DBConnector connector = DBConnectorPool.getInstance().getConnector();
                PreparedStatement statement = connector.prepareStatement(sqlQuery)) {
            ResultSet result = statement.executeQuery();
            rooms = SQLConverter.getRoomsName(result);
        } catch (SQLException e) {
            LOGGER.severe("Could not get rooms: " + e.getMessage());
            throw e;
        }

        return rooms;
    }
}
