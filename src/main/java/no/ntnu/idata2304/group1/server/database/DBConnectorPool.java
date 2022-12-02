package no.ntnu.idata2304.group1.server.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Db connector pool.
 */
public class DBConnectorPool {

    /**
     * The Pool.
     */
    DBConnector[] pool;
    private static final int POOLSIZE = 10;
    /**
     * The Current size.
     */
    int currentSize;

    private static DBConnectorPool instance;

    private DBConnectorPool(String path) {
        this.currentSize = 0;
        this.pool = new DBConnector[POOLSIZE];
        if (path == null) {
            pool[0] = new DBConnector(true);
            for (int i = 1; i < POOLSIZE; i++) {
                pool[i] = new DBConnector(false);
            }
        } else {
            pool[0] = new DBConnector(path, true);
            for (int i = 1; i < POOLSIZE; i++) {
                pool[i] = new DBConnector(path, false);
            }
        }

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DBConnectorPool getInstance() {
        if (instance == null) {
            instance = new DBConnectorPool(null);
        }
        return instance;
    }

    /**
     * Gets instance.
     *
     * @param path the path
     * @return the instance
     */
    public static DBConnectorPool getInstance(String path) {
        if (instance == null) {
            instance = new DBConnectorPool(path);
        }
        return instance;
    }

    /**
     * Execute query result set.
     *
     * @param query the query
     * @return the result set
     * @throws SQLException the sql exception
     */
    public ResultSet executeQuery(String query) throws SQLException {
        if (query == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        DBConnector connector = null;
        while (connector == null) {
            connector = getConnector();
            if (connector == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return connector.executeQuery(query);
    }

    /**
     * Gets connector.
     *
     * @return the connector
     */
    public synchronized DBConnector getConnector() {
        DBConnector connector = null;
        while (connector == null) {
            for (int i = 0; i < POOLSIZE && connector == null; i++) {
                if (!pool[i].isBusy()) {
                    pool[i].setBusy();
                    connector = pool[i];
                }
            }
        }
        return connector;
    }

    /**
     * Execute.
     *
     * @param sqlQuery the sql query
     */
    public void execute(String sqlQuery) {

    }

}
