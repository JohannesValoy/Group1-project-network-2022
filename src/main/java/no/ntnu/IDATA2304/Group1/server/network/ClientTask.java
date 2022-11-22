package no.ntnu.idata2304.group1.server.network;

import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

public class ClientTask implements Runnable {

    private ClientRunnable client;

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

    public ClientRunnable getClient() {
        return client;
    }
}

