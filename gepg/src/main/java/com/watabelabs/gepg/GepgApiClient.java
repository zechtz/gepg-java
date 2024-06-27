package com.watabelabs.gepg;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.watabelabs.gepg.constants.GepgResponseCode;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqAck;
import com.watabelabs.gepg.mappers.bill.GepgBillSubResp;
import com.watabelabs.gepg.mappers.bill.GepgBillSubRespAck;
import com.watabelabs.gepg.mappers.payment.GepgPmtSpInfo;
import com.watabelabs.gepg.mappers.payment.GepgPmtSpInfoAck;
import com.watabelabs.gepg.mappers.reconciliation.GepgSpReconcResp;
import com.watabelabs.gepg.mappers.reconciliation.GepgSpReconcRespAck;
import com.watabelabs.gepg.utils.GepgEndpoints;
import com.watabelabs.gepg.utils.MessageUtil;
import com.watabelabs.gepg.utils.XmlUtil;

public class GepgApiClient {

    private static final Logger logger = LoggerFactory.getLogger(GepgApiClient.class);

    private String privateKeystorePath;
    private String privateKeystorePassword;
    private String privateKeyAlias;
    private String gepgCode;
    private String apiUrl;

    // these are default http headers
    private static final String CONTENT_TYPE = "Application/xml";
    private static final String GEPG_COM = "default.sp.in";

    // Load environment variables once
    private static final Dotenv dotenv = Dotenv.load();

    // No-args constructor
    public GepgApiClient() {
        this.privateKeystorePath = getEnvVariable("PRIVATE_KEYSTORE_PATH");
        this.privateKeystorePassword = getEnvVariable("PRIVATE_KEYSTORE_PASSWORD");
        this.privateKeyAlias = getEnvVariable("PRIVATE_KEY_ALIAS");
        this.gepgCode = getEnvVariable("GEPG_CODE");
        this.apiUrl = getEnvVariable("API_URL");

        // Verify file existence
        File keystoreFile = new File(this.privateKeystorePath);
        if (!keystoreFile.exists() || !keystoreFile.canRead()) {
            throw new RuntimeException("Keystore file not found or not readable: " + this.privateKeystorePath);
        }
    }

    // make this private Constructor so that it can not be instantiated
    private GepgApiClient(String endpoint) {
        this();
        this.apiUrl = this.apiUrl + endpoint;
    }

    private String getEnvVariable(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            logger.warn("Environment variable '{}' is not set!", key);
        }
        return value;
    }

    // Getters and Setters

    public String getPrivateKeystorePath() {
        return privateKeystorePath;
    }

    public void setPrivateKeystorePath(String privateKeystorePath) {
        this.privateKeystorePath = privateKeystorePath;
    }

    public String getPrivateKeystorePassword() {
        return privateKeystorePassword;
    }

    public void setPrivateKeystorePassword(String privateKeystorePassword) {
        this.privateKeystorePassword = privateKeystorePassword;
    }

    public String getPrivateKeyAlias() {
        return privateKeyAlias;
    }

    public void setPrivateKeyAlias(String privateKeyAlias) {
        this.privateKeyAlias = privateKeyAlias;
    }

    public String getGepgCode() {
        return gepgCode;
    }

    public void setGepgCode(String gepgCode) {
        this.gepgCode = gepgCode;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
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
        this.setApiUrl(apiUrl + GepgEndpoints.SUBMIT_BILL);

        // Step 1: Send the bill submission request
        String response = sendRequest(signedRequest);
        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        // Check if the response contains a valid acknowledgment
        if (billSubReqAck == null || billSubReqAck.getTrxStsCode() == 0) {
            throw new Exception("Invalid acknowledgment response");
        }

        return billSubReqAck;
    }

    /**
     * Submits a control number reuse request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck reuseControlNumber(String signedRequest) throws Exception {
        this.setApiUrl(apiUrl + GepgEndpoints.REUSE_CONTROL_NUMBER);

        // Step 1: Send the control number reuse request
        String response = sendRequest(signedRequest);
        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        // Check if the response contains a valid acknowledgment
        if (billSubReqAck == null || billSubReqAck.getTrxStsCode() == 0) {
            throw new Exception("Invalid acknowledgment response");
        }

        return billSubReqAck;
    }

    /**
     * Submits a bill change/update request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck updateBill(String signedRequest) throws Exception {
        this.setApiUrl(apiUrl + GepgEndpoints.UPDATE_BILL);

        // Step 1: Send the control number reuse request
        String response = sendRequest(signedRequest);
        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        // Check if the response contains a valid acknowledgment
        if (billSubReqAck == null || billSubReqAck.getTrxStsCode() == 0) {
            throw new Exception("Invalid acknowledgment response");
        }

        return billSubReqAck;
    }

    /**
     * Submits a bill change/update request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck cancelBill(String signedRequest) throws Exception {

        this.setApiUrl(apiUrl + GepgEndpoints.CANCEL_BILL);

        // Step 1: Send the control number reuse request
        String response = sendRequest(signedRequest);
        Envelope<GepgBillSubReqAck> envelope = mapResponse(response, GepgBillSubReqAck.class);
        GepgBillSubReqAck billSubReqAck = envelope.getContent().get(0);

        // Check if the response contains a valid acknowledgment
        if (billSubReqAck == null || billSubReqAck.getTrxStsCode() == 0) {
            throw new Exception("Invalid acknowledgment response");
        }

        return billSubReqAck;
    }

    /**
     * Submits a payment to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgPmtSpInfoAck submitPayment(String signedRequest) throws Exception {
        this.setApiUrl(apiUrl + GepgEndpoints.SEND_PAYMENT);

        // Step 1: Send the control number reuse request
        String response = sendRequest(signedRequest);
        Envelope<GepgPmtSpInfoAck> envelope = mapResponse(response, GepgPmtSpInfoAck.class);
        GepgPmtSpInfoAck paymentSpInfoAck = envelope.getContent().get(0);

        // Check if the response contains a valid acknowledgment
        if (paymentSpInfoAck == null || paymentSpInfoAck.getTrxStsCode() != 7101) {
            throw new Exception("Invalid acknowledgment response");
        }

        return paymentSpInfoAck;
    }

    /**
     * Submits a reconciliaiton request to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgSpReconcRespAck requestReconciliation(String signedRequest) throws Exception {
        this.setApiUrl(apiUrl + GepgEndpoints.REQUEST_RECONCILIATION);

        // Step 1: Send the control number reuse request
        String response = sendRequest(signedRequest);
        Envelope<GepgSpReconcRespAck> envelope = mapResponse(response, GepgSpReconcRespAck.class);
        GepgSpReconcRespAck reconciliationRespAck = envelope.getContent().get(0);

        // Check if the response contains a valid acknowledgment
        if (reconciliationRespAck == null || Integer.valueOf(reconciliationRespAck.getReconcStsCode()) != 7241) {
            throw new Exception("Invalid acknowledgment response");
        }

        return reconciliationRespAck;
    }

    /**
     * Receives the Reconciliation response as xml and return the POJO
     *
     * @param responseXml the payment notification XML response from GePG
     * @return GepgSpReconcResp the java GepgSpReconcResp class
     * @throws Exception if an error occurs during the process
     */
    public GepgSpReconcResp receiveReconciliationResponse(String responseXml) throws Exception {
        return convertToJavaObject(responseXml, GepgSpReconcResp.class);
    }

    /**
     * Receives the Payment Notification Info as xml and return the POJO
     *
     * @param responseXml the payment notification XML response from GePG
     * @return GepgPmtSpInfo the java GepgPmtSpInfo class
     * @throws Exception if an error occurs during the process
     */
    public GepgPmtSpInfo receivePaymentNotification(String responseXml) throws Exception {
        return convertToJavaObject(responseXml, GepgPmtSpInfo.class);
    }

    /**
     * Create the PaymentNotification Info Ack xml and return the String
     *
     * @param responseXml the payment notification XML response from GePG
     * @return An Xml String representing GepgPmtSpInfoAck class
     * @throws Exception if an error occurs during the process
     */
    public String getPaymentInfoAck(String responseXml) throws Exception {
        GepgPmtSpInfoAck gepgPmtSpInfoAck = new GepgPmtSpInfoAck(7101);

        String ackXml = convertToXmlString(gepgPmtSpInfoAck);

        // Initialize with the required parameters
        return signMessage(ackXml, GepgBillSubRespAck.class);
    }

    /**
     * Create the Reconciliation Response Ack xml and return the String
     *
     * @param responseXml the reconciliation response XML response from GePG
     * @return An Xml String representing GepgPmtSpInfoAck class
     * @throws Exception if an error occurs during the process
     */
    public String getReconciliationAck(String responseXml) throws Exception {
        GepgBillSubRespAck gepgBillSubRespAck = new GepgBillSubRespAck(7101);

        String ackXml = convertToXmlString(gepgBillSubRespAck);

        // Initialize with the required parameters
        return signMessage(ackXml, GepgBillSubRespAck.class);
    }

    /**
     * Receives the control number response and sends an acknowledgment back.
     *
     * @param responseXml the XML response from GePG
     * @return the signed acknowledgment XML to be sent back to GePG
     * @throws Exception if an error occurs during the process
     */
    public String receiveControlNumber(GepgBillSubResp gepgBillSubResp) throws Exception {

        // Prepare the acknowledgment response
        GepgBillSubRespAck gepgBillSubRespAck = new GepgBillSubRespAck(7101);
        // ackMapper.setTrxStsCode(responseMapper.getBillTrxInf().getTrxStsCode());

        // Convert acknowledgment to XML and sign it
        String ackXml = convertToXmlString(gepgBillSubRespAck);

        // Initialize with the required parameters
        return signMessage(ackXml, GepgBillSubRespAck.class);
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
    public <T> String signMessage(String message, Class<T> contentClass) throws Exception {
        MessageUtil messageUtil = new MessageUtil(this.privateKeystorePath, this.privateKeystorePassword,
                this.privateKeyAlias);
        return messageUtil.sign(message, contentClass);
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
    public String convertToXmlString(Object object) throws Exception {
        return XmlUtil.convertToXmlString(object);
    }

    /**
     * Converts a JAXB-annotated object to an XML string without the XML
     * declaration.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object without the XML
     *         declaration
     * @throws Exception if an error occurs during XML conversion
     */
    public String convertToXmlStringWithoutDeclaration(Object object) throws Exception {
        return XmlUtil.convertToXmlStringWithoutDeclaration(object);
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
     * @param clazz the class type to generate the acknowledgment for
     * @param <T>   the type of the class
     * @return the signed XML string representing the acknowledgment
     * @throws Exception if an error occurs during the process
     */
    public <T> String generateResponseAck(Class<T> clazz) throws Exception {
        // Create an instance of the specified class
        T instance = clazz.getDeclaredConstructor().newInstance();

        // Set the status code to 7101
        clazz.getMethod("setTrxStsCode", int.class).invoke(instance, 7101);

        // Convert the instance to an XML string
        String ackXml = convertToXmlString(instance);

        // Sign the XML string
        return signMessage(ackXml, clazz);
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
     * @return the response from the GePG API
     * @throws Exception if an error occurs during the request
     */
    private String sendRequest(String signedRequest) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", CONTENT_TYPE);
        connection.setRequestProperty("Gepg-Com", GEPG_COM);
        connection.setRequestProperty("Gepg-Code", gepgCode);
        connection.setDoOutput(true);
        connection.getOutputStream().write(signedRequest.getBytes("UTF-8"));

        Scanner scanner = new Scanner(connection.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        return response;
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
