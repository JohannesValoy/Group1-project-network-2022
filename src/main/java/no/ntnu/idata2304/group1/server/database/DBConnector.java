package no.ntnu.idata2304.group1.server.database;

import java.io.Closeable;
import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class for connecting to the database
 */
public class DBConnector implements Closeable {

    private static final String DBDRIVE = "jdbc:sqlite:";
    private static final Logger LOGGER = Logger.getLogger(DBConnector.class.getName());
    private Connection conn;
    private String uri;
    private boolean busy = false;

    /**
     * Creates a new database connector with the default database
     *
     * @param setup If true, the database will run a setup script
     * @throws SQLException
     */
    public DBConnector(boolean setup) {
        String path;
        try {
            path = getClass().getResource("Data.db").toString();
        } catch (NullPointerException e) {
            path = getClass().getResource("").toString() + "data.db";
        }
        try {
            this.uri = DBDRIVE + path.replace("%20", " ");
            this.conn = DriverManager.getConnection(uri);
            if (setup) {
                LOGGER.info("Running setup script");
                setup();
            }

        } catch (Exception e) {
            LOGGER.severe("Could not connect to the database");
            throw new RuntimeException("Could not connect to database");
        }


    }

    /**
     * Creates a new database connector with a given database
     *
     * @param path  the path for the database
     * @param setup Boolean for setup
     */
    public DBConnector(String path, boolean setup) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("The path cannot be null or empty");
        }
        this.uri = DBDRIVE + path.replace("%20", " ");
        try {
            this.conn = DriverManager.getConnection(uri);
            if (setup) {
                setup();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not connect to database");
        }

    }

    /**
     * Creates the tables if not already created
     * 
     * @throws SQLException if the tables could not be created
     */
    private void setup() throws SQLException {
        String roomSQL = "CREATE TABLE IF NOT EXISTS rooms (" + "ID integer PRIMARY KEY,"
                + "RoomName text NOT NULL" + " )";
        String nodeSQL = "CREATE TABLE IF NOT EXISTS nodes (" + "ID integer PRIMARY KEY,"
                + "NodeName text," + "key text UNIQUE," + "roomID integer REFERENCES rooms(ID),"
                + "type String NOT NULL," + "CHECK(type IN (\"Temperature\",\"Humidty\"))" + ")";
        String data = "CREATE TABLE IF NOT EXISTS logs (\n" + "ID integer PRIMARY KEY,"
                + "roomID integer REFERENCES rooms(ID)," + "reading float NOT NULL,"
                + "timeStamp DateTime NOT NULL," + "nodeID integer REFERENCES NODES(ID)" + ")";
        execute(roomSQL);
        execute(nodeSQL);
        execute(data);
    }

    /**
     * Executes a SQL statement
     * 
     * @param sqlStatement the SQL statement to execute
     * @throws SQLException if the statement could not be executed
     */
    private synchronized void execute(String sqlStatement) throws SQLException {
        busy = true;
        if (sqlStatement == null || sqlStatement.isEmpty()) {
            throw new IllegalArgumentException("The SQL statement cannot be null or empty");
        }
        try (Statement statement = conn.createStatement()) {
            statement.execute(sqlStatement);
        }
        busy = false;
    }

    /**
     * Executes a SQL query and returns the result
     *
     * @param sqlStatement the SQL statement to execute
     * @return the result of the query or null if the query did not return anything
     * @throws SQLException if the query could not be executed
     */
    public synchronized ResultSet executeQuery(String sqlStatement) throws SQLException {
        if (sqlStatement == null || sqlStatement.isEmpty()) {
            throw new IllegalArgumentException("The SQL statement cannot be null or empty");
        }
        ResultSet result = null;
        try (Statement stmt = conn.createStatement()) {
            result = stmt.executeQuery(sqlStatement);
        } catch (SQLException e) {
            if (!e.getMessage().equals("query does not return ResultSet")) {
                busy = false;
                throw e;
            } else {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sqlStatement);
                }
                busy = false;
            }
        }
        busy = false;
        return result;

    }


    /**
     * Creates a prepared statement that can be used
     *
     * @param query the query
     * @return prepared statement
     * @throws SQLException the sql exception
     */
    public synchronized PreparedStatement prepareStatement(String query) throws SQLException {
        return conn.prepareStatement(query);
    }


    /**
     * Gets the connection to the database
     *
     * @return the connection to the database
     */
    public String getPath() {
        return uri;
    }

    /**
     * Checks if the connector is busy
     *
     * @return boolean true if busy, false otherwise
     */
    public boolean isBusy() {
        return busy;
    }

    @Override
    public void close() {
        setFree();
    }

    /**
     * Sets the Connector to busy. This is used to prevent the connector from being closed and used
     * while a query is being executed.
     */
    public void setBusy() {
        busy = true;
    }

    /**
     * Sets free.
     */
    public void setFree() {
        busy = false;
    }
}
