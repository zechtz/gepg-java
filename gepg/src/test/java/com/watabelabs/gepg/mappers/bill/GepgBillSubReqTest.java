package com.watabelabs.gepg.mappers.bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.watabelabs.gepg.GepgApiClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.javalin.Javalin;

public class GepgBillSubReqTest {

    private static GepgApiClient gepgApiClient;
    private static GepgBillSubResp callbackResponse;
    private static CountDownLatch latch;

    private static Javalin app;

    @BeforeAll
    public static void setup() {
        gepgApiClient = new GepgApiClient();

        app = Javalin.create().start(8080);

        // Setup the callback endpoint to handle GePG system responses
        app.post("/api/v1/bills/submit-control-number", ctx -> {
            latch.countDown();
            GepgBillSubResp response = gepgApiClient.convertToJavaObject(ctx.body(), GepgBillSubResp.class);
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
        GepgBillSubReq gepgBillSubReq = createBillSubReq();

        String xmlOutput = gepgApiClient.convertToXmlString(gepgBillSubReq);

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<gepgBillSubReq>" +
                "<BillHdr>" +
                "<SpCode>SP023</SpCode>" +
                "<RtrRespFlg>true</RtrRespFlg>" +
                "</BillHdr>" +
                "<BillTrxInf>" +
                "<BillId>11ae8614-ceda-4b32-aa83-2dc651ed4bcd</BillId>" +
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

        GepgBillControlNoReuse gepgBillSubReq = createBillControlNoReuse();

        String xmlOutput = gepgApiClient.convertToXmlStringWithoutDeclaration(gepgBillSubReq);

        String expectedXml = "<gepgBillSubReq>" +
                "<BillHdr>" +
                "<SpCode>SP023</SpCode>" +
                "<RtrRespFlg>true</RtrRespFlg>" +
                "</BillHdr>" +
                "<BillTrxInf>" +
                "<BillId>11ae8614-ceda-4b32-aa83-2dc651ed4bcd</BillId>" +
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
        GepgBillSubReqAck gepgBillSubReqAckMapper = createBillSubReqAck();

        String xmlString = gepgApiClient.convertToXmlString(gepgBillSubReqAckMapper);
        String signedMessage = gepgApiClient.signMessage(xmlString, GepgBillSubReqAck.class);

        // Define expected keys to check in the signed XML
        String[] keys = { "Gepg", "gepgBillSubReqAck", "TrxStsCode", "gepgSignature" };

        // Check if all expected keys are present in the signed message
        boolean allKeysPresent = gepgApiClient.checkKeys(signedMessage, keys);

        // Assert that all keys are present
        assertTrue(allKeysPresent, "Not all keys are present in the signed XML message.");
    }

    @Test
    public void testSignAndSubmitBillWithCallback() throws Exception {
        // Create a sample message
        GepgBillSubReq mapper = createBillSubReq();

        String xmlString = gepgApiClient.convertToXmlStringWithoutDeclaration(mapper);

        // Sign the message
        String signedMessage = gepgApiClient.signMessage(xmlString, GepgBillSubReq.class);

        System.out.println(signedMessage);

        // Simulate a callback from GePG system
        latch = new CountDownLatch(1);

        // Submit the signed message
        GepgBillSubReqAck response = gepgApiClient.submitBill(signedMessage);
        assertNotNull(response);
        assertTrue(response.getTrxStsCode() != 0);

        // Simulate receiving a control number response from GePG system
        String controlNumberResponse = "<Gepg><gepgBillSubResp><BillTrxInf><BillId>11ae8614-ceda-4b32-aa83-2dc651ed4bcd</BillId><TrxSts>GF</TrxSts><PayCntrNum>0</PayCntrNum><TrxStsCode>7242;7627</TrxStsCode></BillTrxInf><gepgSignature>...</gepgSignature></gepgBillSubResp></Gepg>";

        GepgBillSubResp gepgBillSubResp = gepgApiClient.convertToJavaObject(controlNumberResponse,
                GepgBillSubResp.class);

        String signedAckXml = gepgApiClient.receiveControlNumber(gepgBillSubResp);
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

    public static String postBillResponse(GepgBillSubResp gepgResponse) throws Exception {
        GepgApiClient gepgApiClient = new GepgApiClient();
        System.out.println(gepgResponse);
        GepgBillSubRespAck billSubRespAckMapper = new GepgBillSubRespAck(
                gepgResponse.getBillTrxInf().getTrxStsCode());

        String xmlResponse = gepgApiClient.convertToXmlString(billSubRespAckMapper);

        return xmlResponse;
    }

    private static GepgBillSubReq createBillSubReq() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                UUID.fromString("11ae8614-ceda-4b32-aa83-2dc651ed4bcd"), "2001", "tjv47", 7885.0, 0.0,
                LocalDateTime.parse("2017-05-30T10:00:01", formatter), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", LocalDateTime.parse("2017-02-22T10:00:10", formatter), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, Arrays.asList(item1, item2));

        return new GepgBillSubReq(billHdr, billTrxInf);
    }

    private static GepgBillControlNoReuse createBillControlNoReuse() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                UUID.fromString("11ae8614-ceda-4b32-aa83-2dc651ed4bcd"), "2001", "tjv47", 7885.0, 0.0,
                LocalDateTime.parse("2017-05-30T10:00:01", formatter), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", LocalDateTime.parse("2017-02-22T10:00:10", formatter), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, "990239121373", Arrays.asList(item1, item2));

        return new GepgBillControlNoReuse(billHdr, billTrxInf);
    }
}
