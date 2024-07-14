package com.watabelabs.gepg.mappers.reconciliation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.watabelabs.gepg.GepgApiClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GepgSpReconcRespTest {
    private static final Logger logger = LoggerFactory.getLogger(GepgSpReconcRespTest.class);
    private static GepgApiClient gepgApiClient;

    @BeforeAll
    public static void setup() {
        gepgApiClient = new GepgApiClient();
    }

    @Test
    public void testReconciliationResponseXmlCreation() throws Exception {
        GepgSpReconcResp gepgSpReconcResp = getReconciliationResponse();

        String xmlOutput = gepgApiClient.parseToXml(gepgSpReconcResp, true);

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<gepgSpReconcResp>" +
                "<ReconcBatchInfo>" +
                "<SpReconcReqId>1379794698</SpReconcReqId>" +
                "<SpCode>SP108</SpCode>" +
                "<SpName>GOV Agency</SpName>" +
                "<ReconcStsCode>GOV Agency</ReconcStsCode>" +
                "</ReconcBatchInfo>" +
                "<ReconcTrans>" +
                "<ReconcTrxInf>" +
                "<SpBillId>11ae8614-ceda-4b32-aa83-2dc651ed4bcd</SpBillId>" +
                "<BillCtrNum>991080222529</BillCtrNum>" +
                "<pspTrxId>E991080222529</pspTrxId>" +
                "<PaidAmt>37500.0</PaidAmt>" +
                "<CCy>TZS</CCy>" +
                "<PayRefId>9910222529</PayRefId>" +
                "<TrxDtTm>2017-10-09T07:35:56</TrxDtTm>" +
                "<CtrAccNum>0150211612834</CtrAccNum>" +
                "<UsdPayChnl>FAHARI HUDUMA</UsdPayChnl>" +
                "<PspName>NMB</PspName>" +
                "<PspCode>PSP006</PspCode>" +
                "<DptCellNum></DptCellNum>" +
                "<DptName></DptName>" +
                "<DptEmailAddr></DptEmailAddr>" +
                "<Remarks>Failed at PSP</Remarks>" +
                "<ReconcRvs1></ReconcRvs1>" +
                "<ReconcRvs2></ReconcRvs2>" +
                "<ReconcRvs3></ReconcRvs3>" +
                "</ReconcTrxInf>" +
                "<ReconcTrxInf>" +
                "<SpBillId>11ae8614-ceda-4b32-aa83-2dc651ed4bce</SpBillId>" +
                "<BillCtrNum>991080222520</BillCtrNum>" +
                "<pspTrxId>E991080222528</pspTrxId>" +
                "<PaidAmt>37500.0</PaidAmt>" +
                "<CCy>TZS</CCy>" +
                "<PayRefId>9910222529</PayRefId>" +
                "<TrxDtTm>2017-10-09T07:35:56</TrxDtTm>" +
                "<CtrAccNum>0150211612834</CtrAccNum>" +
                "<UsdPayChnl>FAHARI HUDUMA</UsdPayChnl>" +
                "<PspName>NMB</PspName>" +
                "<PspCode>PSP006</PspCode>" +
                "<DptCellNum></DptCellNum>" +
                "<DptName></DptName>" +
                "<DptEmailAddr></DptEmailAddr>" +
                "<Remarks>Failed at PSP</Remarks>" +
                "<ReconcRvs1></ReconcRvs1>" +
                "<ReconcRvs2></ReconcRvs2>" +
                "<ReconcRvs3></ReconcRvs3>" +
                "</ReconcTrxInf>" +
                "</ReconcTrans>" +
                "</gepgSpReconcResp>";

        assertEquals(expectedXml.replaceAll("\\s+", ""), xmlOutput.replaceAll("\\s+", ""));
    }

    @Test
    public void testReconciliationResponseXmlPojoConvertion() throws Exception {

        String receivedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<Gepg>" +
                "<gepgSpReconcResp>" +
                "<ReconcBatchInfo>" +
                "<SpReconcReqId>1379794698</SpReconcReqId>" +
                "<SpCode>SP108</SpCode>" +
                "<SpName>GOV Agency</SpName>" +
                "<ReconcStsCode>GOV Agency</ReconcStsCode>" +
                "</ReconcBatchInfo>" +
                "<ReconcTrans>" +
                "<ReconcTrxInf>" +
                "<SpBillId>11ae8614-ceda-4b32-aa83-2dc651ed4bcd</SpBillId>" +
                "<BillCtrNum>991080222529</BillCtrNum>" +
                "<pspTrxId>E991080222529</pspTrxId>" +
                "<PaidAmt>37500.0</PaidAmt>" +
                "<CCy>TZS</CCy>" +
                "<PayRefId>9910222529</PayRefId>" +
                "<TrxDtTm>2017-10-09T07:35:56</TrxDtTm>" +
                "<CtrAccNum>0150211612834</CtrAccNum>" +
                "<UsdPayChnl>FAHARI HUDUMA</UsdPayChnl>" +
                "<PspName>NMB</PspName>" +
                "<PspCode>PSP006</PspCode>" +
                "<DptCellNum></DptCellNum>" +
                "<DptName></DptName>" +
                "<DptEmailAddr></DptEmailAddr>" +
                "<Remarks>Failed at PSP</Remarks>" +
                "<ReconcRvs1></ReconcRvs1>" +
                "<ReconcRvs2></ReconcRvs2>" +
                "<ReconcRvs3></ReconcRvs3>" +
                "</ReconcTrxInf>" +
                "<ReconcTrxInf>" +
                "<SpBillId>11ae8614-ceda-4b32-aa83-2dc651ed4bce</SpBillId>" +
                "<BillCtrNum>991080222520</BillCtrNum>" +
                "<pspTrxId>E991080222528</pspTrxId>" +
                "<PaidAmt>37500.0</PaidAmt>" +
                "<CCy>TZS</CCy>" +
                "<PayRefId>9910222529</PayRefId>" +
                "<TrxDtTm>2017-10-09T07:35:56</TrxDtTm>" +
                "<CtrAccNum>0150211612834</CtrAccNum>" +
                "<UsdPayChnl>FAHARI HUDUMA</UsdPayChnl>" +
                "<PspName>NMB</PspName>" +
                "<PspCode>PSP006</PspCode>" +
                "<DptCellNum></DptCellNum>" +
                "<DptName></DptName>" +
                "<DptEmailAddr></DptEmailAddr>" +
                "<Remarks>Failed at PSP</Remarks>" +
                "<ReconcRvs1></ReconcRvs1>" +
                "<ReconcRvs2></ReconcRvs2>" +
                "<ReconcRvs3></ReconcRvs3>" +
                "</ReconcTrxInf>" +
                "</ReconcTrans>" +
                "</gepgSpReconcResp>" +
                "<gepgSignature>signature_will_go_here</gepgSignature>" +
                "</Gepg>";

        GepgSpReconcResp resp = gepgApiClient.convertToJavaObject(receivedXml, GepgSpReconcResp.class);

        assertEquals("GOV Agency", resp.getReconcBatchInfo().getSpName());
    }

    @Test
    public void testReconciliationResponseAck() throws Exception {

        GepgSpReconcRespAck gepgSpReconcRespAck = new GepgSpReconcRespAck();

        String reconciliationResponseAck = gepgApiClient.generateResponseAck(gepgSpReconcRespAck);

        logger.info("RECONCILIATION_RESP_ACK:{}", reconciliationResponseAck);

        assertNotNull(reconciliationResponseAck);
        assertTrue(reconciliationResponseAck.contains("gepgSpReconcRespAck"));
    }

    public GepgSpReconcResp getReconciliationResponse() {
        // Create ReconcBatchInfo
        GepgReconcBatchInfo batchInfo = new GepgReconcBatchInfo(
                1379794698L,
                "SP108",
                "GOV Agency",
                "GOV Agency");

        // Create list of GepgReconcTrxInf
        List<GepgReconcTrxInf> reconcTrxInfList = new ArrayList<>();

        // UUID.fromString("00000000-0000-0000-0000-0000FH467115"),
        // Create first GepgReconcTrxInf
        GepgReconcTrxInf reconcTrxInf1 = new GepgReconcTrxInf(
                UUID.fromString("11ae8614-ceda-4b32-aa83-2dc651ed4bcd"),
                991080222529L,
                "E991080222529",
                37500.0,
                "TZS",
                "9910222529",
                "2017-10-09T07:35:56",
                "0150211612834",
                "FAHARI HUDUMA",
                "NMB",
                "PSP006",
                "",
                "",
                "",
                "Failed at PSP",
                "",
                "",
                "");

        reconcTrxInfList.add(reconcTrxInf1);

        // Create second GepgReconcTrxInf
        GepgReconcTrxInf reconcTrxInf2 = new GepgReconcTrxInf(
                UUID.fromString("11ae8614-ceda-4b32-aa83-2dc651ed4bce"),
                991080222520L,
                "E991080222528",
                37500.0,
                "TZS",
                "9910222529",
                "2017-10-09T07:35:56",
                "0150211612834",
                "FAHARI HUDUMA",
                "NMB",
                "PSP006",
                "",
                "",
                "",
                "Failed at PSP",
                "",
                "",
                "");
        reconcTrxInfList.add(reconcTrxInf2);

        // Create GepgReconcTrans
        GepgReconcTrans reconcTrans = new GepgReconcTrans(reconcTrxInfList);

        // Create and return GepgSpReconcResp
        return new GepgSpReconcResp(batchInfo, reconcTrans);
    }
}
