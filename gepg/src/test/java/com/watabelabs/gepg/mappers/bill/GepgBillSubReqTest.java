package com.watabelabs.gepg.mappers.bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

import com.watabelabs.gepg.GepgRequest;
import com.watabelabs.gepg.utils.MessageUtil;
import com.watabelabs.gepg.utils.XmlUtil;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;

public class GepgBillSubReqTest {

    private static String keystorePath;
    private static String keystorePassword;
    private static String keyAlias;
    private static GepgBillSubResp callbackResponse;
    private static CountDownLatch latch;

    private final String gepgCode = "SP19940";
    private final String apiUrl = "http://localhost:3005/api/bill/sigqrequest";
    private static Javalin app;

    @BeforeAll
    public static void setup() {
        try {
            // Load the keystore file from the classpath
            InputStream resourceStream = GepgBillSubReqTest.class.getResourceAsStream("/keys/private-key.pfx");
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

        // Start Javalin server
        app = Javalin.create().start(9090);

        // Setup the callback endpoint to handle GePG system responses
        app.post("/api/v1/submit-control-number", ctx -> {
            latch.countDown();
            GepgBillSubResp response = MessageUtil.parseContent(ctx.body(), GepgBillSubResp.class);
            callbackResponse = response;
            ctx.result(postBillResponse(response));
        });
    }

    @AfterAll
    public static void teardown() {
        if (app != null) {
            app.stop();
        }
    }

    @Test
    public void testBillToXmlConvertion() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                "7885", "2001", "tjv47", 7885, 0, LocalDateTime.parse("2017-05-30T10:00:01", formatter), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", LocalDateTime.parse("2017-02-22T10:00:10", formatter), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885, true, 1, Arrays.asList(item1, item2));

        GepgBillSubReq gepgBillSubReq = new GepgBillSubReq(billHdr, billTrxInf);

        String xmlOutput = XmlUtil.convertToXmlString(gepgBillSubReq);

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<gepgBillSubReq>" +
                "<BillHdr>" +
                "<SpCode>SP023</SpCode>" +
                "<RtrRespFlg>true</RtrRespFlg>" +
                "</BillHdr>" +
                "<BillTrxInf>" +
                "<BillId>7885</BillId>" +
                "<SubSpCode>2001</SubSpCode>" +
                "<SpSysId>tjv47</SpSysId>" +
                "<BillAmt>7885.0</BillAmt>" +
                "<MiscAmt>0.0</MiscAmt>" +
                "<BillExprDt>2017-05-30T10:00:01Z</BillExprDt>" +
                "<PyrId>Palapala</PyrId>" +
                "<PyrName>Charles Palapala</PyrName>" +
                "<BillDesc>Bill Number 7885</BillDesc>" +
                "<BillGenDt>2017-02-22T10:00:10Z</BillGenDt>" +
                "<BillGenBy>100</BillGenBy>" +
                "<BillApprBy>Hashim</BillApprBy>" +
                "<PyrCellNum>0699210053</PyrCellNum>" +
                "<PyrEmail>charlestp@yahoo.com</PyrEmail>" +
                "<Ccy>TZS</Ccy>" +
                "<BillEqvAmt>7885.0</BillEqvAmt>" +
                "<RemFlag>true</RemFlag>" +
                "<BillPayOpt>1</BillPayOpt>" +
                "<BillItems>" +
                "<BillItem>" +
                "<BillItemRef>788578851</BillItemRef>" +
                "<UseItemRefOnPay>N</UseItemRefOnPay>" +
                "<BillItemAmt>7885.0</BillItemAmt>" +
                "<BillItemEqvAmt>7885.0</BillItemEqvAmt>" +
                "<BillItemMiscAmt>0.0</BillItemMiscAmt>" +
                "<GfsCode>140206</GfsCode>" +
                "</BillItem>" +
                "<BillItem>" +
                "<BillItemRef>788578852</BillItemRef>" +
                "<UseItemRefOnPay>N</UseItemRefOnPay>" +
                "<BillItemAmt>7885.0</BillItemAmt>" +
                "<BillItemEqvAmt>7885.0</BillItemEqvAmt>" +
                "<BillItemMiscAmt>0.0</BillItemMiscAmt>" +
                "<GfsCode>140206</GfsCode>" +
                "</BillItem>" +
                "</BillItems>" +
                "</BillTrxInf>" +
                "</gepgBillSubReq>";

        assertEquals(expectedXml.replaceAll("\\s+", ""), xmlOutput.replaceAll("\\s+", ""));
    }

    @Test
    public void testControlNumberReuseConvertionToXml() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                "7885", "2001", "tjv47", 7885, 0, LocalDateTime.parse("2017-05-30T10:00:01", formatter), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", LocalDateTime.parse("2017-02-22T10:00:10", formatter), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885, true, 1, "990239121373", Arrays.asList(item1, item2));

        GepgBillSubReq gepgBillSubReq = new GepgBillSubReq(billHdr, billTrxInf);

        String xmlOutput = XmlUtil.convertToXmlStringWithoutDeclaration(gepgBillSubReq);

        String expectedXml = "<gepgBillSubReq>" +
                "<BillHdr>" +
                "<SpCode>SP023</SpCode>" +
                "<RtrRespFlg>true</RtrRespFlg>" +
                "</BillHdr>" +
                "<BillTrxInf>" +
                "<BillId>7885</BillId>" +
                "<SubSpCode>2001</SubSpCode>" +
                "<SpSysId>tjv47</SpSysId>" +
                "<BillAmt>7885.0</BillAmt>" +
                "<MiscAmt>0.0</MiscAmt>" +
                "<BillExprDt>2017-05-30T10:00:01Z</BillExprDt>" +
                "<PyrId>Palapala</PyrId>" +
                "<PyrName>Charles Palapala</PyrName>" +
                "<BillDesc>Bill Number 7885</BillDesc>" +
                "<BillGenDt>2017-02-22T10:00:10Z</BillGenDt>" +
                "<BillGenBy>100</BillGenBy>" +
                "<BillApprBy>Hashim</BillApprBy>" +
                "<PyrCellNum>0699210053</PyrCellNum>" +
                "<PyrEmail>charlestp@yahoo.com</PyrEmail>" +
                "<Ccy>TZS</Ccy>" +
                "<BillEqvAmt>7885.0</BillEqvAmt>" +
                "<RemFlag>true</RemFlag>" +
                "<BillPayOpt>1</BillPayOpt>" +
                "<PayCntrNum>990239121373</PayCntrNum>" +
                "<BillItems>" +
                "<BillItem>" +
                "<BillItemRef>788578851</BillItemRef>" +
                "<UseItemRefOnPay>N</UseItemRefOnPay>" +
                "<BillItemAmt>7885.0</BillItemAmt>" +
                "<BillItemEqvAmt>7885.0</BillItemEqvAmt>" +
                "<BillItemMiscAmt>0.0</BillItemMiscAmt>" +
                "<GfsCode>140206</GfsCode>" +
                "</BillItem>" +
                "<BillItem>" +
                "<BillItemRef>788578852</BillItemRef>" +
                "<UseItemRefOnPay>N</UseItemRefOnPay>" +
                "<BillItemAmt>7885.0</BillItemAmt>" +
                "<BillItemEqvAmt>7885.0</BillItemEqvAmt>" +
                "<BillItemMiscAmt>0.0</BillItemMiscAmt>" +
                "<GfsCode>140206</GfsCode>" +
                "</BillItem>" +
                "</BillItems>" +
                "</BillTrxInf>" +
                "</gepgBillSubReq>";

        assertEquals(expectedXml.replaceAll("\\s+", ""), xmlOutput.replaceAll("\\s+", ""));
    }

    @Test
    public void testCorrectGepgBillSubReqAck() throws Exception {

        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);
        GepgBillSubReqAck gepgBillSubReqAckMapper = createBillSubReqAck();

        String xmlString = XmlUtil.convertToXmlString(gepgBillSubReqAckMapper);
        String signedMessage = messageUtil.sign(xmlString, GepgBillSubReqAck.class);

        // Define expected keys to check in the signed XML
        String[] keys = { "Gepg", "gepgBillSubReqAck", "TrxStsCode", "gepgSignature" };

        // Check if all expected keys are present in the signed message
        boolean allKeysPresent = XmlUtil.checkKeys(signedMessage, keys);

        // Assert that all keys are present
        assertTrue(allKeysPresent, "Not all keys are present in the signed XML message.");
    }

    @Test
    @Disabled
    public void testSignAndSubmitBillWithCallback() throws Exception {
        // Create a sample message
        GepgBillSubReq mapper = createBillSubReq();

        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        String xmlString = XmlUtil.convertToXmlStringWithoutDeclaration(mapper);

        // Sign the message
        String signedMessage = messageUtil.sign(xmlString, GepgBillSubReq.class);

        System.out.println(signedMessage);

        GepgRequest request = new GepgRequest(gepgCode, apiUrl);

        // Simulate a callback from GePG system
        latch = new CountDownLatch(1);

        // Submit the signed message
        GepgBillSubReqAck response = request.submitBill(signedMessage);
        assertNotNull(response);
        assertTrue(response.getTrxStsCode() != 0);

        // Simulate receiving a control number response from GePG system
        String controlNumberResponse = "<Gepg><gepgBillSubResp><BillTrxInf><BillId>7885</BillId><TrxSts>GF</TrxSts><PayCntrNum>0</PayCntrNum><TrxStsCode>7242;7627</TrxStsCode></BillTrxInf><gepgSignature>...</gepgSignature></gepgBillSubResp></Gepg>";
        String signedAckXml = request.receiveControlNumber(controlNumberResponse);
        assertNotNull(signedAckXml);
        assertTrue(signedAckXml.contains("gepgBillSubRespAck"));

        // Wait for callback
        latch.await();
    }

    private static GepgBillSubRespAck createBillSubRespAck() {
        return new GepgBillSubRespAck(7101);
    }

    private static GepgBillSubReqAck createBillSubReqAck() {
        return new GepgBillSubReqAck(7101);
    }

    private GepgBillControlNoReuse createBillControlNoReuse() {

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
                "TZS", 7885, true, 1, "55052", Arrays.asList(item1, item2));

        // Creating and populating the Bill Submission Request
        GepgBillControlNoReuse request = new GepgBillControlNoReuse(billHdr, billTrxInf);

        // Print the object to verify the data
        return request;

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

    public static String postBillResponse(GepgBillSubResp gepgResponse) throws Exception {
        System.out.println(gepgResponse);
        GepgBillSubRespAck billSubRespAckMapper = new GepgBillSubRespAck(
                gepgResponse.getBillTrxInf().getTrxStsCode());

        String xmlResponse = XmlUtil.convertToXmlString(billSubRespAckMapper);

        return xmlResponse;
    }
}
