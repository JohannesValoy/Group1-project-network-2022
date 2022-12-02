package no.ntnu.idata2304.group1.server.network.handlers;

import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

/**
 * The type Client task.
 */
public class ClientTask implements Runnable {

    private final ClientRunnable client;
    private boolean running;

    /**
     * Instantiates a new Client task.
     *
     * @param client the client
     */
    public ClientTask(ClientRunnable client) {
        this.client = client;
        this.running = false;
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

    /**
     * Checks if the client is running
     *
     * @return True if the client is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Set as running.
     */
    public synchronized void setAsRunning() {
        this.running = true;
    }
}
