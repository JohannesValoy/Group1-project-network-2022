package no.ntnu.idata2304.group1.server.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

/**
 * The type Sql connector test.
 */
public class SQLConnectorTest {
    /**
     * Gets test connector.
     *
     * @return the test connector
     * @throws SQLException the sql exception
     */
    public static DBConnector getTestConnector() throws SQLException {
        return new DBConnector(
                "src/test/resources/no/ntnu/idata2304/group1/server/database/test.db", true);
    }

    /**
     * Test sql connector on right url.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testSQLConnectorOnRightURL() throws SQLException {
        try (DBConnector connector = getTestConnector()) {
            assertEquals(
                    "jdbc:sqlite:src/test/resources/no/ntnu/idata2304/group1/server/database/test.db",
                    connector.getPath());
        }


    }

    /**
     * Test sql connection with query.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void testSQLConnectionWithQuery() throws SQLException {
        DBConnector connector = getTestConnector();
        String query = "SELECT * FROM rooms";
        assertDoesNotThrow(() -> connector.executeQuery(query));
        assertTrue(connector.executeQuery(query).isBeforeFirst());
    }

}
