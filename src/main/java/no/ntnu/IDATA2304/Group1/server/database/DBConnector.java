package no.ntnu.idata2304.group1.server.database;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class for connecting to the database
 */
public class DBConnector implements Closeable {

    private Connection conn;
    private String uri;

    /**
     * Creates a new database connector with the default database
     */
    public DBConnector() {
        String path;
        try {
            path = getClass().getResource("Data.db").toString();
        } catch (NullPointerException e) {
            path = getClass().getResource("").toString() + "data.db";
        }
        this.uri = "jdbc:sqlite:" + path.toString().replace("%20", " ");
        try {
            this.conn = DriverManager.getConnection(uri);
            setup();
        } catch (SQLException e) {
            throw new IllegalAccessError("Seems like there is a error");
        }

    }

    /**
     * Creates a new database connector with a given database
     * 
     * @param path the path for the database
     */
    public DBConnector(String path) {
        this.uri = "jdbc:sqlite:" + path.toString().replace("%20", " ");
        try {
            this.conn = DriverManager.getConnection(uri);
            setup();
        } catch (SQLException e) {
            throw new IllegalAccessError("Seems like there is a error");
        }

    }

    /**
     * Creates the tables if not already created
     * 
     * @throws SQLException if the tables could not be created
     */
    private void setup() throws SQLException {
        String roomSQL = "CREATE TABLE IF NOT EXISTS rooms (" + "id integer PRIMARY KEY,"
                + "name text NOT NULL" + ")";
        String nodeSQL = "CREATE TABLE IF NOT EXISTS nodes (" + "id integer PRIMARY KEY,"
                + "name text," + "key text," + "roomid integer" + ")";
        String data = "CREATE TABLE IF NOT EXISTS logs (\n" + "roomID integer," + "temp integer,"
                + "date DateTime" + ")";
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
        try (Statement statement = conn.createStatement()) {
            return statement.executeQuery(sqlStatement);
        }
    }

    /**
     * Gets the connection to the database
     * 
     * @return the connection to the database
     */
    public String getPath() {
        return uri;
    }

    @Override
    public void close() throws IOException {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
