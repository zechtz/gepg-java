package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.watabelabs.gepg.GepgRequest;
import com.watabelabs.gepg.mappers.bill.GepgBillHdr;
import com.watabelabs.gepg.mappers.bill.GepgBillItem;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReq;
import com.watabelabs.gepg.mappers.bill.GepgBillTrxInf;
import com.watabelabs.gepg.utils.MessageUtil;
import com.watabelabs.gepg.utils.XmlUtil;

public class Main {

    private static String gepgCode = "SP1000";
    private static String apiUrl = "https://uat1.gepg.go.tz/api/sigqrequest";

    private static String keystorePath = "/data/sites/tfra/tfra-api/gepg-keys/private-key.pfx";
    private static String keystorePassword = "passpass";
    private static String keyAlias = "gepgclient";

    public static void main(String[] args) throws Exception {

        GepgBillSubReq billSubRequestMapper = createData();

        String message = XmlUtil.convertToXmlString(billSubRequestMapper);

        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message, GepgBillSubReq.class);

        // Assert that the signed message is not null and contains the digital signature
        GepgRequest gRequest = new GepgRequest(gepgCode, apiUrl);

        // GepgBillSubReqAckMapper respMapper = gRequest.submitBill(signedMessage);

        // String responseMessage =
        // GepgResponseCode.getResponseMessage(respMapper.getTrxStsCode());

        System.out.println("Hello World!");
    }

    private static GepgBillSubReq createData() {

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
