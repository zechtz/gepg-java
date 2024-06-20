package com.watabelabs.gepg.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationException;
import javax.xml.transform.stream.StreamResult;

import com.watabelabs.gepg.GepgRequest;
import com.watabelabs.gepg.constants.GepgResponseCode;
import com.watabelabs.gepg.mappers.bill.GepgBillHdrMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillItemMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqAckMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubRespAckMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubRespMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillTrxInfoMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import spark.Spark;

public class MessageUtilTest {

    private static String keystorePath;
    private static String keystorePassword;
    private static String keyAlias;
    private static GepgBillSubRespMapper callbackResponse;
    private static CountDownLatch latch;

    private final String gepgCode = "SP19940";
    private final String apiUrl = "http://localhost:3005/api/bill/sigqrequest";

    @BeforeAll
    public static void setup() {
        try {
            // Load the keystore file from the classpath
            InputStream resourceStream = MessageUtilTest.class.getResourceAsStream("/keys/private-key.pfx");
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

        // Start SparkJava server
        Spark.port(4040);

        // Setup the callback endpoint to handle GePG system responses
        Spark.post("/api/callback", (req, res) -> {
            MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

            // convert back to Pojo
            GepgBillSubRespMapper callbackResponse = messageUtil.unwrapAndConvertToPojo(req.body(),
                    GepgBillSubRespMapper.class);

            latch.countDown();

            String _apiUrl = "http://localhost:3005/api/bill/sigqrequest";

            // Prepare the acknowledgment response
            GepgBillSubRespAckMapper ack = new GepgBillSubRespAckMapper("7101");
            String ackXmlString = XmlUtil.convertToXmlString(ack);

            String signedMessage = messageUtil.sign(ackXmlString, GepgBillSubRespAckMapper.class);

            // Send the acknowledgment back to GePG
            GepgRequest gepgRequest = new GepgRequest("Gepg_Code", _apiUrl);

            gepgRequest.submitBillAck(signedMessage);

            return "Received";
        });

        Spark.awaitInitialization();
    }

    @AfterAll
    public static void teardown() {
        Spark.stop();
    }

    @Test
    public void testSignMessage() throws Exception {
        // Create a sample message
        String message = "<gepgBillSubReq><BillHdr><SpCode>S023</SpCode><RtrRespFlg>true</RtrRespFlg></BillHdr></gepgBillSubReq>";

        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message, GepgBillSubReqMapper.class);

        // Assert that the signed message is not null and contains the digital signature
        assertNotNull(signedMessage);
        System.out.println(signedMessage);
        assertTrue(signedMessage.contains("<gepgSignature>"));
    }

    @Test
    public void testXmlConversion() throws Exception {
        // Create a sample message
        String message = "<gepgBillSubReq><BillHdr><SpCode>S023</SpCode><RtrRespFlg>true</RtrRespFlg></BillHdr></gepgBillSubReq>";

        // Instantiate MessageUtil
        MessageUtil.Envelope<GepgBillSubReqMapper> envelope = new MessageUtil.Envelope<>();
        GepgBillSubReqMapper billSubReq = new GepgBillSubReqMapper();
        envelope.setContent(Arrays.asList(billSubReq));
        envelope.setGepgSignature("dummySignature");

        // Convert to XML string
        JAXBContext context = JAXBContext.newInstance(MessageUtil.Envelope.class, GepgBillSubReqMapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(envelope, new StreamResult(sw));
        String xmlString = sw.toString();

        // Expected XML structure
        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<Gepg>\n"
                + "    <gepgBillSubReq/>\n"
                + "    <gepgSignature>dummySignature</gepgSignature>\n"
                + "</Gepg>";

        // Assert the XML string matches the expected structure
        assertEquals(expectedXml.replaceAll("\\s+", ""), xmlString.trim().replaceAll("\\s+", ""));
    }

    @Test
    public void testSignMessageWithNullInput() {
        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message with null input
        assertThrows(ValidationException.class, () -> {
            messageUtil.sign(null, GepgBillSubReqMapper.class);
        });
    }

    @Test
    public void testSignMessageWithEmptyInput() {
        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message with empty input
        assertThrows(ValidationException.class, () -> {
            messageUtil.sign("", GepgBillSubReqMapper.class);
        });
    }

    @Test
    public void testSignAndSubmitBillWithCallback() throws Exception {
        // Create a sample message
        GepgBillSubReqMapper mapper = createData();

        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        String xmlString = XmlUtil.convertToXmlStringWithoutDeclaration(mapper);

        // Sign the message
        String signedMessage = messageUtil.sign(xmlString, GepgBillSubReqMapper.class);

        System.out.println(signedMessage);

        GepgRequest request = new GepgRequest("Gepg_Code", apiUrl);

        // Simulate a callback from GePG system
        latch = new CountDownLatch(1);

        // Submit the signed message
        GepgBillSubReqAckMapper response = request.submitBill(signedMessage);

        // Submit the GepgBillSubReqAckMapper

        // Assert that the response is not null
        assertNotNull(response);
        assertEquals("00", response.getTrxStsCode());
        assertEquals("Success", GepgResponseCode.getResponseMessage(response.getTrxStsCode()));

        // Wait for callback
        latch.await();

        // Assert that the callback response is received and contains the expected data
        assertNotNull(callbackResponse);
        assertEquals("123456789", callbackResponse.getBillTrxInf().getBillAmt());
    }

    @Test
    public void testActualDtoWillGetMapped() throws Exception {
        // Create a sample message
        GepgBillSubReqMapper billSubRequestMapper = createData();

        String message = XmlUtil.convertToXmlString(billSubRequestMapper);

        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message, GepgBillSubReqMapper.class);

        // Assert that the signed message is not null and contains the digital signature
        assertNotNull(signedMessage);
        System.out.println(signedMessage);
        assertTrue(signedMessage.contains("<gepgSignature>"));
    }

    private static GepgBillSubReqMapper createData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Creating and populating the Bill Header
        GepgBillHdrMapper billHdr = new GepgBillHdrMapper("SP023", true);

        // Creating and populating Bill Items
        GepgBillItemMapper item1 = new GepgBillItemMapper("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItemMapper item2 = new GepgBillItemMapper("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        LocalDateTime billExprDt = LocalDateTime.parse("2017-05-30T10:00:01", formatter);
        LocalDateTime billGenDt = LocalDateTime.parse("2017-02-22T10:00:10", formatter);

        // Creating and populating the Bill Transaction Information
        GepgBillTrxInfoMapper billTrxInf = new GepgBillTrxInfoMapper(
                "7885", "2001", "tjv47", 7885, 0, billGenDt, "Palapala", "Charles Palapala",
                "Bill Number 7885", billExprDt, "100", "Hashim", "0699210053", "charlestp@yahoo.com",
                "TZS", 7885, true, 1, Arrays.asList(item1, item2));

        // Creating and populating the Bill Submission Request
        GepgBillSubReqMapper request = new GepgBillSubReqMapper(billHdr, billTrxInf);

        // Print the object to verify the data
        return request;
    }
}
