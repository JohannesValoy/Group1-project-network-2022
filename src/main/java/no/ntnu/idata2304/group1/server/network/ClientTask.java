package no.ntnu.idata2304.group1.server.network;

import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

/**
 * The type Client task.
 */
public class ClientTask implements Runnable {

    private ClientRunnable client;

    /**
     * Instantiates a new Client task.
     *
     * @param client the client
     */
    public ClientTask(ClientRunnable client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.run();
        if (client.isClosed()) {
            ClientHandler.getInstance().removeClient(client);
        }
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public ClientRunnable getClient() {
        return client;
    }
}

