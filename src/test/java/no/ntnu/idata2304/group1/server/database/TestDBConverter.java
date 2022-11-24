package no.ntnu.idata2304.group1.server.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.Room;

public class TestDBConverter {

    @Test
    public void testDBConverter() {
        ArrayList<String> rooms = new ArrayList<>();

        String sqlQuery = SQLCommandFactory.getTemperature(rooms.iterator());
        ResultSet testResults = null;
        try {
            DBConnector connector = SQLConnectorTest.getTestConnector();
            assertDoesNotThrow(() -> SQLConverter.getRoomLogResults(
                    connector.executeQuery(SQLCommandFactory.getTemperature(rooms.iterator()))));
        } catch (Exception e) {
            fail("The error lies in the factory or connector");
        }

    }

    @Test
    public void testDBConverterWithRooms() {
        ArrayList<String> rooms = new ArrayList<>();
        rooms.add("C220");
        String sqlQuery = SQLCommandFactory.getTemperature(rooms.iterator());
        ResultSet testResults = null;

        try {
            DBConnector connector = SQLConnectorTest.getTestConnector();
            List<Room> result = SQLConverter.getRoomLogResults(
                    connector.executeQuery(SQLCommandFactory.getTemperature(rooms.iterator())));
            assertEquals("C220", result.get(0).getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }
}
