package no.ntnu.idata2304.group1.server.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLConverter {

    private SQLConverter() {}

    public static void getTempResult(ResultSet result) {
        HashMap<String, ArrayList<HashMap<String, Object>>> rooms = new HashMap<>();

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
