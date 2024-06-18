package com.watabelabs.gepg.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationException;
import javax.xml.transform.stream.StreamResult;

import com.watabelabs.gepg.GepgRequest;
import com.watabelabs.gepg.mappers.bill.GepgBillHeaderMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillItemMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqAckMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubRequestMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillTrxInfoMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class MessageUtilTest {

    private static String keystorePath;
    private static String keystorePassword;
    private static String keyAlias;

    private final String gepgCode = "SP19940";
    private final String apiUrl = "https://uat1.gepg.go.tz/api/sigqrequest";

    @BeforeAll
    public static void setup() {
        keystorePath = "/data/sites/tfra/tfra-api/gepg-keys/private-key.pfx";
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
        String signedMessage = messageUtil.sign(message);

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
        MessageUtil.Envelope envelope = new MessageUtil.Envelope();
        envelope.setMessage(message);
        envelope.setDigitalSignature("dummySignature");

        // Convert to XML string
        JAXBContext context = JAXBContext.newInstance(MessageUtil.Envelope.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(envelope, new StreamResult(sw));
        String xmlString = sw.toString();

        // Expected XML structure
        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<Gepg>\n"
                + "    <message>&lt;gepgBillSubReq&gt;&lt;BillHdr&gt;&lt;SpCode&gt;S023&lt;/SpCode&gt;&lt;RtrRespFlg&gt;true&lt;/RtrRespFlg&gt;&lt;/BillHdr&gt;&lt;/gepgBillSubReq&gt;</message>\n"
                + "    <gepgSignature>dummySignature</gepgSignature>\n"
                + "</Gepg>";

        // Assert the XML string matches the expected structure
        assertEquals(expectedXml, xmlString.trim());
    }

    @Test
    public void testSignMessageWithNullInput() {
        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message with null input
        assertThrows(ValidationException.class, () -> {
            messageUtil.sign(null);
        });
    }

    @Test
    public void testSignMessageWithEmptyInput() {
        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message with empty input
        assertThrows(ValidationException.class, () -> {
            messageUtil.sign("");
        });
    }

    @Test
    @Disabled("Test is skipped because it Gepg tesing Endpoints are not provide yet")
    public void testSignAndSubmitBill() throws Exception {
        // Create a sample message
        String message = "<gepgBillSubReq><BillHdr><SpCode>S023</SpCode><RtrRespFlg>true</RtrRespFlg></BillHdr></gepgBillSubReq>";

        // Instantiate MessageUtil
        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message);

        // Instantiate GepgRequest
        GepgRequest request = new GepgRequest(gepgCode, apiUrl);

        // Submit the signed message
        GepgBillSubReqAckMapper response = request.submitBill(signedMessage);

        // Assert that the response is not null
        assertNotNull(response);
    }

    @Test
    public void testActualDtoWillGetMapped() throws Exception {
        // Create a sample message

        GepgBillSubRequestMapper billSubRequestMapper = createData();

        String message = XmlUtil.convertToXmlString(billSubRequestMapper);

        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message);

        // Assert that the signed message is not null and contains the digital signature
        assertNotNull(signedMessage);
        System.out.println(signedMessage);
        assertTrue(signedMessage.contains("<gepgSignature>"));
    }

    private static GepgBillSubRequestMapper createData() {

        // Creating and populating the Bill Header
        GepgBillHeaderMapper billHdr = new GepgBillHeaderMapper("SP023", true);

        // Creating and populating Bill Items
        GepgBillItemMapper item1 = new GepgBillItemMapper("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItemMapper item2 = new GepgBillItemMapper("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        // Creating and populating the Bill Transaction Information
        GepgBillTrxInfoMapper billTrxInf = new GepgBillTrxInfoMapper(
                "7885", "2001", "tjv47", 7885, 0, new Date(), "Palapala", "Charles Palapala",
                "Bill Number 7885", new Date(), "100", "Hashim", "0699210053", "charlestp@yahoo.com",
                "TZS", 7885, true, 1, Arrays.asList(item1, item2));

        // Creating and populating the Bill Submission Request
        GepgBillSubRequestMapper request = new GepgBillSubRequestMapper(billHdr, billTrxInf);

        // Print the object to verify the data
        return request;
    }
}
