package no.ntnu.idata2304.group1.server.database;

import static org.junit.jupiter.api.Assertions.fail;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.server.database.DBConnectorPool;

public class DBConnectorPoolTest {

    @Test
    public void testPool() {
        DBConnectorPool pool = DBConnectorPool.getInstance();
        ResultSet result = null;
        try {
            result = pool.executeQuery("SELECT * FROM rooms");
        } catch (Exception e) {
            fail("Could not execute query: " + e.getMessage());
        }
    }

    @Test
    public void testPoolWithCustomPath() {
        DBConnectorPool pool = DBConnectorPool.getInstance();
        ResultSet result = null;
        try {
            result = pool.executeQuery("SELECT * FROM rooms WHERE id = 1");
        } catch (Exception e) {
            fail("Could not execute query: " + e.getMessage());
        }
    }
}
