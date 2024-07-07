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

public class PublicKeyReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicKeyReader.class);

    public static PublicKey get(String publicKeyPath, String keystoreType, String publicKeyPassword,
            String publicKeyAlias) {
        try {
            KeyStore keystore = KeyStore.getInstance(keystoreType);
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

            LOGGER.info(" THE_CERTIFICATE_FOUND_WITH_PUBLIC_KEY:{}", certificate.getPublicKey());
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
