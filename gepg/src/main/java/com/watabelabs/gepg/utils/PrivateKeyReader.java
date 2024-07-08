package com.watabelabs.gepg.utils;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;

import javax.validation.ValidationException;

/**
 * The {@code PrivateKeyReader} class provides utility methods for reading
 * private keys
 * from a keystore file. It supports various keystore types and allows for easy
 * retrieval
 * of private keys using their aliases and passwords.
 */
public class PrivateKeyReader {

    /**
     * Retrieves the private key from the specified keystore file.
     *
     * @param privateKeyPath     the path to the keystore file containing the
     *                           private key
     * @param keystoreType       the type of the keystore (e.g., "PKCS12", "JKS")
     * @param privateKeyPassword the password for the keystore and the private key
     * @param privateKeyAlias    the alias of the private key in the keystore
     * @return the {@link PrivateKey} object
     * @throws ValidationException if any error occurs during the process of reading
     *                             the private key
     */
    public static PrivateKey get(String privateKeyPath, String keystoreType, String privateKeyPassword,
            String privateKeyAlias) {
        try {
            // Read the keystore file bytes
            byte[] certStoreBytes = Files.readAllBytes(Paths.get(privateKeyPath));
            // Create a KeyStore instance based on the specified keystore type
            KeyStore keyStore = KeyStore.getInstance(keystoreType);
            // Load the keystore from the byte array input stream
            keyStore.load(new ByteArrayInputStream(certStoreBytes), privateKeyPassword.toCharArray());

            // Retrieve and return the private key using the alias and password
            return (PrivateKey) keyStore.getKey(privateKeyAlias, privateKeyPassword.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(e.getLocalizedMessage());
        }
    }
}

