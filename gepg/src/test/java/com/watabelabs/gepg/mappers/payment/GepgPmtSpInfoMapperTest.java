package com.watabelabs.gepg.mappers.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.watabelabs.gepg.utils.MessageUtil;
import com.watabelabs.gepg.utils.XmlUtil;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GepgPmtSpInfoMapperTest {

    private static String keystorePath;
    private static String keystorePassword;
    private static String keyAlias;

    @BeforeAll
    public static void setup() {
        try {
            // Load the keystore file from the classpath
            InputStream resourceStream = GepgPmtSpInfoMapperTest.class.getResourceAsStream("/keys/private-key.pfx");
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
    public void testPmtInfoXmlConversionWithDeclaration() throws Exception {
        // Create a sample message
        GepgPmtSpInfo pmtSpInfoMapper = createData();

        // Convert to XML string
        String message = XmlUtil.convertToXmlString(pmtSpInfoMapper);

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gepgPmtSpInfo><PymtTrxInf><TrxId>TRX123456</TrxId><SpCode>SP001</SpCode><PayRefId>PAYREF123456</PayRefId><BillId>74c7c4ee-b9d1-4a90-bb71-c999b7b6b09c</BillId><PayCtrNum>PAYCTR123456</PayCtrNum><BillAmt>1000.00</BillAmt><PaidAmt>1000.00</PaidAmt><BillPayOpt>1</BillPayOpt><CCy>TZS</CCy><TrxDtTm>2022-01-01T12:00:00</TrxDtTm><UsdPayChnl>MOBILE</UsdPayChnl><PyrCellNum>255712345678</PyrCellNum><PyrName>JohnDoe</PyrName><PyrEmail>johndoe@example.com</PyrEmail><PspReceiptNumber>PSPREC123456</PspReceiptNumber><PspName>PSPName</PspName><CtrAccNum>CTRACC123456</CtrAccNum></PymtTrxInf></gepgPmtSpInfo>";

        assertEquals(expectedXml.replaceAll("\\s+", ""), message.replaceAll("\\s+", ""));
    }

    @Test
    public void testPmtInfoConversionWithoutDeclaration() throws Exception {
        // Create a sample message
        GepgPmtSpInfo pmtSpInfoMapper = createData();

        // Convert to XML string
        String message = XmlUtil.convertToXmlStringWithoutDeclaration(pmtSpInfoMapper);

        String expectedXml = "<gepgPmtSpInfo><PymtTrxInf><TrxId>TRX123456</TrxId><SpCode>SP001</SpCode><PayRefId>PAYREF123456</PayRefId><BillId>74c7c4ee-b9d1-4a90-bb71-c999b7b6b09c</BillId><PayCtrNum>PAYCTR123456</PayCtrNum><BillAmt>1000.00</BillAmt><PaidAmt>1000.00</PaidAmt><BillPayOpt>1</BillPayOpt><CCy>TZS</CCy><TrxDtTm>2022-01-01T12:00:00</TrxDtTm><UsdPayChnl>MOBILE</UsdPayChnl><PyrCellNum>255712345678</PyrCellNum><PyrName>JohnDoe</PyrName><PyrEmail>johndoe@example.com</PyrEmail><PspReceiptNumber>PSPREC123456</PspReceiptNumber><PspName>PSPName</PspName><CtrAccNum>CTRACC123456</CtrAccNum></PymtTrxInf></gepgPmtSpInfo>";

        assertEquals(expectedXml.replaceAll("\\s+", ""), message.replaceAll("\\s+", ""));
    }

    @Test
    public void testSignedMessageContainsGepgAndSignature() throws Exception {
        // Create a sample message
        GepgPmtSpInfo pmtSpInfoMapper = createData();

        // Convert to XML string
        String message = XmlUtil.convertToXmlStringWithoutDeclaration(pmtSpInfoMapper);

        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message, GepgPmtSpInfo.class);

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
                "74c7c4ee-b9d1-4a90-bb71-c999b7b6b09c", // Example string, replace with actual if needed
                "PAYCTR123456", // payCtrNum
                1000.00, // billAmt
                1000.00, // paidAmt
                "EXACT", // billPayOpt
                "TZS", // CCy
                "2022-01-01T12:00:00", // trxDtTm
                "MOBILE", // usdPayChn
                "255712345678", // pyrCellNum
                "John Doe", // pyrName
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
