package no.ntnu.idata2304.group1.server.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import no.ntnu.idata2304.group1.data.RoomRecord;

/**
 * A class for converting SQL results to Java objects
 */
@Deprecated
// TODO: Need to dedice if we want to use this or not. The optional thing would be fetching the data
// trough with the response class
public class SQLConverter {

    private SQLConverter() {}

    public static void getTempResult(ResultSet result) {
        HashMap<String, ArrayList<RoomRecord>> temp = new HashMap<>();


        if (result == null) {
            throw new IllegalArgumentException("Result can't be null");
        }
        try {
            while (result.next()) {
                String room = result.getString("name");
                if (!rooms.containsKey(room)) {
                    rooms.put(room, new ArrayList<>());
                }
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("time", result.getTimestamp("time"));
                temp.put("temp", result.getDouble("temp"));
                rooms.get(room).add(temp);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
