package no.ntnu.idata2304.group1.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

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
        try {
            this.conn = DriverManager.getConnection(uri);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (this.conn != null) {
                setup(exist);
            }
        }
    }

    private void setup(Boolean exist) throws SQLException {
        String roomSQL = "CREATE TABLE IF NOT EXISTS rooms (" + "id integer PRIMARY KEY,"
                + "name text NOT NULL" + ")";
        String nodeSQL = "CREATE TABLE IF NOT EXISTS nodes (" + "id integer PRIMARY KEY,"
                + "name text," + "key text," + "roomid integer" + ")";
        String data = "CREATE TABLE IF NOT EXISTS nodes (\n" + "roomID integer," + "temp integer,"
                + "date DateTime" + ")";
        execute(roomSQL);
        execute(nodeSQL);
        execute(data);
    }

    public synchronized void execute(String sqlStatement) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute(sqlStatement);
    }
}
