package com.watabelabs.gepg.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The MessageUtil class provides functionality to sign messages using a digital
 * signature. It wraps the message and the signature into an XML envelope.
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
 * String signedMessage = messageUtil.sign(message, GepgBillSubReq.class);
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
     * @param message      the message to be signed
     * @param contentClass the class of the content to be wrapped
     * @return the signed message wrapped in an XML envelope
     * @throws Exception if an error occurs during signing or XML conversion
     */
    public <T> String sign(String message, Class<T> contentClass) throws Exception {
        if (message == null || message.isEmpty()) {
            throw new ValidationException("Message cannot be null or empty");
        }

        PrivateKey privateKey = DigitalSignatureUtil.loadPrivateKey(keystorePath, keystorePassword, keyAlias);
        String digitalSignature = DigitalSignatureUtil.signData(message, privateKey);

        T content = parseContent(message, contentClass);

        Envelope<T> envelope = new Envelope<>();
        envelope.setContent(Collections.singletonList(content));
        envelope.setGepgSignature(digitalSignature);

        return convertToXmlString(envelope, contentClass);
    }

    /**
     * Parses the content from the provided message.
     *
     * @param message      the message containing the content
     * @param contentClass the class of the content to be parsed
     * @return the parsed content
     * @throws Exception if an error occurs during parsing
     */
    private <T> T parseContent(String message, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return contentClass.cast(unmarshaller.unmarshal(new StringReader(message)));
    }

    /**
     * Converts the envelope object to an XML string.
     *
     * @param envelope     the envelope object containing the content and the
     *                     digital signature
     * @param contentClass the class of the content to be wrapped
     * @return the XML string representation of the envelope
     * @throws Exception if an error occurs during XML conversion
     */
    private <T> String convertToXmlString(Envelope<T> envelope, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(envelope, sw);
        return sw.toString();
    }

    /**
     * Unwraps the provided XML string from the envelope and converts it to the
     * specified POJO class.
     *
     * @param xmlString    the XML string containing the envelope
     * @param contentClass the class of the content to be extracted
     * @return the content as a POJO
     * @throws Exception if an error occurs during XML conversion
     */
    public <T> T unwrapAndConvertToPojo(String xmlString, Class<T> contentClass) throws Exception {
        // Unmarshal the Envelope
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Envelope<T> envelope = (Envelope<T>) unmarshaller.unmarshal(new StringReader(xmlString));

        ct the content and return the first item (assuming there's only one)
        return envelope.getContent().get(0);
    }

    /**
     * The Envelope class wraps any content and the digital signature into an XML
     * structure.
     */
    @XmlRootElement(name = "Gepg")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Envelope<T> {

        @XmlAnyElement(lax = true)
        private List<T> content;

        @XmlElement(name = "gepgSignature", required = true)
        private String gepgSignature;

        // Getters and setters

        public List<T> getContent() {
            return content;
        }

        public void setContent(List<T> content) {
            this.content = content;
        }

        public String getGepgSignature() {
            return gepgSignature;
        }

        public void setGepgSignature(String gepgSignature) {
            this.gepgSignature = gepgSignature;
        }
    }
}
