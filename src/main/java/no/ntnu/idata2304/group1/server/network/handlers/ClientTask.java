package no.ntnu.idata2304.group1.server.network.handlers;

import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

/**
 * The type Client task.
 */
public class ClientTask implements Runnable {

    private ClientRunnable client;
    private boolean running;
    /**
     * Instantiates a new Client task.
     *
     * @param client the client
     */
    public ClientTask(ClientRunnable client) {
        this.client = client;
        running = false;
    }

    @Override
    public void run() {
        running = true;
        client.run();
        if (client.isClosed()) {
            ClientHandler.getInstance().removeClient(this);
        }
        running = false;
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public ClientRunnable getClient() {
        return client;
    }

    public boolean isRunning() {
        return running;
    }
}

