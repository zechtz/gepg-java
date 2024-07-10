package com.watabelabs.gepg.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

import javax.validation.ValidationException;

/**
 * Utility class for reading private keys from a keystore.
 * This class provides a method to retrieve a private key from a specified
 * keystore file.
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
            // Load the keystore file from the specified path
            FileInputStream fileInputStream = new FileInputStream(privateKeyPath);

            // Create a KeyStore instance based on the specified keystore type
            KeyStore keyStore = KeyStore.getInstance(keystoreType);

            // Load the keystore from the file input stream using the provided password
            keyStore.load(fileInputStream, privateKeyPassword.toCharArray());

            // Retrieve and return the private key using the alias and password
            return (PrivateKey) keyStore.getKey(privateKeyAlias, privateKeyPassword.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Error reading private key: " + e.getMessage(), e);
        }
    }
}
