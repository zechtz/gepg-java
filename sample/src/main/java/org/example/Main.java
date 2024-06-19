package org.example;

import java.util.Arrays;
import java.util.Date;

import com.watabelabs.gepg.GepgRequest;
import com.watabelabs.gepg.constants.GepgResponseCode;
import com.watabelabs.gepg.mappers.bill.GepgBillHdrMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillItemMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqAckMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillSubReqMapper;
import com.watabelabs.gepg.mappers.bill.GepgBillTrxInfoMapper;
import com.watabelabs.gepg.utils.MessageUtil;
import com.watabelabs.gepg.utils.XmlUtil;

public class Main {

    private static String gepgCode = "SP1000";
    private static String apiUrl = "https://uat1.gepg.go.tz/api/sigqrequest";

    private static String keystorePath = "/data/sites/tfra/tfra-api/gepg-keys/private-key.pfx";
    private static String keystorePassword = "passpass";
    private static String keyAlias = "gepgclient";

    public static void main(String[] args) throws Exception {

        GepgBillSubReqMapper billSubRequestMapper = createData();

        String message = XmlUtil.convertToXmlString(billSubRequestMapper);

        MessageUtil messageUtil = new MessageUtil(keystorePath, keystorePassword, keyAlias);

        // Sign the message
        String signedMessage = messageUtil.sign(message);

        // Assert that the signed message is not null and contains the digital signature
        GepgRequest gRequest = new GepgRequest(gepgCode, apiUrl);

        GepgBillSubReqAckMapper respMapper = gRequest.submitBill(signedMessage);

        String responseMessage = GepgResponseCode.getResponseMessage(respMapper.getTrxStsCode());

        System.out.println(responseMessage);
    }

    private static GepgBillSubReqMapper createData() {

        // Creating and populating the Bill Header
        GepgBillHdrMapper billHdr = new GepgBillHdrMapper("SP023", true);

        // Creating and populating Bill Items
        GepgBillItemMapper item1 = new GepgBillItemMapper("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItemMapper item2 = new GepgBillItemMapper("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        // Creating and populating the Bill Transaction Information
        GepgBillTrxInfoMapper billTrxInf = new GepgBillTrxInfoMapper(
                "7885", "2001", "tjv47", 7885, 0, new Date(), "Palapala", "Charles Palapala",
                "Bill Number 7885", new Date(), "100", "Hashim", "0699210053", "charlestp@yahoo.com",
                "TZS", 7885, true, 1, Arrays.asList(item1, item2));

        // Creating and populating the Bill Submission Request
        GepgBillSubReqMapper request = new GepgBillSubReqMapper(billHdr, billTrxInf);

        // Print the object to verify the data
        return request;
    }
}
