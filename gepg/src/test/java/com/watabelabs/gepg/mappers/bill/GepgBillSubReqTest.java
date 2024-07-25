package com.watabelabs.gepg.mappers.bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.watabelabs.gepg.GepgApiClient;
import com.watabelabs.gepg.mappers.bill.acks.GepgBillSubReqAck;
import com.watabelabs.gepg.mappers.bill.acks.GepgBillSubRespAck;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillControlNoReuse;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillHdr;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillItem;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillSubReq;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillTrxInf;
import com.watabelabs.gepg.mappers.bill.responses.GepgBillSubResp;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;

public class GepgBillSubReqTest {
    private static final Logger logger = LoggerFactory.getLogger(GepgBillSubReqTest.class);

    private static GepgApiClient gepgApiClient;
    private static CountDownLatch latch;

    // Load environment variables once
    private static final Dotenv dotenv = Dotenv.load();

    private static Javalin app;

    @BeforeAll
    public static void setup() {
        gepgApiClient = new GepgApiClient();
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

        String xmlOutput = gepgApiClient.parseToXml(gepgBillSubReq);

        logger.info("------------XML_OUTPUT-------------: {}", xmlOutput);

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
                "<BillExprDt>" + gepgApiClient.getFutureDateTimeInDays(10) + "</BillExprDt>" +
                "<PyrId>Palapala</PyrId>" +
                "<PyrName>Charles&amp;N'gombe&amp;Özil</PyrName>" +
                "<BillDesc>Bill Number 7885</BillDesc>" +
                "<BillGenDt>" + gepgApiClient.getCurrentDateTime() + "</BillGenDt>" +
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

        String xmlOutput = gepgApiClient.parseToXml(gepgBillSubReq);

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
                "<BillExprDt>" + gepgApiClient.getFutureDateTimeInDays(10) + "</BillExprDt>" +
                "<PyrId>Palapala</PyrId>" +
                "<PyrName>Charles Palapala</PyrName>" +
                "<BillDesc>Bill Number 7885</BillDesc>" +
                "<BillGenDt>" + gepgApiClient.getCurrentDateTime() + "</BillGenDt>" +
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

        String xmlString = gepgApiClient.parseToXml(gepgBillSubReqAckMapper);

        // Define expected keys to check in the signed XML
        String[] keys = { "gepgBillSubReqAck", "TrxStsCode" };

        // Check if all expected keys are present in the signed message
        boolean allKeysPresent = gepgApiClient.checkKeys(xmlString, keys);

        // Assert that all keys are present
        assertTrue(allKeysPresent, "Not all keys are present in the signed XML message.");
    }

    @Test
    public void testSignAndSubmitBillWithCallback() throws Exception {
        // Create a sample message
        GepgBillSubReq mapper = createActualBillWithValidSpCode();

        String xmlString = gepgApiClient.parseToXml(mapper);

        // Sign the message
        String signedMessage = gepgApiClient.signMessage(xmlString, GepgBillSubReq.class);

        logger.info(signedMessage);

        // Mock the GepgApiClient to simulate the server response
        GepgApiClient mockClient = mock(GepgApiClient.class);

        when(mockClient.submitBill(signedMessage)).thenReturn(createBillSubReqAck());

        // Simulate a callback from GePG system
        latch = new CountDownLatch(1);

        // Use the mocked client to submit the signed message
        GepgBillSubReqAck response = mockClient.submitBill(signedMessage);

        logger.info("Gepg Response: {}", response);

        assertNotNull(response);
        assertTrue(response.getTrxStsCode() != 0);

        // Simulate receiving a control number response from GePG system
        String controlNumberResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Gepg><gepgBillSubResp><BillTrxInf><BillId>11ae8614-ceda-4b32-aa83-2dc651ed4bcd</BillId><TrxSts>GF</TrxSts><PayCntrNum>5522023232</PayCntrNum><TrxStsCode>7242;7627</TrxStsCode></BillTrxInf><gepgSignature>...</gepgSignature></gepgBillSubResp></Gepg>";

        GepgBillSubResp gepgBillSubResp = gepgApiClient.receiveControlNumber(controlNumberResponse);

        logger.info("GepgBillSubResp: {}", gepgBillSubResp);

        String controlNumberAck = gepgApiClient.generateResponseAck(new GepgBillSubRespAck());

        assertNotNull(controlNumberAck);
        assertTrue(controlNumberAck.contains("gepgBillSubRespAck"));

        // Simulate the callback by counting down the latch
        latch.countDown();

        // Wait for callback
        latch.await();
    }

    @Test
    public void testPaymentNotificationResponseAck() throws Exception {
        String billSubRespAck = gepgApiClient.generateResponseAck(new GepgBillSubRespAck());

        logger.info("Bill_SUB_RESP_ACK:{}", billSubRespAck);

        assertNotNull(billSubRespAck);
        assertTrue(billSubRespAck.contains("gepgBillSubRespAck"));
    }

    private static GepgBillSubReqAck createBillSubReqAck() {
        return new GepgBillSubReqAck(7101);
    }

    public static String postBillResponse(GepgBillSubResp gepgResponse) throws Exception {
        GepgApiClient gepgApiClient = new GepgApiClient();
        System.out.println(gepgResponse);
        GepgBillSubRespAck billSubRespAckMapper = new GepgBillSubRespAck();

        String xmlResponse = gepgApiClient.generateResponseAck(billSubRespAckMapper);

        return xmlResponse;
    }

    private GepgBillSubReq createActualBillWithValidSpCode() {

        String spCode = getEnvVariable("SP_CODE");
        String subSpCode = getEnvVariable("SUB_SP_CODE");
        String systemId = getEnvVariable("SYSTEM_ID");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        GepgBillHdr billHdr = new GepgBillHdr(spCode, true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                "11ae8614-ceda-4b32-aa83-2dc651ed4bcd", subSpCode, systemId, 7885.0, 0.0,
                gepgApiClient.getFutureDateTimeInDays(10), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", gepgApiClient.getCurrentDateTime(), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, Arrays.asList(item1, item2));

        return new GepgBillSubReq(billHdr, billTrxInf);
    }

    private GepgBillSubReq createBillSubReq() {

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                "11ae8614-ceda-4b32-aa83-2dc651ed4bcd", "2001", "tjv47", 7885.0, 0.0,
                gepgApiClient.getFutureDateTimeInDays(10), "Palapala",
                "Charles & N'gombe & Özil",
                "Bill Number 7885", gepgApiClient.getCurrentDateTime(), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, Arrays.asList(item1, item2));

        return new GepgBillSubReq(billHdr, billTrxInf);
    }

    private static GepgBillControlNoReuse createBillControlNoReuse() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                "11ae8614-ceda-4b32-aa83-2dc651ed4bcd", "2001", "tjv47", 7885.0, 0.0,
                gepgApiClient.getFutureDateTimeInDays(10), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", gepgApiClient.getCurrentDateTime(), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, Arrays.asList(item1, item2));

        billTrxInf.setPayCntrNum("990239121373");

        return new GepgBillControlNoReuse(billHdr, billTrxInf);
    }

    private String getEnvVariable(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            logger.warn("Environment variable '{}' is not set!", key);
        }
        return value;
    }
}
