package com.watabelabs.gepg.utils;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;

import javax.validation.ValidationException;

public class PrivateKeyReader {

    public static PrivateKey get(String privateKeyPath, String keystoreType, String privateKeyPassword,
            String privateKeyAlias) {

        try {
            byte[] certStoreBytes = Files.readAllBytes(Paths.get(privateKeyPath));
            KeyStore keyStore = KeyStore.getInstance(keystoreType);
            keyStore.load(new ByteArrayInputStream(certStoreBytes), privateKeyPassword.toCharArray());

            // Get the private key and certificate from the loaded key store
            return (PrivateKey) keyStore.getKey(privateKeyAlias, privateKeyPassword.toCharArray());

        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(e.getLocalizedMessage());
        }
    }
}
