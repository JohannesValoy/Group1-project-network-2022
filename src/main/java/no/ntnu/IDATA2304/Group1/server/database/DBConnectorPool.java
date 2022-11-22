package no.ntnu.idata2304.group1.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectorPool {

    DBConnector[] pool;
    private static final int POOLSIZE = 10;
    int currentSize;

    private static DBConnectorPool instance;

    private DBConnectorPool() {
        this.currentSize = 0;
        this.pool = new DBConnector[POOLSIZE];
        pool[0] = new DBConnector(true);
        for (int i = 1; i < POOLSIZE; i++) {
            pool[i] = new DBConnector(false);
        }
    }

    public static DBConnectorPool getInstance() {
        if (instance == null) {
            instance = new DBConnectorPool();
        }
        return instance;
    }

    public ResultSet executeQuery(String query) throws SQLException, InterruptedException {
        if (query == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        DBConnector connector = null;
        while (connector == null) {
            connector = getConnector();
            if (connector == null) {
                Thread.sleep(100);
            }
        }
        return connector.executeQuery(query);
    }

    private DBConnector getConnector() {
        DBConnector connector = null;
        for (int i = 0; i < POOLSIZE; i++) {
            if (!pool[i].isBusy()) {
                pool[i].setBusy(true);
                connector = pool[i];
            }
        }
        return connector;
    }

}
