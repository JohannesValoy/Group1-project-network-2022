package no.ntnu.idata2304.group1.server.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SQLConnectorTest {
    public static DBConnector getTestConnector() {
        return new DBConnector(
                "src/test/resources/no/ntnu/idata2304/group1/server/database/test.db");
    }

    @Test
    public void testSQLConnectorOnRightURL() {
        DBConnector connector = getTestConnector();
        assertEquals(connector.getPath(),
                "jdbc:sqlite:src/test/resources/no/ntnu/idata2304/group1/server/database/test.db");
    }

    @Test
    public void testSQLConnectionWithQuery() {
        DBConnector connector = getTestConnector();
        String query = "SELECT * FROM rooms";
        assertDoesNotThrow(() -> connector.executeQuery(query));
    }
}
