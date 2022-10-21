package no.ntnu.idata2304.group1.server.database;

import java.io.Closeable;
import java.io.IOException;
import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector implements Closeable {

    private Connection conn;

    public DBConnector() throws SQLException {
        String path;
        boolean exist = true;
        try {
            path = getClass().getResource("Data.db").toString();
        } catch (NullPointerException e) {
            path = getClass().getResource("").toString() + "data.db";
        }
        String uri = "jdbc:sqlite:" + path.toString().replace("%20", " ");
        this.conn = DriverManager.getConnection(uri);
        setup(exist);
    }

    private void setup(Boolean exist) throws SQLException {
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
        Statement statement = conn.createStatement();
        statement.execute(sqlStatement);
    }

    // TODO: Implement a better exception system
    public synchronized ResultSet executeQuery(String sqlStatement) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute(sqlStatement);
        return statement.getResultSet();
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
