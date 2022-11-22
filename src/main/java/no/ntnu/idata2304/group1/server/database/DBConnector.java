package no.ntnu.idata2304.group1.server.database;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class for connecting to the database
 */
public class DBConnector implements Closeable {

    private static final String dbDrive = "jdbc:sqlite:";
    private final java.util.logging.Logger Logger =
            java.util.logging.Logger.getLogger(DBConnector.class.getName());
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
            this.uri = dbDrive + path.replace("%20", " ");
            this.conn = DriverManager.getConnection(uri);
            if (setup) {
                setup();
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not connect to database");
        }


    }

    /**
     * Creates a new database connector with a given database
     * 
     * @param path the path for the database
     */
    public DBConnector(String path, boolean setup) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("The path cannot be null or empty");
        }
        this.uri = dbDrive + path.replace("%20", " ");
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
                + "name text NOT NULL," + "roomNumber integer NOT NULL" + " )";
        String nodeSQL =
                "CREATE TABLE IF NOT EXISTS nodes (" + "ID integer PRIMARY KEY," + "name text,"
                        + "key text UNIQUE," + "roomID integer," + "type String NOT NULL" + ")";
        String data = "CREATE TABLE IF NOT EXISTS logs (\n" + "roomID integer NOT NULL,"
                + "reading float NOT NULL ," + "timeStamp DateTime NOT NULL ,"
                + "nodeID integer NOT NULL"
                + ", FOREIGN KEY (roomID) REFERENCES rooms(ID), CHECK(timeStamp > DATE('now')))";
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
    public synchronized void execute(String sqlStatement) throws SQLException {
        if (sqlStatement == null || sqlStatement.isEmpty()) {
            throw new IllegalArgumentException("The SQL statement cannot be null or empty");
        }
        try (Statement statement = conn.createStatement()) {
            statement.execute(sqlStatement);
        }
    }

    /**
     * Executes a SQL query and returns the result
     * 
     * @param sqlStatement the SQL statement to execute
     * @return the result of the query
     * @throws SQLException if the query could not be executed
     */
    public synchronized ResultSet executeQuery(String sqlStatement) throws SQLException {
        if (sqlStatement == null || sqlStatement.isEmpty()) {
            throw new IllegalArgumentException("The SQL statement cannot be null or empty");
        }
        ResultSet result = null;
        try (Statement statement = conn.createStatement()) {
            result = statement.executeQuery(sqlStatement);
        } catch (Exception e) {
            busy = false;
            throw e;
        }
        busy = false;
        return result;
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
        try {
            conn.close();
        } catch (SQLException e) {

        }
    }

    /**
     * Sets the Connector to busy. This is used to prevent the connector from being closed and used
     * while a query is being executed.
     * 
     * @param b boolean true if busy, false otherwise
     */
    public void setBusy() {
        busy = true;
    }
}
