package com.watabelabs.gepg;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.watabelabs.gepg.amqp.enums.GepgQueueHeaderRequestType;
import com.watabelabs.gepg.amqp.headers.GepgQueueHeaders;
import com.watabelabs.gepg.amqp.publisher.GepgAMqPublisher;
import com.watabelabs.gepg.amqp.queues.GepgAMqQueues;
import com.watabelabs.gepg.constants.GepgResponseCode;
import com.watabelabs.gepg.mappers.bill.acks.GepgBillSubReqAck;
import com.watabelabs.gepg.mappers.bill.acks.GepgBillSubResAck;
import com.watabelabs.gepg.mappers.bill.acks.GepgBillSubRespAck;
import com.watabelabs.gepg.mappers.bill.responses.GepgBillSubResp;
import com.watabelabs.gepg.mappers.payment.acks.GepgOlPmtNtfSpInfoAck;
import com.watabelabs.gepg.mappers.payment.acks.GepgPmtSpInfoAck;
import com.watabelabs.gepg.mappers.payment.requests.GepgPmtSpInfo;
import com.watabelabs.gepg.mappers.reconciliation.acks.GepgSpReconcRespAck;
import com.watabelabs.gepg.mappers.reconciliation.responses.GepgSpReconcResp;
import com.watabelabs.gepg.utils.DateTimeUtil;
import com.watabelabs.gepg.utils.DotEnvUtil;
import com.watabelabs.gepg.utils.Envelope;
import com.watabelabs.gepg.utils.GepgEndpoints;
import com.watabelabs.gepg.utils.MessageUtil;
import com.watabelabs.gepg.utils.PrivateKeyReader;
import com.watabelabs.gepg.utils.PublicKeyReader;
import com.watabelabs.gepg.utils.XmlUtil;

/**
 * The {@code GepgApiClient} class provides a client interface to interact with
 * the GePG API.
 * It includes methods for submitting bills, payments, and reconciliation
 * requests, as well
 * as for receiving and processing responses from the GePG API.
 */
public class GepgApiClient {

    private static final Logger logger = LoggerFactory.getLogger(GepgApiClient.class);

    private String gepgCode;
    private String apiUrl;

    private String privateKeystorePath;
    private String privateKeystorePassword;
    private String privateKeyAlias;

    private String publicKeystorePath;
    private String publicKeystorePassword;
    private String publicKeyAlias;
    private String keystoreType;
    private String signatureAlgorithm;

    // Default HTTP headers
    private static final String CONTENT_TYPE = "Application/xml";
    private static final String GEPG_COM = "default.sp.in";
    private static final String GEPG_COM_CN_REUSE = "reusebill.sp.in";
    private static final String GEPG_COM_BILL_CHANGE = "changebill.sp.in";

    /**
     * No-args constructor initializes the client with environment variables.
     */
    public GepgApiClient() {
        this.apiUrl = DotEnvUtil.getEnvVariable("API_URL");
        this.gepgCode = DotEnvUtil.getEnvVariable("GEPG_CODE");

        this.privateKeystorePath = DotEnvUtil.getEnvVariable("PRIVATE_KEYSTORE_PATH");
        this.privateKeystorePassword = DotEnvUtil.getEnvVariable("PRIVATE_KEYSTORE_PASSWORD");
        this.privateKeyAlias = DotEnvUtil.getEnvVariable("PRIVATE_KEY_ALIAS");

        this.publicKeystorePath = DotEnvUtil.getEnvVariable("PUBLIC_KEYSTORE_PATH");
        this.publicKeyAlias = DotEnvUtil.getEnvVariable("PUBLIC_KEY_ALIAS");
        this.publicKeystorePassword = DotEnvUtil.getEnvVariable("PUBLIC_KEYSTORE_PASSWORD");

        this.keystoreType = DotEnvUtil.getEnvVariable("KEYSTORE_TYPE");
        this.signatureAlgorithm = DotEnvUtil.getEnvVariable("SIGNATURE_ALGORITHM");

        // Verify private keystore file existence
        File keystoreFile = new File(this.privateKeystorePath);
        if (!keystoreFile.exists() || !keystoreFile.canRead()) {
            throw new RuntimeException("Keystore file not found or not readable: " + this.privateKeystorePath);
        }

        // Verify public keystore file existence
        File publicKeystoreFile = new File(this.publicKeystorePath);
        if (!publicKeystoreFile.exists() || !publicKeystoreFile.canRead()) {
            throw new RuntimeException("Public Keystore file not found or not readable: " + this.publicKeystorePath);
        }
    }

    /**
     * Private constructor to prevent instantiation.
     *
     * @param endpoint the endpoint to be appended to the base API URL
     */
    @SuppressWarnings("unused")
    private GepgApiClient(String endpoint) {
        this();
        this.apiUrl = this.apiUrl + endpoint;
    }

    // Getters and Setters

    /**
     * Gets the path to the private keystore.
     *
     * @return the private keystore path.
     */
    public String getPrivateKeystorePath() {
        return privateKeystorePath;
    }

    /**
     * Sets the path to the private keystore.
     *
     * @param privateKeystorePath the private keystore path to set.
     */
    public void setPrivateKeystorePath(String privateKeystorePath) {
        this.privateKeystorePath = privateKeystorePath;
    }

    /**
     * Gets the password for the private keystore.
     *
     * @return the private keystore password.
     */
    public String getPrivateKeystorePassword() {
        return privateKeystorePassword;
    }

    /**
     * Sets the password for the private keystore.
     *
     * @param privateKeystorePassword the private keystore password to set.
     */
    public void setPrivateKeystorePassword(String privateKeystorePassword) {
        this.privateKeystorePassword = privateKeystorePassword;
    }

    /**
     * Gets the alias for the private key.
     *
     * @return the private key alias.
     */
    public String getPrivateKeyAlias() {
        return privateKeyAlias;
    }

    /**
     * Sets the alias for the private key.
     *
     * @param privateKeyAlias the private key alias to set.
     */
    public void setPrivateKeyAlias(String privateKeyAlias) {
        this.privateKeyAlias = privateKeyAlias;
    }

    /**
     * Gets the GEPG code.
     *
     * @return the GEPG code.
     */
    public String getGepgCode() {
        return gepgCode;
    }

    /**
     * Sets the GEPG code.
     *
     * @param gepgCode the GEPG code to set.
     */
    public void setGepgCode(String gepgCode) {
        this.gepgCode = gepgCode;
    }

    /**
     * Gets the API URL.
     *
     * @return the API URL.
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Sets the API URL.
     *
     * @param apiUrl the API URL to set.
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * Checks if the private and public keys form a valid key pair.
     *
     * @return true if the keys form a valid key pair, false otherwise
     */
    public boolean checkKeyPair() {
        try {
            PrivateKey privateKey = PrivateKeyReader.get(privateKeystorePath, keystoreType, privateKeystorePassword,
                    privateKeyAlias);
            PublicKey publicKey = PublicKeyReader.get(publicKeystorePath, keystoreType, publicKeystorePassword,
                    publicKeyAlias);

            // Generate a test message
            String testMessage = "Test message for key pair validation";
            byte[] testMessageBytes = testMessage.getBytes(StandardCharsets.UTF_8);

            // Sign the test message with the private key
            Signature signature = Signature.getInstance(signatureAlgorithm);
            signature.initSign(privateKey);
            signature.update(testMessageBytes);
            byte[] digitalSignature = signature.sign();

            // Verify the test message with the public key
            signature.initVerify(publicKey);
            signature.update(testMessageBytes);
            boolean isVerified = signature.verify(digitalSignature);

            if (!isVerified) {
                logger.error(
                        "Key pair mismatch: The public key did not verify the signature created by the private key.");
            } else {
                logger.info(
                        "Key pair match: The public key successfully verified the signature created by the private key.");
            }

            return isVerified;
        } catch (Exception e) {
            logger.error("Exception occurred while checking key pair: ", e);
            return false;
        }
    }

    /*
     * ============================================================================
     * PUBLIC METHODS
     * ============================================================================
     */

    /**
     * Submits a bill to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck submitBill(String signedRequest) throws Exception {
        String submissionUrl = apiUrl + GepgEndpoints.SUBMIT_BILL;

        // Step 1: Send the bill submission request
        String response = sendRequest(submissionUrl, signedRequest, GEPG_COM);

        logger.info("BILL_SUBMISSION_RESPONSE:{}", response);

        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        return billSubReqAck;
    }

    /**
     * Publishes a bill submission request to the RabbitMQ queue.
     *
     * @param signedRequest the signed XML request
     */
    public void publishBill(String signedRequest) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(GepgQueueHeaderRequestType.REQUEST_TYPE.toString(), GepgQueueHeaders.BILL_SUBMISSION_HEADER);
        GepgAMqPublisher.publishToQueue(GepgAMqQueues.BILL_SUBMISSION_QUEUE, signedRequest, headers);
    }

    /**
     * Submits a control number reuse request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck reuseControlNumber(String signedRequest) throws Exception {
        String submissionUrl = apiUrl + GepgEndpoints.REUSE_CONTROL_NUMBER;

        // Step 1: Send the control number reuse request
        String response = sendRequest(submissionUrl, signedRequest, GEPG_COM_CN_REUSE);

        logger.info("CONTROL_NUMBER_REUSE_REQUEST_RESPONSE:{}", response);

        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        return billSubReqAck;
    }

    /**
     * Publishes a control number reuse request to the RabbitMQ queue.
     *
     * @param signedRequest the signed XML request
     */
    public void publishControlNumberReuse(String signedRequest) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(GepgQueueHeaderRequestType.REQUEST_TYPE.toString(), GepgQueueHeaders.CONTROL_NUMBER_REUSE_HEADER);
        GepgAMqPublisher.publishToQueue(GepgAMqQueues.BILL_SUBMISSION_QUEUE, signedRequest, headers);
    }

    /**
     * Submits a bill change/update request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck updateBill(String signedRequest) throws Exception {
        String submissionUrl = apiUrl + GepgEndpoints.UPDATE_BILL;

        // Step 1: Send the control number reuse request
        String response = sendRequest(submissionUrl, signedRequest, GEPG_COM_BILL_CHANGE);

        logger.info("BILL_UPDATE_REQUEST_RESPONSE:{}", response);

        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        return billSubReqAck;
    }

    /**
     * Publishes a bill change/update request to the RabbitMQ queue.
     *
     * @param signedRequest the signed XML request
     */
    public void publishBillUpdate(String signedRequest) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(GepgQueueHeaderRequestType.REQUEST_TYPE.toString(), GepgQueueHeaders.BILL_UPDATE_HEADER);
        GepgAMqPublisher.publishToQueue(GepgAMqQueues.BILL_SUBMISSION_QUEUE, signedRequest, headers);
    }

    /**
     * Submits a bill change/update request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck cancelBill(String signedRequest) throws Exception {
        String submissionUrl = apiUrl + GepgEndpoints.CANCEL_BILL;

        // Step 1: Send the control number reuse request
        String response = sendRequest(submissionUrl, signedRequest, CONTENT_TYPE);

        logger.info("CANCEL_BILL_REQUEST_RESPONSE:{}", response);

        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        return billSubReqAck;
    }

    /**
     * Publishes a bill cancellation request to the RabbitMQ queue.
     *
     * @param signedRequest the signed XML request
     */
    public void publishBillCancellation(String signedRequest) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(GepgQueueHeaderRequestType.REQUEST_TYPE.toString(), GepgQueueHeaders.BILL_CANCELLATION_HEADER);
        GepgAMqPublisher.publishToQueue(GepgAMqQueues.BILL_SUBMISSION_QUEUE, signedRequest, headers);
    }

    /**
     * Submits a payment to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgPmtSpInfoAck submitPayment(String signedRequest) throws Exception {
        String submissionUrl = apiUrl + GepgEndpoints.SEND_PAYMENT;

        // Step 1: Send the control number reuse request
        String response = sendRequest(submissionUrl, signedRequest, CONTENT_TYPE);

        logger.info("PAYMENT_SUBMISSION_REQUEST_RESPONSE:{}", response);

        Envelope<GepgPmtSpInfoAck> envelope = mapResponse(response, GepgPmtSpInfoAck.class);
        GepgPmtSpInfoAck paymentSpInfoAck = envelope.getContent().get(0);

        return paymentSpInfoAck;
    }

    /**
     * Publishes a payment submission request to the RabbitMQ queue.
     *
     * @param signedRequest the signed XML request
     */
    public void publishPayments(String signedRequest) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(GepgQueueHeaderRequestType.REQUEST_TYPE.toString(), GepgQueueHeaders.PAYMENT_SUBMISSION_HEADER);
        GepgAMqPublisher.publishToQueue(GepgAMqQueues.PAYMENT_SUBMISSION_QUEUE, signedRequest, headers);
    }

    /**
     * Submits a reconciliation request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgSpReconcRespAck requestReconciliation(String signedRequest) throws Exception {
        String submissionUrl = apiUrl + GepgEndpoints.REQUEST_RECONCILIATION;

        // Step 1: Send the control number reuse request
        String response = sendRequest(submissionUrl, signedRequest, CONTENT_TYPE);

        logger.info("RECONCILIATION_REQUEST_RESPONSE:{}", response);

        Envelope<GepgSpReconcRespAck> envelope = mapResponse(response, GepgSpReconcRespAck.class);
        GepgSpReconcRespAck reconciliationRespAck = envelope.getContent().get(0);

        return reconciliationRespAck;
    }

    /**
     * Publishes a reconciliation request to the RabbitMQ queue.
     *
     * @param signedRequest the signed XML request
     */
    public void publishReconciliation(String signedRequest) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(GepgQueueHeaderRequestType.REQUEST_TYPE.toString(),
                GepgQueueHeaders.RECONCILIATION_SUBMISSION_HEADER);
        GepgAMqPublisher.publishToQueue(GepgAMqQueues.RECONCILIATION_SUBMISSION_QUEUE, signedRequest,
                headers);
    }

    /**
     * Receives the Reconciliation response as XML and returns the POJO.
     *
     * @param responseXml the reconciliation response XML from GePG
     * @return {@link GepgSpReconcResp} the java representation of the response
     * @throws Exception if an error occurs during the process
     */
    public GepgSpReconcResp receiveReconciliationResponse(String responseXml) throws Exception {
        return convertToJavaObject(responseXml, GepgSpReconcResp.class);
    }

    /**
     * Receives the Payment Notification Info as XML and returns the POJO.
     *
     * @param responseXml the payment notification XML response from GePG
     * @return {@link GepgPmtSpInfo} the java representation of the response
     * @throws Exception if an error occurs during the process
     */
    public GepgPmtSpInfo receivePaymentNotification(String responseXml) throws Exception {
        return convertToJavaObject(responseXml, GepgPmtSpInfo.class);
    }

    /**
     * Creates the Payment Notification Info Ack XML and returns it as a string.
     *
     * @param responseXml the payment notification XML response from GePG
     * @return the XML string representing {@link GepgPmtSpInfoAck} class
     * @throws Exception if an error occurs during the process
     */
    public String getPaymentInfoAck(String responseXml) throws Exception {
        GepgPmtSpInfoAck gepgPmtSpInfoAck = new GepgPmtSpInfoAck(7101);

        String ackXml = parseToXml(gepgPmtSpInfoAck);

        // Initialize with the required parameters
        return signMessage(ackXml, GepgBillSubRespAck.class);
    }

    /**
     * Creates the Reconciliation Response Ack XML and returns it as a string.
     *
     * @param responseXml the reconciliation response XML response from GePG
     * @return the XML string representing {@link GepgPmtSpInfoAck} class
     * @throws Exception if an error occurs during the process
     */
    public String getReconciliationAck(String responseXml) throws Exception {
        GepgBillSubRespAck gepgBillSubRespAck = new GepgBillSubRespAck(7101);

        String ackXml = parseToXml(gepgBillSubRespAck);

        // Initialize with the required parameters
        return signMessage(ackXml, GepgBillSubRespAck.class);
    }

    /**
     * Receives the control number response and sends an acknowledgment back.
     *
     * @param responseXml the control number xml response from GEPG
     * @return GepgBillSubResp the java object corresponding to the response
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubResp receiveControlNumber(String responseXml) throws Exception {
        return convertToJavaObject(responseXml, GepgBillSubResp.class);
    }

    /**
     * Signs the provided message using the private key and wraps it in an XML
     * envelope.
     *
     * @param <T>          the type of the content
     * @param message      the message to be signed
     * @param contentClass the class of the content to be wrapped
     * @return the signed message wrapped in an XML envelope
     * @throws Exception if an error occurs during signing or XML conversion
     */
    public <T> String signMessage(String message, Class<T> contentClass) throws Exception {
        MessageUtil messageUtil = new MessageUtil(
                this.privateKeystorePath,
                this.publicKeystorePath,
                this.privateKeystorePassword,
                this.privateKeyAlias,
                this.publicKeyAlias,
                this.publicKeystorePassword,
                this.keystoreType,
                this.signatureAlgorithm);
        return messageUtil.sign(message, contentClass);
    }

    /**
     * Verifies the provided XML string using the public key.
     *
     * @param <T>               the type of the content
     * @param xmlString         the XML string containing the envelope
     * @param contentClass      the class of the content to be verified
     * @param publicKeyPath     the path to the public key
     * @param publicKeyPassword the password for the public key
     * @param publicKeyAlias    the alias of the public key
     * @return true if the signature is valid, false otherwise
     * @throws Exception if an error occurs during verification
     */
    public <T> boolean verify(String xmlString, Class<T> contentClass, String publicKeyPath, String publicKeyPassword,
            String publicKeyAlias) throws Exception {
        MessageUtil messageUtil = new MessageUtil(this.privateKeystorePath, this.publicKeystorePath,
                this.privateKeystorePassword, this.privateKeyAlias, this.publicKeyAlias, this.publicKeystorePassword,
                this.keystoreType, this.signatureAlgorithm);

        return messageUtil.verify(xmlString, contentClass);
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

    public <T> boolean verifyMessage(String xmlString, Class<T> contentClass) throws Exception {

        MessageUtil messageUtil = new MessageUtil(
                this.privateKeystorePath,
                this.publicKeystorePath,
                this.privateKeystorePassword,
                this.privateKeyAlias,
                this.publicKeyAlias,
                this.publicKeystorePassword,
                this.keystoreType,
                this.signatureAlgorithm);

        return messageUtil.verify(xmlString, contentClass);
    }

    /**
     * Unwraps the provided XML string from the envelope and converts it to the
     * specified POJO class.
     *
     * @param xmlString    the XML string containing the envelope
     * @param contentClass the class of the content to be extracted
     * @param <T>          the type of the content
     * @return the content as a POJO
     * @throws Exception if an error occurs during XML conversion
     */
    public <T> T convertToJavaObject(String xmlString, Class<T> contentClass) throws Exception {
        return MessageUtil.unwrapAndConvertToPojo(xmlString, contentClass);
    }

    /**
     * Converts a JAXB-annotated object to an XML string.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object
     * @throws Exception if an error occurs during XML conversion
     */
    public String parseToXml(Object object) throws Exception {
        return parseToXml(object, false);
    }

    /**
     * Converts a JAXB-annotated object to an XML string.
     *
     * @param object         the JAXB-annotated object to convert
     * @param withDeclartion withDeclartion the the boolean value which when set to
     *                       true
     *                       will parse with declaratin
     * @return the XML string representation of the object with the xml declartion
     * @throws Exception if an error occurs during XML conversion
     */
    public String parseToXml(Object object, Boolean withDeclartion) throws Exception {
        return withDeclartion ? XmlUtil.convertToXmlString(object)
                : XmlUtil.convertToXmlStringWithoutDeclaration(object);
    }

    /**
     * Converts a JAXB-annotated object to a signed GepgPayload string.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object
     * @throws Exception if an error occurs during XML conversion
     */
    public String generatePayload(Object object) throws Exception {
        String xml = XmlUtil.convertToXmlStringWithoutDeclaration(object);
        return signMessage(xml, object.getClass());
    }

    /**
     * Returns a message corresponding to the given code.
     *
     * @param code the code for which to retrieve the message
     * @return the message corresponding to the given code
     * @throws IllegalArgumentException if the code is not recognized
     */
    public String getResponseMessage(int code) {
        String message = GepgResponseCode.getResponseMessage(code);
        if (message == null) {
            throw new IllegalArgumentException("Unknown code: " + code);
        }
        return message;
    }

    /**
     * Checks if the provided XML string contains all specified keys.
     *
     * @param xmlString the XML string to check
     * @param keys      the keys to check for in the XML string
     * @return true if all keys are found in the XML string, false otherwise
     * @throws Exception if an error occurs during XML parsing or XPath evaluation
     *
     *                   Example usage:
     *
     *                   <pre>{@code
     * String xml = "<root><key1>value1</key1><key2>value2</key2></root>";
     * boolean containsKeys = XmlUtil.checkKeys(xml, "key1", "key2");
     * System.out.println(containsKeys); // Outputs: true
     * }</pre>
     */
    public boolean checkKeys(String xmlString, String... keys) throws Exception {
        return XmlUtil.checkKeys(xmlString, keys);
    }

    /**
     * Generates a response acknowledgment for the specified class with status code
     * 7101,
     * converts it to an XML string, signs it, and returns the signed XML string.
     *
     * @param instance the class type to generate the acknowledgment for
     * @param <T>      the type of the class
     * @return the signed XML string representing the acknowledgment
     * @throws Exception if an error occurs during the process
     */
    public <T> String generateResponseAck(T instance) throws Exception {
        Class<?> clazz = instance.getClass();

        // Set the status code based on the class type
        if (clazz == GepgBillSubRespAck.class) {
            clazz.getMethod("setTrxStsCode", int.class).invoke(instance, 7101);
        } else if (clazz == GepgBillSubResAck.class) {
            clazz.getMethod("setTrxStsCode", int.class).invoke(instance, 7101);
        } else if (clazz == GepgPmtSpInfoAck.class) {
            clazz.getMethod("setTrxStsCode", int.class).invoke(instance, 7101);
        } else if (clazz == GepgOlPmtNtfSpInfoAck.class) {
            clazz.getMethod("setOlStsCode", int.class).invoke(instance, 7101);
        } else if (clazz == GepgSpReconcRespAck.class) {
            clazz.getMethod("setReconcStsCode", int.class).invoke(instance, 7101);
        } else {
            throw new IllegalArgumentException("Unknown response class: " + clazz.getSimpleName());
        }

        // Convert the instance to an XML string
        String ackXml = parseToXml(instance);

        // Sign the XML string
        return signMessage(ackXml, clazz);
    }

    /**
     * Generates a String representing a future date and time based on
     * the specified number of days from now.
     *
     * @param days the number of days from now to calculate the future date and time
     * @return the LocalDateTime object representing the future date and time
     *
     *         <p>
     *         Example usage:
     *         </p>
     *
     *         <pre>{@code
     * String futureDateTime = Util.getFutureDateTimeInDays(365);
     * System.out.println(Util.formatDateTime(futureDateTime));
     * }</pre>
     *
     *         <p>
     *         Example return value:
     *         </p>
     *
     *         <pre>{@code
     * "2025-07-04T00:00:00"
     * }</pre>
     */
    public String getFutureDateTimeInDays(int days) {
        return DateTimeUtil.getFutureDateTimeInDays(days);
    }

    /**
     * Generates a String representing a past date and time based on
     * the specified number of days from now.
     *
     * @param days the number of days from now to calculate the past date and time
     * @return the LocalDateTime object representing the future date and time
     *
     *         <p>
     *         Example usage:
     *         </p>
     *
     *         <pre>{@code
     * String pastDate = Util.getPastDateTimeInDays(365);
     * System.out.println(Util.formatDateTime(futureDateTime));
     * }</pre>
     *
     *         <p>
     *         Example return value:
     *         </p>
     *
     *         <pre>{@code
     * "2023-07-04T00:00:00"
     * }</pre>
     */
    public String getPastDateTimeInDays(int days) {
        return DateTimeUtil.getPastDateTimeInDays(days);
    }

    /**
     * Generates a String representing the current date and time.
     *
     * @return the LocalDateTime object representing the current date and time
     *
     *         <p>
     *         Example usage:
     *         </p>
     *
     *         <pre>{@code
     * String currentDateTime = Util.getCurrentDateTime();
     * System.out.println(Util.formatDateTime(currentDateTime));
     * }</pre>
     *
     *         <p>
     *         Example return value:
     *         </p>
     *
     *         <pre>{@code
     * "2024-07-04T00:00:00"
     * }</pre>
     */
    public String getCurrentDateTime() {
        return DateTimeUtil.getCurrentDateTime();
    }

    /*
     * ============================================================================
     * PRIVATE METHODS
     * ============================================================================
     */

    /**
     * Sends the signed request to the GePG API and returns the response.
     *
     * @param signedRequest the signed XML request
     * @param headerInfo    the GePG specific header information
     * @return the response from the GePG API
     * @throws Exception if an error occurs during the request
     */
    private String sendRequest(String submissionUrl, String signedRequest, String headerInfo) throws Exception {
        URL url = new URL(submissionUrl);
        int retryCount = 3;
        int initialDelay = 1000; // 1 second

        logger.info("SUBMISSION_URL:{}", url);

        for (int attempt = 0; attempt < retryCount; attempt++) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", CONTENT_TYPE);
                connection.setRequestProperty("Gepg-Com", headerInfo);
                connection.setRequestProperty("Gepg-Code", gepgCode);
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000); // 5 seconds
                connection.setReadTimeout(5000); // 5 seconds

                connection.getOutputStream().write(signedRequest.getBytes("UTF-8"));

                Scanner scanner = new Scanner(connection.getInputStream());
                String response = scanner.useDelimiter("\\A").next();
                scanner.close();

                return response;

            } catch (SocketTimeoutException e) {
                logger.warn("Request timed out. Attempt: {}/{}", attempt + 1, retryCount);
                if (attempt == retryCount - 1) {
                    throw new IOException("Failed after " + retryCount + " attempts", e);
                }
            } catch (IOException e) {
                logger.error("IOException occurred: ", e);
                throw e;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            // Exponential backoff
            try {
                Thread.sleep(initialDelay * (long) Math.pow(2, attempt));
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new IOException("Retry interrupted", ie);
            }
        }
        throw new IOException("Request failed after " + retryCount + " attempts.");
    }

    /**
     * Maps the XML response to an Envelope containing the specified content class
     * type.
     *
     * @param response     the XML response
     * @param contentClass the class of the content to be mapped
     * @param <T>          the type of the content class
     * @return the Envelope containing the mapped content
     * @throws Exception if an error occurs during the mapping
     */
    @SuppressWarnings("unchecked")
    private <T> Envelope<T> mapResponse(String response, Class<T> contentClass) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Envelope.class, contentClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(response);
        return (Envelope<T>) unmarshaller.unmarshal(reader);
    }
}
