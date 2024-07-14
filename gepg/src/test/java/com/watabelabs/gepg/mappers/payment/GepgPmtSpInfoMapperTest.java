package com.watabelabs.gepg.mappers.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.watabelabs.gepg.GepgApiClient;

public class GepgPmtSpInfoMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(GepgPmtSpInfoMapperTest.class);

    private static GepgApiClient gepgApiClient;

    @BeforeAll
    public static void setup() {
        gepgApiClient = new GepgApiClient();
    }

    @Test
    public void testPmtInfoXmlConversionWithDeclaration() throws Exception {
        // Create a sample message
        GepgPmtSpInfo pmtSpInfoMapper = createData();

        // Convert to XML string
        String message = gepgApiClient.parseToXml(pmtSpInfoMapper, true);

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gepgPmtSpInfo><PymtTrxInf><TrxId>TRX123456</TrxId><SpCode>SP001</SpCode><PayRefId>PAYREF123456</PayRefId><BillId>74c7c4ee-b9d1-4a90-bb71-c999b7b6b09c</BillId><PayCtrNum>PAYCTR123456</PayCtrNum><BillAmt>1000.0</BillAmt><PaidAmt>1000.0</PaidAmt><BillPayOpt>1</BillPayOpt><CCy>TZS</CCy><TrxDtTm>2022-01-01T12:00:00</TrxDtTm><UsdPayChnl>MOBILE</UsdPayChnl><PyrCellNum>255712345678</PyrCellNum><PyrName>JohnDoe</PyrName><PyrEmail>johndoe@example.com</PyrEmail><PspReceiptNumber>PSPREC123456</PspReceiptNumber><PspName>PSPName</PspName><CtrAccNum>CTRACC123456</CtrAccNum></PymtTrxInf></gepgPmtSpInfo>";

        assertEquals(expectedXml.replaceAll("\\s+", ""), message.replaceAll("\\s+", ""));
    }

    @Test
    public void testOnlinePaymentNotificationAck() throws Exception {
        String gepgOlPmtNtfSpInfoAck = gepgApiClient.generateResponseAck(new GepgOlPmtNtfSpInfoAck());

        logger.info("ONLINE_PMT_INFO_ACK:{}", gepgOlPmtNtfSpInfoAck);

        assertNotNull(gepgOlPmtNtfSpInfoAck);
        assertTrue(gepgOlPmtNtfSpInfoAck.contains("gepgOlPmtNtfSpAck"));
    }

    @Test
    public void testPmtInfoConversionWithoutDeclaration() throws Exception {
        // Create a sample message
        GepgPmtSpInfo pmtSpInfoMapper = createData();

        // Convert to XML string
        String message = gepgApiClient.parseToXml(pmtSpInfoMapper);

        String expectedXml = "<gepgPmtSpInfo><PymtTrxInf><TrxId>TRX123456</TrxId><SpCode>SP001</SpCode><PayRefId>PAYREF123456</PayRefId><BillId>74c7c4ee-b9d1-4a90-bb71-c999b7b6b09c</BillId><PayCtrNum>PAYCTR123456</PayCtrNum><BillAmt>1000.0</BillAmt><PaidAmt>1000.0</PaidAmt><BillPayOpt>1</BillPayOpt><CCy>TZS</CCy><TrxDtTm>2022-01-01T12:00:00</TrxDtTm><UsdPayChnl>MOBILE</UsdPayChnl><PyrCellNum>255712345678</PyrCellNum><PyrName>JohnDoe</PyrName><PyrEmail>johndoe@example.com</PyrEmail><PspReceiptNumber>PSPREC123456</PspReceiptNumber><PspName>PSPName</PspName><CtrAccNum>CTRACC123456</CtrAccNum></PymtTrxInf></gepgPmtSpInfo>";

        assertEquals(expectedXml.replaceAll("\\s+", ""), message.replaceAll("\\s+", ""));
    }

    @Test
    public void testSignedMessageContainsGepgAndSignature() throws Exception {
        GepgApiClient gepgApiClient = new GepgApiClient();
        // Create a sample message
        GepgPmtSpInfo pmtSpInfoMapper = createData();

        // Convert to XML string
        String message = gepgApiClient.parseToXml(pmtSpInfoMapper);

        System.out.println("THE_CONVERTED_MESSAGE" + message);

        // Sign the message
        String signedMessage = gepgApiClient.signMessage(message, GepgPmtSpInfo.class);

        System.out.println("THE_SIGNED_MESSAGE" + signedMessage);

        // Assert that the signed message is not null and contains <Gepg> and
        // <gepgSignature>
        assertNotNull(signedMessage);
        System.out.println(signedMessage);
        assertTrue(signedMessage.contains("<Gepg>"));
        assertTrue(signedMessage.contains("<gepgSignature>"));
    }

    private static GepgPmtSpInfo createData() {
        // Creating and populating the Payment Transaction Information

        GepgPymtTrxInf pymtTrxInf = new GepgPymtTrxInf(
                "TRX123456", // trxId
                "SP001", // spCode
                "PAYREF123456", // payRefId
                UUID.fromString("74c7c4ee-b9d1-4a90-bb71-c999b7b6b09c"), // billId
                "PAYCTR123456", // payCtrNum
                1000.0, // billAmt
                1000.0, // paidAmt
                "1", // billPayOptString (corresponds to FULL payment option)
                "TZS", // CCy
                "2022-01-01T12:00:00", // trxDtTm
                "MOBILE", // usdPayChn
                "255712345678", // pyrCellNum
                "JohnDoe", // pyrName
                "johndoe@example.com", // pyrEmail
                "PSPREC123456", // pspReceiptNumber
                "PSPName", // pspName
                "CTRACC123456" // ctrAccNum
        );
        // Creating and populating the Payment Service Provider Information
        GepgPmtSpInfo pmtSpInfo = new GepgPmtSpInfo(pymtTrxInf);

        // Print the object to verify the data
        System.out.println(pmtSpInfo);

        return pmtSpInfo;
    }
}
