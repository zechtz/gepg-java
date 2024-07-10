package com.watabelabs.gepg.utils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * Utility class for generating and verifying digital signatures.
 * This class provides methods to create a digital signature for a given content
 * using a private key,
 * and verify a given digital signature using a public key.
 * The signature algorithm used for both operations is provided during the
 * instantiation of the class.
 */
public class DigitalSignatureUtil {

    private String signatureAlgorithm;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * Constructs a DigitalSignatureUtil instance with the specified signature
     * algorithm, private key, and public key.
     *
     * @param signatureAlgorithm the algorithm used for generating and verifying
     *                           signatures (e.g., "SHA1withRSA")
     * @param privateKey         the private key used for generating digital
     *                           signatures
     * @param publicKey          the public key used for verifying digital
     *                           signatures
     */
    public DigitalSignatureUtil(String signatureAlgorithm, PrivateKey privateKey, PublicKey publicKey) {
        this.signatureAlgorithm = signatureAlgorithm;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * Generates a digital signature for the given content.
     *
     * @param content the content to be signed
     * @return the Base64-encoded digital signature
     * @throws Exception if an error occurs during the signature generation process
     */
    public String generateSignature(String content) throws Exception {
        // Convert the content string into a byte array
        byte[] data = content.getBytes();

        // Initialize the Signature object with the specified algorithm
        Signature signature = Signature.getInstance(signatureAlgorithm);

        // Initialize the Signature object for signing with the private key
        signature.initSign(privateKey);

        // Add the data to be signed to the Signature object
        signature.update(data);

        // Generate the digital signature
        byte[] signatureBytes = signature.sign();

        // Encode the signature bytes into a Base64 string and return it
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    /**
     * Verifies a digital signature for the given content.
     *
     * @param signature the Base64-encoded digital signature to be verified
     * @param content   the content that was signed
     * @return true if the signature is valid; false otherwise
     * @throws Exception if an error occurs during the signature verification
     *                   process
     */
    public boolean verifySignature(String signature, String content) throws Exception {
        // Decode the Base64-encoded signature into a byte array
        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        // Initialize the Signature object with the specified algorithm
        Signature signatureInstance = Signature.getInstance(signatureAlgorithm);

        // Convert the content string into a byte array
        byte[] data = content.getBytes();

        // Initialize the Signature object for verification with the public key
        signatureInstance.initVerify(publicKey);

        // Add the data to be verified to the Signature object
        signatureInstance.update(data);

        // Verify the digital signature and return the result
        return signatureInstance.verify(signatureBytes);
    }
}
