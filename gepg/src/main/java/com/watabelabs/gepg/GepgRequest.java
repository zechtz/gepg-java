package com.watabelabs.gepg;

import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqAck;
import com.watabelabs.gepg.mappers.bill.GepgBillSubRespAck;
import com.watabelabs.gepg.mappers.bill.GepgBillSubResp;
import com.watabelabs.gepg.utils.MessageUtil;
import com.watabelabs.gepg.utils.XmlUtil;

public class GepgRequest {

    private final String gepgCode;
    private final String apiUrl;
    private static final String CONTENT_TYPE = "Application/xml";
    private static final String GEPG_COM = "default.sp.in";

    private static String keystorePath;
    private static String keystorePassword;
    private static String keyAlias;

    /**
     * Constructor to initialize the GepgRequest with the necessary parameters.
     *
     * @param gepgCode the GePG code
     * @param apiUrl   the API URL
     */
    public GepgRequest(String gepgCode, String apiUrl) {
        this.gepgCode = gepgCode;
        this.apiUrl = apiUrl;
    }

    private static void loadFiles() {
        try {
            // Load the keystore file from the classpath
            InputStream resourceStream = GepgRequest.class.getResourceAsStream("/keys/private-key.pfx");
            if (resourceStream == null) {
                throw new RuntimeException("Keystore file not found");
            }

            // Copy the resource to a temporary file
            Path tempFile = Files.createTempFile("private-key", ".pfx");
            Files.copy(resourceStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Set the keystore path to the temporary file location
            keystorePath = tempFile.toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load keystore", e);
        }

        keystorePassword = "passpass";
        keyAlias = "gepgclient";
    }

    /**
     * Submits a bill to the GePG API.
     *
     * @param signedRequest the signed XML request
     * @return the acknowledgment response from the GePG API
     * @throws Exception if an error occurs during the process
     */
    public GepgBillSubReqAck submitBill(String signedRequest) throws Exception {
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
     * Receives the control number response and sends an acknowledgment back.
     *
     * @param responseXml the XML response from GePG
     * @return the signed acknowledgment XML to be sent back to GePG
     * @throws Exception if an error occurs during the process
     */
    public String receiveControlNumber(String responseXml) throws Exception {
        loadFiles();

        Envelope<GepgBillSubResp> envelope = mapResponse(responseXml, GepgBillSubResp.class);
        GepgBillSubResp responseMapper = envelope.getContent().get(0);

        // Prepare the acknowledgment response
        GepgBillSubRespAck ackMapper = new GepgBillSubRespAck();
        ackMapper.setTrxStsCode(responseMapper.getBillTrxInf().getTrxStsCode());

        // Convert acknowledgment to XML and sign it
        String ackXml = XmlUtil.convertToXmlString(ackMapper);

        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias); // Initialize with the
                                                                                             // required parameters
        return messageUtil.sign(ackXml, GepgBillSubRespAck.class);
    }

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
