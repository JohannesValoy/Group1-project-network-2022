package no.ntnu.idata2304.group1.server.network.handlers;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import no.ntnu.idata2304.group1.server.network.clients.ClientRunnable;

/**
 * This class is responsible for handling all the clients.
 * 
 * @author Mathias J. Kirkeby
 */
public class ClientHandler extends Thread {
    private static ClientHandler instance = null;
    private static final int TOTALTHREADS = 10;
    private ArrayList<ClientRunnable> clients;
    private ExecutorService pool;

    private ClientHandler() {
        this.setName("Client Handler");
        this.clients = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(TOTALTHREADS);
    }

    /**
     * Creates a new ClientHandler and starts it
     * 
     * @return The new ClientHandler
     */
    public static ClientHandler getInstance() {
        if (instance == null) {
            instance = new ClientHandler();

            instance.start();
        }
        return instance;
    }

    /**
     * Adds a new client to the list of clients
     * 
     * @param client The client to add
     */
    public synchronized void addClient(ClientRunnable client) {
        if (client != null && !clients.contains(client)) {
            clients.add(client);
        }
    }

    /**
     * Removes a client from the list of clients
     * 
     * @param client The client to remove
     */
    public synchronized void removeClient(ClientRunnable client) {
        if (client != null && clients.contains(client)) {
            clients.remove(client);
        }
    }

    @Override
    public void run() {
        while (true) {
            // While clone complains, we already know that this is a ArrayList.
            ArrayList<ClientRunnable> clients = (ArrayList<ClientRunnable>) this.clients.clone();
            for (ClientRunnable client : clients) {
                if (!client.isRunning()) {
                    client.setAsRunning();
                    ClientTask task = new ClientTask(client);
                    pool.execute(task);
                }
            }
        }
    }
}

