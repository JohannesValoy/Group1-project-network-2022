package no.ntnu.idata2304.group1.server.database;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector implements Closeable {

    private Connection conn;
    private String uri;

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

    public DBConnector(String path) {
        this.uri = "jdbc:sqlite:" + path.toString().replace("%20", " ");
        try {
            this.conn = DriverManager.getConnection(uri);
            setup();
        } catch (SQLException e) {
            throw new IllegalAccessError("Seems like there is a error");
        }

    }


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

    public synchronized void execute(String sqlStatement) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(sqlStatement);
        }
    }

    // TODO: Implement a better exception system
    public synchronized ResultSet executeQuery(String sqlStatement) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            return statement.executeQuery(sqlStatement);
        }
    }

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
