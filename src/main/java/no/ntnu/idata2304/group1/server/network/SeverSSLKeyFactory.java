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
    // TODO: Add support for a pk1 file with another password on the key then the keystore

    public static SSLContext createSSLContext(String keyStorePath, String keyStorePassword) {
        SSLContext ctx = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("pkcs12");
            try (InputStream kstore = new FileInputStream(keyStorePath)) {
                keyStore.load(kstore, keyStorePassword.toCharArray());
            }
            KeyManagerFactory kmf =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, keyStorePassword.toCharArray());
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
    public static boolean checkServerKeyExist(String path) {
        boolean returnValue = false;
        if (path != null && !path.isBlank()) {
            File file = new File(path);
            returnValue = file.exists();
        }
        return returnValue;
    }

    /**
     * Check that the file exists and can be loaded to a keystore
     * 
     * @param keyStorePath The path to the file
     * @param keyStorePassword The password to the file
     * @return true if the file exists and can be loaded, false otherwise
     */
    public static boolean testKeyStore(String keyStorePath, String keyStorePassword) {
        boolean successful = false;
        if (checkServerKeyExist(keyStorePath)) {
            try {
                KeyStore keyStore = KeyStore.getInstance("pkcs12");
                try (InputStream kstore = new FileInputStream(keyStorePath)) {
                    keyStore.load(kstore, keyStorePassword.toCharArray());
                }
                successful = true;
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "This is not a valid keystore", e);
            }
        }
        return successful;
    }
}
