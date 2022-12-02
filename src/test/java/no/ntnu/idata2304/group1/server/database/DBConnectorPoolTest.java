package no.ntnu.idata2304.group1.server.database;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;

/**
 * The type Db connector pool test.
 */
public class DBConnectorPoolTest {

    /**
     * Test pool.
     */
    @Test
    public void testPool() {
        DBConnectorPool pool = DBConnectorPool.getInstance();
        ResultSet result = null;
        try {
            result = pool.executeQuery("SELECT * FROM rooms");
            assertTrue(result.next());
        } catch (Exception e) {
            fail("Could not execute query: " + e.getMessage());
        }
    }

    /**
     * Test pool with custom path.
     */
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
