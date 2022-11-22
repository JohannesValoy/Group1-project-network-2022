package no.ntnu.idata2304.group1.server.database;

import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

public class SQLTasks {
    private String query;
    private ClientRunnable client;

    public SQLTasks(String query, ClientRunnable client) {
        if (query == null || client == null) {
            throw new IllegalArgumentException("Query or client cannot be null");
        }
        this.query = query;
        this.client = client;
    }

    public String getQuery() {
        return query;
    }

    public ClientRunnable getClient() {
        return client;
    }
}
