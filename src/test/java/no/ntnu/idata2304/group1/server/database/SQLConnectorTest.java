package no.ntnu.idata2304.group1.server.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import no.ntnu.idata2304.group1.server.database.DBConnector;

public class SQLConnectorTest {
    public static DBConnector getTestConnector() throws SQLException {
        return new DBConnector(
                "src/test/resources/no/ntnu/idata2304/group1/server/database/test.db", true);
    }

    @Test
    public void testSQLConnectorOnRightURL() throws SQLException {
        try (DBConnector connector = getTestConnector()) {
            assertEquals(
                    "jdbc:sqlite:src/test/resources/no/ntnu/idata2304/group1/server/database/test.db",
                    connector.getPath());
        }


    }

    @Test
    public void testSQLConnectionWithQuery() throws SQLException {
        DBConnector connector = getTestConnector();
        String query = "SELECT * FROM rooms";
        assertDoesNotThrow(() -> connector.executeQuery(query));
    }

}
