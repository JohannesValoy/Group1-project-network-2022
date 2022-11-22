package no.ntnu.idata2304.group1.server.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

public class SeverSSLKeyFactory {

    private static final Logger LOGGER = Logger.getLogger(SeverSSLKeyFactory.class.getName());

    private SeverSSLKeyFactory() {}

    /**
     * Creates a SSL context from the keystore This code is based on the example from this page:
     * https://gpotter2.github.io/tutos/en/sslsockets
     * 
     * @param keyStorePath
     * @param keyStorePassword
     * @return
     */
    // TODO: Maybe using pm1's solution insteaad of keystore?
    public static SSLContext createSSLContext(String keyStorePath, String keyStorePassword) {
        SSLContext ctx = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            try (InputStream kstore = new FileInputStream(keyStorePath)) {
                keyStore.load(kstore, keyStorePassword.toCharArray());
            }
            KeyManagerFactory kmf =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, "".toCharArray());
            ctx = SSLContext.getInstance("TLS");
            ctx.init(kmf.getKeyManagers(), null, SecureRandom.getInstanceStrong());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Could not create SSL context", e);
        }
        return ctx;
    }


    /**
     * Check that the file actually exists
     * 
     * @param path The path to the file
     * @return true if the file exists, false otherwise
     */
    // TODO: Implement a check for trying to fetch the server keystore
    public static boolean checkServerKeyExist(String path) {
        boolean returnValue = false;
        if (path != null && !path.isBlank()) {
            File file = new File(path);
            returnValue = file.exists();
        }
        return returnValue;
    }

    public static boolean testKeyStore(String string, String string2) {
        return false;
    }
}
