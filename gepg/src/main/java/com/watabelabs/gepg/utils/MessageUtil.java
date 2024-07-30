package com.watabelabs.gepg.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collections;

import javax.validation.ValidationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Utility class for handling XML message signing and verification.
 */
public class MessageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

    private String privateKeyPath;
    private String publicKeyPath;
    private String privateKeyPassword;
    private String publicKeyPassword;
    private String privateKeyAlias;
    private String publicKeyAlias;
    private String keyStoreType;
    private String signatureAlgorithm;

    /**
     * Default constructor.
     */
    public MessageUtil() {
    }

    /**
     * Parameterized constructor for initializing MessageUtil with key and signature
     * information.
     *
     * @param privateKeyPath     Path to the private key keystore.
     * @param publicKeyPath      Path to the public key keystore.
     * @param privateKeyPassword Password for the private key keystore.
     * @param privateKeyAlias    Alias for the private key in the keystore.
     * @param publicKeyAlias     Alias for the public key in the keystore.
     * @param publicKeyPassword  Password for the public key keystore.
     * @param keyStoreType       Type of the keystore (e.g., PKCS12).
     * @param signatureAlgorithm Algorithm used for signing and verification (e.g.,
     *                           SHA256withRSA).
     */
    public MessageUtil(
            String privateKeyPath,
            String publicKeyPath,
            String privateKeyPassword,
            String privateKeyAlias,
            String publicKeyAlias,
            String publicKeyPassword,
            String keyStoreType,
            String signatureAlgorithm) {
        this.privateKeyPath = privateKeyPath;
        this.publicKeyPath = publicKeyPath;
        this.privateKeyPassword = privateKeyPassword;
        this.publicKeyPassword = publicKeyPassword;
        this.privateKeyAlias = privateKeyAlias;
        this.publicKeyAlias = publicKeyAlias;
        this.keyStoreType = keyStoreType;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    /**
     * Signs the given XML message using the private key and wraps it in an
     * Envelope.
     *
     * @param message      The XML message to be signed.
     * @param contentClass The class of the content within the message.
     * @param <T>          The type of the content.
     * @return The signed XML message wrapped in an Envelope.
     * @throws Exception If an error occurs during the signing process.
     */
    public <T> String sign(String message, Class<T> contentClass) throws Exception {
        if (message == null || message.isEmpty()) {
            throw new ValidationException("Message cannot be null or empty");
        }

        LOGGER.info("Original Message: " + message);

        // Read private key
        PrivateKey privateKey = PrivateKeyReader.get(privateKeyPath, keyStoreType, privateKeyPassword, privateKeyAlias);
        // Load the public key
        PublicKey publicKey = PublicKeyReader.get(publicKeyPath, keyStoreType, publicKeyPassword, publicKeyAlias);

        DigitalSignatureUtil digitalSignatureUtil = new DigitalSignatureUtil(signatureAlgorithm, privateKey, publicKey);

        T content = parseContent(message, contentClass);

        String encodedSignature = digitalSignatureUtil.generateSignature(message);

        Envelope<T> envelope = new Envelope<>();
        envelope.setContent(Collections.singletonList(content));
        envelope.setGepgSignature(encodedSignature);

        String signedXml = convertToXmlString(envelope, contentClass);

        boolean isValid = verify(signedXml, contentClass);

        if (!isValid) {
            String errorMessage = "Signature verification failed after signing.";
            LOGGER.error(errorMessage);
            throw new ValidationException(errorMessage);
        }

        // add back the xml declaration that was stripped off by the sanitize method
        String escapedString = escapeCharacter(signedXml);
        LOGGER.info("Signed XML: " + escapedString);
        return escapedString;
    }

    /**
     * Parses the content of the given XML message into an instance of the specified
     * class.
     *
     * @param message      The XML message to be parsed.
     * @param contentClass The class of the content.
     * @param <T>          The type of the content.
     * @return An instance of the specified content class.
     * @throws Exception If an error occurs during parsing.
     */
    public static <T> T parseContent(String message, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return contentClass.cast(unmarshaller.unmarshal(new StringReader(message)));
    }

    /**
     * Converts an Envelope containing the specified content class into an XML
     * string.
     *
     * @param envelope     The Envelope containing the content.
     * @param contentClass The class of the content.
     * @param <T>          The type of the content.
     * @return The XML string representation of the Envelope.
     * @throws Exception If an error occurs during the conversion.
     */
    public <T> String convertToXmlString(Envelope<T> envelope, Class<T> contentClass) throws Exception {
        JAXBContext context;
        String xmlString;
        try {
            StringWriter sw = new StringWriter();
            context = JAXBContext.newInstance(Envelope.class, contentClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false); // Include XML declaration
            marshaller.marshal(envelope, sw);
            xmlString = sw.toString();
            return xmlString;
        } catch (Exception e) {
            throw new ValidationException(e.getLocalizedMessage());
        }
    }

    /**
     * Unwraps the provided XML string from the Envelope and converts it to the
     * specified POJO class.
     *
     * @param xmlString    The XML string containing the Envelope.
     * @param contentClass The class of the content to be extracted.
     * @param <T>          The type of the content.
     * @return The content as a POJO.
     * @throws Exception If an error occurs during the conversion.
     */
    public static <T> T unwrapAndConvertToPojo(String xmlString, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        @SuppressWarnings("unchecked")
        Envelope<T> envelope = (Envelope<T>) unmarshaller.unmarshal(new StringReader(xmlString));
        return envelope.getContent().get(0);
    }

    /**
     * Verifies the digital signature of the provided XML string using the public
     * key.
     *
     * @param xmlString    The XML string containing the Envelope with the digital
     *                     signature.
     * @param contentClass The class of the content within the Envelope.
     * @param <T>          The type of the content.
     * @return True if the signature is valid, false otherwise.
     * @throws Exception If an error occurs during verification.
     */
    public <T> boolean verify(String xmlString, Class<T> contentClass) throws Exception {
        // Parse the XML string to a Document
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new InputSource(new StringReader(xmlString)));

        // Unmarshal the XML to an Envelope object
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        @SuppressWarnings("unchecked")
        Envelope<T> envelope = (Envelope<T>) unmarshaller.unmarshal(doc);

        // Extract the content and signature
        T content = envelope.getContent().get(0);
        String gepgSignature = envelope.getGepgSignature();

        // Re-marshal the content to an XML string
        Document contentDoc = docBuilder.newDocument();
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.marshal(content, contentDoc);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(contentDoc), new StreamResult(writer));
        String message = writer.toString();

        // Remove XML declaration if present
        message = message.replaceAll("^<\\?xml.*?\\?>", "").trim();

        // Load the public key
        PublicKey publicKey = PublicKeyReader.get(publicKeyPath, keyStoreType, publicKeyPassword, publicKeyAlias);

        // Load the private key
        PrivateKey privateKey = PrivateKeyReader.get(privateKeyPath, keyStoreType, privateKeyPassword, privateKeyAlias);

        DigitalSignatureUtil digitalSignatureUtil = new DigitalSignatureUtil(signatureAlgorithm, privateKey, publicKey);

        return digitalSignatureUtil.verifySignature(gepgSignature, message);
    }

    /**
     * Escapes special characters in the provided XML string.
     *
     * @param xmlString The XML string to be escaped.
     * @return The escaped XML string.
     */

    public static String escapeCharacter(String xmlString) {
        // Unescape any pre-escaped characters first
        String unescapedString = htmlUnescape(xmlString);

        // Remove newlines and spaces between tags but keep the inner spaces
        String compressedString = unescapedString.replaceAll(">\\s+<", "><").trim();

        return compressedString;
    }

    /**
     * Unescapes special characters in the provided XML string.
     *
     * @param xmlString The XML string to be unescaped.
     * @return The unescaped XML string.
     */
    private static String htmlUnescape(String xmlString) {
        return xmlString
                .replace("&amp;", "&")
                .replace("&apos;", "'")
                .replace("&Ouml;", "Ö")
                .replace("&quot;", "\"")
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }
}
