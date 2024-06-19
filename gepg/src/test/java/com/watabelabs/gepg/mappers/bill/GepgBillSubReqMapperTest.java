package com.watabelabs.gepg.mappers.bill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import com.watabelabs.gepg.utils.XmlUtil;

import org.junit.jupiter.api.Test;

public class GepgBillSubReqMapperTest {

    @Test
    public void textMapperToXmlConvertionWithXmlDeclaration() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        GepgBillHdrMapper billHdr = new GepgBillHdrMapper("SP023", true);
        GepgBillItemMapper item1 = new GepgBillItemMapper("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItemMapper item2 = new GepgBillItemMapper("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        GepgBillTrxInfoMapper billTrxInf = new GepgBillTrxInfoMapper(
                "7885", "2001", "tjv47", 7885, 0, sdf.parse("2017-05-30T10:00:01"), "Palapala", "Charles Palapala",
                "Bill Number 7885", sdf.parse("2017-02-22T10:00:10"), "100", "Hashim", "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885, true, 1, Arrays.asList(item1, item2));

        GepgBillSubReqMapper gepgBillSubReq = new GepgBillSubReqMapper(billHdr, billTrxInf);

        String xmlOutput = XmlUtil.convertToXmlString(gepgBillSubReq);

        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<gepgBillSubReq>" +
                "<GepgBillHeaderMapper>" +
                "<SpCode>SP023</SpCode>" +
                "<RtrRespFlg>true</RtrRespFlg>" +
                "</GepgBillHeaderMapper>" +
                "<BillTrxInf>" +
                "<BillId>7885</BillId>" +
                "<SubSpCode>2001</SubSpCode>" +
                "<SpSysId>tjv47</SpSysId>" +
                "<BillAmt>7885.0</BillAmt>" +
                "<MiscAmt>0.0</MiscAmt>" +
                "<BillExprDt>2017-05-30T10:00:01Z</BillExprDt>" +
                "<PyrId>Palapala</PyrId>" +
                "<PyrName>Charles Palapala</PyrName>" +
                "<BillDesc>Bill Number 7885</BillDesc>" +
                "<BillGenDt>2017-02-22T10:00:10Z</BillGenDt>" +
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
    public void textMapperToXmlConvertionWithoutDeclaration() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        GepgBillHdrMapper billHdr = new GepgBillHdrMapper("SP023", true);
        GepgBillItemMapper item1 = new GepgBillItemMapper("788578851", "N", 7885.00, 7885.00, 0.00, "140206");
        GepgBillItemMapper item2 = new GepgBillItemMapper("788578852", "N", 7885.00, 7885.00, 0.00, "140206");

        GepgBillTrxInfoMapper billTrxInf = new GepgBillTrxInfoMapper(
                "7885", "2001", "tjv47", 7885, 0, sdf.parse("2017-05-30T10:00:01"), "Palapala", "Charles Palapala",
                "Bill Number 7885", sdf.parse("2017-02-22T10:00:10"), "100", "Hashim", "0699210053",
                "charlestp@yahoo.com",
                "TZS", 7885, true, 1, Arrays.asList(item1, item2));

        GepgBillSubReqMapper gepgBillSubReq = new GepgBillSubReqMapper(billHdr, billTrxInf);

        String xmlOutput = XmlUtil.convertToXmlString(gepgBillSubReq);

        String expectedXml = "<gepgBillSubReq>" +
                "<GepgBillHeaderMapper>" +
                "<SpCode>SP023</SpCode>" +
                "<RtrRespFlg>true</RtrRespFlg>" +
                "</GepgBillHeaderMapper>" +
                "<BillTrxInf>" +
                "<BillId>7885</BillId>" +
                "<SubSpCode>2001</SubSpCode>" +
                "<SpSysId>tjv47</SpSysId>" +
                "<BillAmt>7885.0</BillAmt>" +
                "<MiscAmt>0.0</MiscAmt>" +
                "<BillExprDt>2017-05-30T10:00:01Z</BillExprDt>" +
                "<PyrId>Palapala</PyrId>" +
                "<PyrName>Charles Palapala</PyrName>" +
                "<BillDesc>Bill Number 7885</BillDesc>" +
                "<BillGenDt>2017-02-22T10:00:10Z</BillGenDt>" +
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
}
