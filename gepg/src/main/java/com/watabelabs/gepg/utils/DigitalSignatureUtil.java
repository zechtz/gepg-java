package com.watabelabs.gepg.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Enumeration;

/**
 * The DigitalSignatureUtil class provides utility methods for loading a private
 * key from a PKCS#12 keystore
 * and generating a digital signature using that private key.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>
 * {@code
 * // Load the private key from the keystore
 * String keystorePath = "path/to/keystore.p12";
 * String keystorePassword = "your_keystore_password";
 * String alias = "your_key_alias";
 * PrivateKey privateKey = DigitalSignatureUtil.loadPrivateKey(keystorePath, keystorePassword, alias);
 *
 * // Sign the data
 * String data = "Sample data to be signed";
 * String signature = DigitalSignatureUtil.signData(data, privateKey);
 *
 * // Output the signature
 * System.out.println("Digital Signature: " + signature);
 * }
 * </pre>
 */
public class DigitalSignatureUtil {

    /**
     * Loads a private key from a PKCS#12 keystore.
     *
     * @param keystorePath     the path to the PKCS#12 keystore
     * @param keystorePassword the password for the keystore
     * @param keyStoreType     the keystore type
     * @param alias            the alias of the private key in the keystore
     * @return the loaded private key
     * @throws Exception if an error occurs while loading the private key
     */
    public static PrivateKey loadPrivateKey(String keystorePath, String keystorePassword, String alias)
            throws Exception {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(keystorePath)) {
            keystore.load(fis, keystorePassword.toCharArray());
        }

        // Print available aliases
        Enumeration<String> aliases = keystore.aliases();
        while (aliases.hasMoreElements()) {
            String a = aliases.nextElement();
            System.out.println("Alias: " + a);
        }

        if (!keystore.isKeyEntry(alias)) {
            throw new IllegalArgumentException("Alias " + alias + " does not exist or is not a key entry.");
        }

        return (PrivateKey) keystore.getKey(alias, keystorePassword.toCharArray());
    }

    /**
     * Generates a digital signature for the given data using the specified private
     * key.
     *
     * @param data       the data to be signed
     * @param privateKey the private key to use for signing
     * @return the generated digital signature, encoded in Base64
     * @throws Exception if an error occurs during the signing process
     */
    public static String signData(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }
}
