package no.ntnu.idata2304.group1.server.network;

import java.io.Closeable;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;

/**
 * Responsible for listening for new connections and creating new threads for each connection
 */
// TODO: Support for SSL
public class TCPListener extends Thread implements Closeable {
    private SSLServerSocket socket;

    private final static Logger LOGGER = System.getLogger(TCPListener.class.getName());

    /**
     * Creates a new TCP listener.
     * 
     * @param port The port to listen on
     * @param keyStorePath The path to the keystore
     * @param keyStorePassword The password to the keystore
     * @throws IOException if the socket fails to connect or the keystore is not found
     */
    public TCPListener(int port, String keyStoreName, String keyStorePassword) throws IOException {
        SSLContext context = SeverSSLKeyFactory.createSSLContext(keyStoreName, keyStorePassword);
        if (context == null) {
            throw new IOException("Could not create SSL context");
        }
        SSLServerSocketFactory factory = context.getServerSocketFactory();
        this.socket = (SSLServerSocket) factory.createServerSocket(port);
        this.socket.setEnabledCipherSuites(factory.getDefaultCipherSuites());
        String[] supported = this.socket.getSupportedProtocols();
        this.socket.setEnabledProtocols(supported);
    }

    @Override
    public void run() {
        while (true) {
            LogOutputer.print(MessageType.INFO, "Starting to listening for clients");
            SSLSocket client = null;
            try {
                client = (SSLSocket) socket.accept();
                client.startHandshake();
                LogOutputer.print(MessageType.INFO, "Client connected");
                new ClientThread(client).start();
            } catch (IOException e) {
                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e1) {
                        LOGGER.log(Level.ERROR,
                                "Error trying to close clientThread after failing to create", e1);
                    }
                }
                LogOutputer.print(MessageType.ERROR, "Error connecting to a client");
            }
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
