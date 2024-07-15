package org.example;

import java.util.Arrays;
import java.util.UUID;

import com.watabelabs.gepg.GepgApiClient;
import com.watabelabs.gepg.constants.GepgResponseCode;
import com.watabelabs.gepg.mappers.bill.acks.GepgBillSubReqAck;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillHdr;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillItem;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillSubReq;
import com.watabelabs.gepg.mappers.bill.requests.GepgBillTrxInf;

/**
 * Main class for submitting a bill to the GePG API.
 */
public class Main {

    /**
     * Main method for executing the bill submission process.
     *
     * @param args the command line arguments
     * @throws Exception if an error occurs during the process
     */
    public static void main(String[] args) throws Exception {
        GepgApiClient gepgApiClient = new GepgApiClient();

        GepgBillSubReq billSubRequestMapper = createBillSubReq();

        String message = gepgApiClient.parseToXml(billSubRequestMapper);

        // Sign the message
        String signedMessage = gepgApiClient.signMessage(message, GepgBillSubReq.class);

        GepgBillSubReqAck respMapper = gepgApiClient.submitBill(signedMessage);

        String responseMessage = GepgResponseCode.getResponseMessage(respMapper.getTrxStsCode());

        System.out.println(responseMessage);
    }

    /**
     * Creates a sample GepgBillSubReq object for the bill submission request.
     *
     * @return the created GepgBillSubReq object
     */
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

