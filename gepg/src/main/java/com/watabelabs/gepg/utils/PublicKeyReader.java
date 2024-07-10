package com.watabelabs.gepg.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for reading public keys from a keystore.
 * This class provides a method to retrieve a public key from a specified
 * keystore file.
 */
public class PublicKeyReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicKeyReader.class);

    /**
     * Retrieves the public key from the specified keystore file.
     *
     * @param publicKeyPath     the path to the keystore file containing the public
     *                          key
     * @param keystoreType      the type of the keystore (e.g., "PKCS12", "JKS")
     * @param publicKeyPassword the password for the keystore
     * @param publicKeyAlias    the alias of the public key in the keystore
     * @return the {@link PublicKey} object
     * @throws ValidationException if any error occurs during the process of reading
     *                             the public key
     */
    public static PublicKey get(String publicKeyPath, String keystoreType, String publicKeyPassword,
            String publicKeyAlias) {
        try {
            // Create a KeyStore instance based on the specified keystore type
            KeyStore keyStore = KeyStore.getInstance(keystoreType);

            // Load the keystore file from the specified path
            FileInputStream fileInputStream = new FileInputStream(publicKeyPath);

            // Load the keystore from the file input stream using the provided password
            keyStore.load(fileInputStream, publicKeyPassword.toCharArray());

            // Check if the alias exists in the KeyStore
            if (!keyStore.containsAlias(publicKeyAlias)) {
                throw new ValidationException("Alias '" + publicKeyAlias + "' does not exist in the KeyStore.");
            }

            // Get the certificate from the keystore using the provided alias
            Certificate certificate = keyStore.getCertificate(publicKeyAlias);
            if (certificate == null) {
                throw new ValidationException("Certificate does not exist for alias '" + publicKeyAlias + "'.");
            }

            // Return the public key from the certificate
            return certificate.getPublicKey();

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            // Log the exception and rethrow it as a ValidationException
            LOGGER.error("Error reading public key: {}", e.getMessage(), e);
            throw new ValidationException("Error reading public key: " + e.getMessage(), e);
        }
    }
}
