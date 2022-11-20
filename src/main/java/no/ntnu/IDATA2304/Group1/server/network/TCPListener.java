package no.ntnu.idata2304.group1.server.network;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import no.ntnu.idata2304.group1.server.messages.LogOutputer;
import no.ntnu.idata2304.group1.server.messages.LogOutputer.MessageType;

/**
 * Responsible for listening for new connections and creating new threads for each connection
 */
// TODO: Support for SSL

public class TCPListener extends Thread implements Closeable {
    private SSLServerSocket socket;

    /**
     * Creates a new TCP listener
     * 
     * @param port The port to listen on
     * @throws IOException if the socket fails to connect or the keystore is not found
     */
    public TCPListener(int port, String keyStoreName, String keyStorePassword) throws IOException {
        SSLContext context = createSSLContext(keyStoreName, keyStorePassword);
        if (context == null) {
            throw new IOException("Could not create SSL context");
        }
        SSLServerSocketFactory factory = context.getServerSocketFactory();
        this.socket = (SSLServerSocket) factory.createServerSocket(port);
        this.socket.setEnabledCipherSuites(factory.getDefaultCipherSuites());
        String[] supported = this.socket.getSupportedProtocols();
        this.socket.setEnabledProtocols(supported);
    }

    public TCPListener(int port) throws IOException {
        SSLContext context = createSSLContext("server.jks", "");
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
            try {
                SSLSocket client = (SSLSocket) socket.accept();
                client.startHandshake();
                LogOutputer.print(MessageType.INFO, "Client connected");
                new ClientThread(client).start();
            } catch (IOException e) {
                LogOutputer.print(MessageType.ERROR, "Error connecting to a client");
            }
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    /**
     * Creates a SSL context from the keystore This code is based on the example from this page:
     * https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html#X509TrustManager
     * 
     * @param keyStorePath
     * @param keyStorePassword
     * @return
     */
    private SSLContext createSSLContext(String keyStorePath, String keyStorePassword) {
        SSLContext ctx = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream kstore = TCPListener.class.getResourceAsStream("/" + keyStorePath);
            keyStore.load(kstore, keyStorePassword.toCharArray());
            KeyManagerFactory kmf =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keyStorePassword.toCharArray());
            ctx = SSLContext.getInstance("TLS");
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(),
                    SecureRandom.getInstanceStrong());
        } catch (Exception e) {

        }
        return ctx;
    }
}
