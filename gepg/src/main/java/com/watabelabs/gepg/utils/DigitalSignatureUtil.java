package com.watabelabs.gepg.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Enumeration;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DigitalSignatureUtil class provides utility methods for loading a private
 * key from a PKCS#12 keystore,
 * generating a digital signature using that private key, and verifying the
 * signature using a public key.
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
 *
 * // Load the public key from the keystore
 * PublicKey publicKey = DigitalSignatureUtil.loadPublicKey(keystorePath, keystorePassword, alias);
 *
 * // Verify the signature
 * boolean isValid = DigitalSignatureUtil.verifySignature(data, signature, publicKey);
 * System.out.println("Signature is valid: " + isValid);
 * }
 * </pre>
 */

public class DigitalSignatureUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalSignatureUtil.class);

    /**
     * Loads a private key from a PKCS#12 keystore.
     *
     * @param privateKeyPath     the path to the PKCS#12 keystore
     * @param privateKeyPassword the password for the keystore
     * @param privateKeyAlias    the alias of the private key in the keystore
     * @return the loaded private key
     * @throws Exception if an error occurs while loading the private key
     */
    public static PrivateKey loadPrivateKey(String privateKeyPath, String privateKeyPassword, String privateKeyAlias)
            throws Exception {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(privateKeyPath)) {
            keystore.load(fis, privateKeyPassword.toCharArray());
        }

        Enumeration<String> aliases = keystore.aliases();
        while (aliases.hasMoreElements()) {
            String a = aliases.nextElement();
            System.out.println("Alias: " + a);
        }

        if (!keystore.isKeyEntry(privateKeyAlias)) {
            throw new IllegalArgumentException("Alias " + privateKeyAlias + " does not exist or is not a key entry.");
        }

        return (PrivateKey) keystore.getKey(privateKeyAlias, privateKeyPassword.toCharArray());
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
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);

            byte[] messageBytes = data.getBytes();
            LOGGER.info("Data Bytes to Sign: {}", Base64.getEncoder().encodeToString(messageBytes));
            signature.update(messageBytes);
            byte[] signatureBytes = signature.sign();

            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            LOGGER.error("Signing error: " + e.getMessage(), e);
            throw new ValidationException(e.getLocalizedMessage());
        }
    }

    /**
     * Loads a public key from a PKCS#12 keystore.
     *
     * @param publicKeyPath     the path to the PKCS#12 keystore
     * @param publicKeyPassword the password for the keystore
     * @param publicKeyAlias    the alias of the certificate in the keystore
     * @return the loaded public key
     * @throws Exception if an error occurs while loading the public key
     */
    public static PublicKey loadPublicKey(String publicKeyPath, String publicKeyPassword, String publicKeyAlias)
            throws Exception {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(publicKeyPath)) {
            keystore.load(fis, publicKeyPassword.toCharArray());
        }

        Certificate certificate = keystore.getCertificate(publicKeyAlias);

        if (certificate == null) {
            String errorMessage = String.format("Public Key Alias %s does not exist or does not have a certificate.",
                    publicKeyAlias);
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        return certificate.getPublicKey();
    }

    /**
     * Verifies the digital signature of the given data using the provided public
     * key.
     *
     * @param data          the data to verify
     * @param signatureData the Base64-encoded signature to verify
     * @param publicKey     the public key to verify with
     * @return true if the signature is valid, false otherwise
     * @throws Exception if an error occurs while verifying the signature
     */
    public static boolean verifySignature(String data, String signatureData, PublicKey publicKey) throws Exception {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            byte[] messageBytes = data.getBytes();
            LOGGER.info("Data Bytes to Verify: {}", Base64.getEncoder().encodeToString(messageBytes));
            signature.update(messageBytes);
            byte[] signatureBytes = Base64.getDecoder().decode(signatureData);
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            LOGGER.error("Verification error: " + e.getMessage(), e);
            throw new ValidationException(e.getLocalizedMessage());
        }
    }
}
