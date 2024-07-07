package com.watabelabs.gepg.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.validation.ValidationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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

    private PublicKey publicKey;

    public MessageUtil() {
    }

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

    public <T> String sign(String message, Class<T> contentClass) throws Exception {
        if (message == null || message.isEmpty()) {
            throw new ValidationException("Message cannot be null or empty");
        }

        LOGGER.info("Original Message: " + message);

        // Read private key
        PrivateKey privateKey = PrivateKeyReader.get(privateKeyPath, keyStoreType, privateKeyPassword, privateKeyAlias);

        byte[] dataBytes = message.getBytes(StandardCharsets.UTF_8);

        byte[] digitalSignature;
        String encodedSignature;

        try {
            Signature signature = Signature.getInstance(signatureAlgorithm);
            signature.initSign(privateKey);
            signature.update(dataBytes);
            digitalSignature = signature.sign();

            // Base64 encode the digital signature
            encodedSignature = Base64.getEncoder().encodeToString(digitalSignature);
        } catch (Exception e) {
            LOGGER.error("Signing error: " + e.getMessage());
            throw new ValidationException(e.getLocalizedMessage());
        }

        T content = parseContent(message, contentClass);

        Envelope<T> envelope = new Envelope<>();
        envelope.setContent(Collections.singletonList(content));
        envelope.setGepgSignature(encodedSignature.getBytes(StandardCharsets.UTF_8));

        String signedXml = convertToXmlString(envelope, contentClass);

        LOGGER.info("Signed XML: " + signedXml);

        boolean isValid = verify(signedXml, contentClass);

        if (!isValid) {
            String errorMessage = "Signature verification failed after signing.";
            LOGGER.error(errorMessage);
            throw new ValidationException(errorMessage);
        }

        return signedXml;
    }

    public static <T> T parseContent(String message, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return contentClass.cast(unmarshaller.unmarshal(new StringReader(message)));
    }

    public <T> String convertToXmlString(Envelope<T> envelope, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(envelope, sw);
        return sw.toString();
    }

    private <T> String convertToXmlStringWithoutDeclaration(Envelope<T> envelope, Class<T> contentClass)
            throws Exception {
        // Create a new instance of DocumentBuilderFactory
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        // Create a new DocumentBuilder
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        // Create a new Document
        Document doc = docBuilder.newDocument();

        // Create a JAXB context for the Envelope class and content class
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        // Create a Marshaller
        Marshaller marshaller = context.createMarshaller();
        // Set Marshaller properties
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        // Marshal the envelope to the Document
        marshaller.marshal(envelope, doc);

        // Create a TransformerFactory
        TransformerFactory tf = TransformerFactory.newInstance();
        // Create a Transformer
        Transformer transformer = tf.newTransformer();
        // Create a StringWriter
        StringWriter writer = new StringWriter();
        // Transform the Document to a string
        transformer.transform(new DOMSource(doc), new StreamResult(writer));

        // Return the transformed XML string
        return writer.toString();
    }

    public static <T> T unwrapAndConvertToPojo(String xmlString, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        @SuppressWarnings("unchecked")
        Envelope<T> envelope = (Envelope<T>) unmarshaller.unmarshal(new StringReader(xmlString));
        return envelope.getContent().get(0);
    }

    public <T> boolean verify(String xmlString, Class<T> contentClass) throws Exception {
        // Create a DocumentBuilderFactory
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        // Create a DocumentBuilder
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        // Parse the XML string to a Document
        Document doc = docBuilder.parse(new InputSource(new StringReader(xmlString)));

        // Create a JAXB context for the Envelope and content class
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        @SuppressWarnings("unchecked")
        Envelope<T> envelope = (Envelope<T>) unmarshaller.unmarshal(doc);

        T content = envelope.getContent().get(0);
        byte[] digitalSignature = envelope.getGepgSignature();

        // Marshal the content back to XML using Document
        Document contentDoc = docBuilder.newDocument();
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        marshaller.marshal(content, contentDoc);

        // Transform the Document to a string
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(contentDoc), new StreamResult(writer));
        String message = writer.toString();

        // Remove XML declaration from the marshalled message
        message = message.replaceAll("^<\\?xml.*?\\?>", "").trim();
        LOGGER.info("Message to Verify: " + message);

        LOGGER.info("Digital Signature (Base64 Encoded): " + Base64.getEncoder().encodeToString(digitalSignature));

        publicKey = PublicKeyReader.get(publicKeyPath, keyStoreType, publicKeyPassword, publicKeyAlias);

        try {
            // Verify the incoming request's signature
            Signature verifier = Signature.getInstance(signatureAlgorithm);
            verifier.initVerify(publicKey);
            verifier.update(message.getBytes(StandardCharsets.UTF_8));

            // Decode the signature from Base64
            boolean isVerified = verifier.verify(Base64.getDecoder().decode(digitalSignature));
            LOGGER.info("Signature Verification Result: {}", isVerified);
            return isVerified;
        } catch (Exception e) {
            LOGGER.error("Verification error: " + e.getMessage());
            throw new ValidationException(e.getMessage());
        }
    }

    @XmlRootElement(name = "Gepg")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Envelope<T> {

        @XmlAnyElement(lax = true)
        private List<T> content;

        @XmlElement(name = "gepgSignature", required = true)
        private byte[] gepgSignature;

        public List<T> getContent() {
            return content;
        }

        public void setContent(List<T> content) {
            this.content = content;
        }

        public byte[] getGepgSignature() {
            return gepgSignature;
        }

        public void setGepgSignature(byte[] gepgSignature) {
            this.gepgSignature = gepgSignature;
        }
    }
}
