package com.watabelabs.gepg;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.stream;

import java.lang.System.LoggerFinder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.watabelabs.gepg.mappers.bill.GepgBillHdr;
import com.watabelabs.gepg.mappers.bill.GepgBillItem;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReq;
import com.watabelabs.gepg.mappers.bill.GepgBillTrxInf;

import io.github.cdimascio.dotenv.Dotenv;

class GepgApiClientTest {
    private static GepgApiClient gepgApiClient;

    private static final Logger logger = LoggerFactory.getLogger(GepgApiClientTest.class);

    // Load environment variables once
    private static final Dotenv dotenv = Dotenv.load();

    @BeforeAll
    public static void setup() {
        gepgApiClient = new GepgApiClient();
    }

    @Test
    void testAllRequiredParamsAreSet() {

        String apiUrl = getEnvVariable("API_URL");
        String gepgCode = getEnvVariable("GEPG_CODE");

        String privateKeystorePath = getEnvVariable("PRIVATE_KEYSTORE_PATH");
        String privateKeystorePassword = getEnvVariable("PRIVATE_KEYSTORE_PASSWORD");
        String privateKeyAlias = getEnvVariable("PRIVATE_KEY_ALIAS");

        String publicKeystorePath = getEnvVariable("PUBLIC_KEYSTORE_PATH");
        String publicKeyAlias = getEnvVariable("PUBLIC_KEY_ALIAS");
        String publicKeystorePassword = getEnvVariable("PUBLIC_KEYSTORE_PASSWORD");

        String keystoreType = getEnvVariable("KEYSTORE_TYPE");
        String signatureAlgorithm = getEnvVariable("SIGNATURE_ALGORITHM");

        assertTrue(apiUrl != null && !apiUrl.isEmpty(), "API_URL is required.");
        assertTrue(gepgCode != null && !gepgCode.isEmpty(), "GEPG_CODE is required.");
        assertTrue(publicKeystorePassword != null && !publicKeystorePassword.isEmpty(),
                "PUBLIC_KEYSTORE_PASSWORD is required.");
        assertTrue(privateKeystorePath != null && !privateKeystorePath.isEmpty(), "PRIVATE_KEYSTORE_PATH is required.");
        assertTrue(privateKeystorePassword != null && !privateKeystorePassword.isEmpty(),
                "PRIVATE_KEYSTORE_PASSWORD is required.");
        assertTrue(privateKeyAlias != null && !privateKeyAlias.isEmpty(), "PRIVATE_KEY_ALIAS is required.");
        assertTrue(publicKeystorePath != null && !publicKeystorePath.isEmpty(), "PUBLIC_KEYSTORE_PATH is required.");
        assertTrue(publicKeyAlias != null && !publicKeyAlias.isEmpty(), "PUBLIC_KEY_ALIAS is required.");
        assertTrue(keystoreType != null && !keystoreType.isEmpty(), "KEYSTORE_TYPE is required.");
        assertTrue(signatureAlgorithm != null && !signatureAlgorithm.isEmpty(), "SIGNATURE_ALGORITHM is required.");

        assertTrue(signatureAlgorithm.equals("SHA1withRSA"), "The signature algorithm must be SHA1withRSA.");
    }

    @Test
    void testKeyPairMatch() throws Exception {
        GepgApiClient gepgApiClient = new GepgApiClient();
        boolean isMatch = gepgApiClient.checkKeyPair();
        assertTrue(isMatch, "The private key and public key should match.");
    }

    @Test
    public void testCreateSignAndVerifyBill() throws Exception {
        // Create a bill
        GepgBillSubReq bill = createActualBillWithValidSpCode();

        // Convert the bill to XML
        String billXml = gepgApiClient.convertToXmlStringWithoutDeclaration(bill);

        // Sign the XML
        String signedXml = gepgApiClient.signMessage(billXml, GepgBillSubReq.class);

        logger.info("Signed XML: {}", signedXml);

        // Verify the signed XML
        boolean isVerified = gepgApiClient.verifyMessage(signedXml, GepgBillSubReq.class);

        // Assert that the signature is valid
        assertTrue(isVerified);
    }

    private GepgBillSubReq createActualBillWithValidSpCode() {

        String spCode = getEnvVariable("SP_CODE");
        String subSpCode = getEnvVariable("SUB_SP_CODE");
        String systemId = getEnvVariable("SYSTEM_ID");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        GepgBillHdr billHdr = new GepgBillHdr(spCode, true);
        GepgBillItem item1 = new GepgBillItem("788578851", "N", 7885.0, 7885.0, 0.0, "140206");
        GepgBillItem item2 = new GepgBillItem("788578852", "N", 7885.0, 7885.0, 0.0, "140206");

        GepgBillTrxInf billTrxInf = new GepgBillTrxInf(
                UUID.fromString("11ae8614-ceda-4b32-aa83-2dc651ed4bcd"), subSpCode, systemId, 7885.0, 0.0,
                LocalDateTime.parse("2017-05-30T10:00:01", formatter), "Palapala",
                "Charles Palapala",
                "Bill Number 7885", LocalDateTime.parse("2017-02-22T10:00:10", formatter), "100", "Hashim",
                "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885.0, true, 1, Arrays.asList(item1, item2));

        return new GepgBillSubReq(billHdr, billTrxInf);
    }

    private String getEnvVariable(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isEmpty()) {
            logger.warn("Environment variable '{}' is not set!", key);
        }
        return value;
    }
}
