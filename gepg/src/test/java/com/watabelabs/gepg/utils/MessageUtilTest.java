package com.watabelabs.gepg.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

import javax.validation.ValidationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.watabelabs.gepg.GepgApiClient;
import com.watabelabs.gepg.mappers.bill.GepgBillHdr;
import com.watabelabs.gepg.mappers.bill.GepgBillItem;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReq;
import com.watabelabs.gepg.mappers.bill.GepgBillTrxInf;

public class MessageUtilTest {

    private static GepgApiClient gepgApiClient;

    @BeforeAll
    public static void setup() {
        gepgApiClient = new GepgApiClient();
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load keystore", e);
        }
    }

    @Test
    public void testSignMessage() throws Exception {
        // Create a sample message
        String message = "<gepgBillSubReq><BillHdr><SpCode>S023</SpCode><RtrRespFlg>true</RtrRespFlg></BillHdr></gepgBillSubReq>";

        // Instantiate MessageUtil

        // Sign the message
        String signedMessage = gepgApiClient.signMessage(message, GepgBillSubReq.class);

        // Assert that the signed message is not null and contains the digital signature
        assertNotNull(signedMessage);
        System.out.println(signedMessage);
        assertTrue(signedMessage.contains("<gepgSignature>"));
    }

    @Test
    public void testXmlConversion() throws Exception {
        GepgBillSubReq gepgBillSubReq = createBillSubReq();

        String xmlOutput = gepgApiClient.parseToXml(gepgBillSubReq);

        // Sign the message
        String signedMessage = gepgApiClient.signMessage(xmlOutput, GepgBillSubReq.class);

        // extract <gepgSignature> from signedMessage
        String signature = signedMessage.substring(signedMessage.indexOf("<gepgSignature>") + 15,
                signedMessage.indexOf("</gepgSignature>"));

        // Assert that the signed message is not null and contains the digital signature
        assertNotNull(signedMessage);

        assertTrue(signedMessage.contains("<gepgSignature>"));

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
                "<BillExprDt>2017-05-30T10:00:01</BillExprDt>" +
                "<PyrId>Palapala</PyrId>" +
                "<PyrName>Charles Palapala</PyrName>" +
                "<BillDesc>Bill Number 7885</BillDesc>" +
                "<BillGenDt>2017-02-22T10:00:10</BillGenDt>" +
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
    public void testSignMessageWithNullInput() {
        // Sign the message with null input
        assertThrows(ValidationException.class, () -> {
            gepgApiClient.signMessage(null, GepgBillSubReq.class);
        });
    }

    @Test
    public void testSignMessageWithEmptyInput() {
        // Sign the message with empty input
        assertThrows(ValidationException.class, () -> {
            gepgApiClient.signMessage("", GepgBillSubReq.class);
        });
    }

    @Test
    public void testActualDtoWillGetMapped() throws Exception {
        // Create a sample message
        GepgBillSubReq billSubRequestMapper = createBillSubReq();

        String message = gepgApiClient.parseToXml(billSubRequestMapper);

        // Sign the message
        String signedMessage = gepgApiClient.signMessage(message, GepgBillSubReq.class);

        // Assert that the signed message is not null and contains the digital signature
        assertNotNull(signedMessage);
        System.out.println(signedMessage);
        assertTrue(signedMessage.contains("<gepgSignature>"));
    }

    private static GepgBillSubReq createBillSubReq() {

        GepgBillHdr billHdr = new GepgBillHdr("SP023", true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                UUID.fromString("11ae8614-ceda-4b32-aa83-2dc651ed4bcd"), "2001", "tjv47", 7885.0, 0.0,
                "2017-05-30T10:00:01", "Palapala",
                "Charles Palapala",
                "Bill Number 7885", "2017-02-22T10:00:10", "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, Arrays.asList(item1, item2));

        return new GepgBillSubReq(billHdr, billTrxInf);
    }

}
