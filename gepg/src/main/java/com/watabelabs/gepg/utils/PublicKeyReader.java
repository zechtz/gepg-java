package com.watabelabs.gepg.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code PublicKeyReader} class provides utility methods for reading public
 * keys
 * from a keystore file. It supports various keystore types and allows for easy
 * retrieval
 * of public keys using their aliases and passwords.
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
            KeyStore keystore = KeyStore.getInstance(keystoreType);
            // Load the keystore from the file input stream
            keystore.load(new FileInputStream(publicKeyPath), publicKeyPassword.toCharArray());

            // Check if the alias exists in the KeyStore
            if (!keystore.containsAlias(publicKeyAlias)) {
                throw new ValidationException("Alias '" + publicKeyAlias + "' does not exist in the KeyStore.");
            }

            // Get the certificate from the keystore using the provided alias
            X509Certificate certificate = (X509Certificate) keystore.getCertificate(publicKeyAlias);

            if (certificate == null) {
                throw new ValidationException("Certificate does not exist for alias '" + publicKeyAlias + "'.");
            }

            // Log the found public key
            // LOGGER.info("THE_CERTIFICATE_FOUND_WITH_PUBLIC_KEY:{}",
            // certificate.getPublicKey());
            return certificate.getPublicKey();
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new ValidationException("KeyStore error: " + e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ValidationException("Algorithm not found: " + e.getLocalizedMessage());
        } catch (CertificateException e) {
            e.printStackTrace();
            throw new ValidationException("Certificate error: " + e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ValidationException("I/O error: " + e.getLocalizedMessage());
        } catch (ValidationException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Unexpected error: " + e.getLocalizedMessage());
        }
    }
}
