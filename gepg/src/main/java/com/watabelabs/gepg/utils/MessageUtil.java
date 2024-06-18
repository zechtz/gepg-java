package com.watabelabs.gepg.utils;

import java.io.StringWriter;
import java.security.PrivateKey;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The MessageUtil class provides functionality to sign messages using a digital
 * signature.
 * It wraps the message and the signature into an XML envelope.
 *
 * <p>
 * The digital signature is generated using a private key loaded from a PKCS#12
 * keystore.
 * </p>
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>
 * {@code
 * // Instantiate MessageUtil with required parameters
 * String keystorePath = "path/to/keystore.p12";
 * String keystorePassword = "your_keystore_password";
 * String keyAlias = "your_key_alias";
 *
 * MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);
 *
 * // Create a sample message
 * String message = "<gepgBillSubReq>...</gepgBillSubReq>";
 *
 * // Sign the message
 * String signedMessage = messageUtil.sign(message);
 * System.out.println(signedMessage);
 * }
 * </pre>
 */
public class MessageUtil {

    private String keystorePath;
    private String keystorePassword;
    private String keyAlias;

    /**
     * No-args constructor.
     * This constructor can be used if the parameters are set separately.
     */
    public MessageUtil() {
    }

    /**
     * All-args constructor.
     * This constructor initializes the MessageUtil with the necessary parameters.
     *
     * @param keystorePath     the path to the PKCS#12 keystore
     * @param keystorePassword the password for the keystore
     * @param keyAlias         the alias of the private key in the keystore
     */
    public MessageUtil(String keystorePath, String keystorePassword, String keyAlias) {
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keyAlias = keyAlias;
    }

    /**
     * Signs the provided message using the private key and wraps it in an XML
     * envelope.
     *
     * @param message the message to be signed
     * @return the signed message wrapped in an XML envelope
     * @throws Exception if an error occurs during signing or XML conversion
     */

    public String sign(String message) throws Exception {
        if (message == null || message.isEmpty()) {
            throw new ValidationException("Message cannot be null or empty");
        }

        PrivateKey privateKey = DigitalSignatureUtil.loadPrivateKey(keystorePath, keystorePassword, keyAlias);
        String digitalSignature = DigitalSignatureUtil.signData(message, privateKey);

        Envelope envelope = new Envelope();
        envelope.setMessage(message);
        envelope.setDigitalSignature(digitalSignature);

        return convertToXmlString(envelope);
    }

    /**
     * Converts the envelope object to an XML string.
     *
     * @param envelope the envelope object containing the message and the digital
     *                 signature
     * @return the XML string representation of the envelope
     * @throws Exception if an error occurs during XML conversion
     */
    private static String convertToXmlString(Envelope envelope) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Envelope.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(envelope, sw);
        return sw.toString();
    }

    /**
     * The Envelope class wraps the message and the digital signature into an XML
     * structure.
     */
    @XmlRootElement(name = "Gepg")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Envelope {

        @XmlElement(name = "message")
        private String message;

        @XmlElement(name = "gepgSignature")
        private String digitalSignature;

        // Getters and setters

        /**
         * Retrieves the message.
         *
         * @return the message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Sets the message.
         *
         * @param message the message to set
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * Retrieves the digital signature.
         *
         * @return the digital signature
         */
        public String getDigitalSignature() {
            return digitalSignature;
        }

        /**
         * Sets the digital signature.
         *
         * @param digitalSignature the digital signature to set
         */
        public void setDigitalSignature(String digitalSignature) {
            this.digitalSignature = digitalSignature;
        }
    }
}
