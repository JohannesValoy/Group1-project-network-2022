package no.ntnu.idata2304.group1.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import no.ntnu.idata2304.group1.data.Room;
import no.ntnu.idata2304.group1.data.Sensor;
import no.ntnu.idata2304.group1.data.SensorRecord;

/**
 * A class for converting SQL results to Java objects
 */
// TODO: Need to dedice if we want to use this or not. The optional thing would be fetching the data
// trough with the response class
public class SQLConverter {

    private SQLConverter() {}

    /**
     * Converts a ResultSet to series of Room objects within a map
     *
     * @param result The ResultSet to convert
     * @return A map containing the rooms
     * @throws RuntimeException If the ResultSet is invalid
     */
    public static List<Room> getRoomLogResults(ResultSet result) throws RuntimeException {
        HashMap<String, Room> roomLogs = new HashMap<>();
        if (result == null) {
            throw new IllegalArgumentException("Result can't be null");
        }
        try {
            while (result.next()) {
                Room room;

                String roomName = result.getString("name");
                if (!roomLogs.containsKey(roomName)) {
                    roomLogs.put(roomName, new Room(roomName));
                }
                room = roomLogs.get(roomName);

                Sensor s;

                String sensorName = result.getString("name");
                String sensorType = result.getString("type");
                s = room.findSensorByName(sensorName);
                if (s == null) {
                    s = new Sensor(Sensor.Types.getTypeByName(sensorType), sensorName);
                    room.addSensor(s);
                }
                s.addRecord(new SensorRecord(result.getTimestamp("timeStamp").toLocalDateTime(),
                        result.getFloat("reading")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while trying to fetch the information");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error while converting result to room logs");
        }

        return new ArrayList<>(roomLogs.values());
    }

    public static Room convertToRoom(ResultSet result) {
        Room room = null;
        try {
            while (result.next()) {
                if (room == null) {
                    room = new Room(result.getString("name"));
                }
                Sensor s;
                String sensorName = result.getString("name");
                String sensorType = result.getString("type");
                s = room.findSensorByName(sensorName);
                if (s == null) {
                    s = new Sensor(Sensor.Types.getTypeByName(sensorType), sensorName);
                    room.addSensor(s);
                }
                s.addRecord(new SensorRecord(result.getTimestamp("timeStamp").toLocalDateTime(),
                        result.getFloat("reading")));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error while trying to fetch the information");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error while converting result to room logs");
        }
        return room;
    }

    public static List<String> GetRoomsCommand(String sqlQuery) {
        return null;
    }

}
