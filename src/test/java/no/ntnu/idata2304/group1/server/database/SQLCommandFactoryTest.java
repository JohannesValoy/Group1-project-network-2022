package no.ntnu.idata2304.group1.server.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.data.Sensor.Types;

public class SQLCommandFactoryTest {

    @Test
    public void testSQLCommandFactory() {
        ArrayList<String> rooms = new ArrayList<>();
        rooms.add("C220");
        Iterator<String> iterator = rooms.iterator();


        assertDoesNotThrow(
                () -> SQLCommandFactory.getRoomData(iterator, 10, null, null, Types.TEMPERATURE));
    }

    @Test
    public void testSQLCommandFactoryNoRooms() {
        ArrayList<String> rooms = new ArrayList<>();
        Iterator<String> iterator = rooms.iterator();
        assertDoesNotThrow(
                () -> SQLCommandFactory.getRoomData(iterator, 10, null, null, Types.TEMPERATURE));
    }

    @Test
    public void getRooms() {
        assertDoesNotThrow(() -> SQLCommandFactory.getRooms(null));
    }
}
