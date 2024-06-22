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

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.ValidationException;
import javax.xml.transform.stream.StreamResult;

import com.watabelabs.gepg.mappers.bill.GepgBillHdr;
import com.watabelabs.gepg.mappers.bill.GepgBillItem;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReq;
import com.watabelabs.gepg.mappers.bill.GepgBillSubResp;
import com.watabelabs.gepg.mappers.bill.GepgBillTrxInf;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;

public class MessageUtilTest {

    private static String keystorePath;
    private static String keystorePassword;
    private static String keyAlias;
    private static GepgBillSubResp callbackResponse;
    private static CountDownLatch latch;

    private static Javalin app;

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
    }

    @Test
    public void testSignMessage() throws Exception {
        // Create a sample message
        String message = "<gepgBillSubReq><BillHdr><SpCode>S023</SpCode><RtrRespFlg>true</RtrRespFlg></BillHdr></gepgBillSubReq>";

        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message, GepgBillSubReq.class);

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
        MessageUtil.Envelope<GepgBillSubReq> envelope = new MessageUtil.Envelope<>();
        GepgBillSubReq billSubReq = new GepgBillSubReq();
        envelope.setContent(Arrays.asList(billSubReq));
        envelope.setGepgSignature("dummySignature");

        // Convert to XML string
        JAXBContext context = JAXBContext.newInstance(MessageUtil.Envelope.class, GepgBillSubReq.class);
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
            messageUtil.sign(null, GepgBillSubReq.class);
        });
    }

    @Test
    public void testSignMessageWithEmptyInput() {
        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message with empty input
        assertThrows(ValidationException.class, () -> {
            messageUtil.sign("", GepgBillSubReq.class);
        });
    }

    @Test
    public void testActualDtoWillGetMapped() throws Exception {
        // Create a sample message
        GepgBillSubReq billSubRequestMapper = createBillSubReq();

        String message = XmlUtil.convertToXmlString(billSubRequestMapper);

        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message, GepgBillSubReq.class);

        // Assert that the signed message is not null and contains the digital signature
        assertNotNull(signedMessage);
        System.out.println(signedMessage);
        assertTrue(signedMessage.contains("<gepgSignature>"));
    }

    private static GepgBillSubReq createBillSubReq() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Creating and populating the Bill Header
        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);

        // Creating and populating Bill Items
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        LocalDateTime billExprDt = LocalDateTime.parse("2017-05-30T10:00:01", formatter);
        LocalDateTime billGenDt = LocalDateTime.parse("2017-02-22T10:00:10", formatter);

        // Creating and populating the Bill Transaction Information
        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                "7885", "2001", "tjv47", 7885, 0, billGenDt, "Palapala", "Charles Palapala",
                "Bill Number 7885", billExprDt, "100", "Hashim", "0699210053", "charlestp@yahoo.com",
                "TZS", 7885, true, 1, Arrays.asList(item1, item2));

        // Creating and populating the Bill Submission Request
        GepgBillSubReq request = new GepgBillSubReq(billHdr, billTrxInf);

        // Print the object to verify the data
        return request;
    }

}
