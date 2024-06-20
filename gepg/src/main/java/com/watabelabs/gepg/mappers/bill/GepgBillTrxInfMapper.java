package com.watabelabs.gepg.mappers.bill;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillTrxInfMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the bill transaction
 * information.
 */
@XmlRootElement(name = "BillTrxInf")
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillTrxInfMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "BillId")
    private String billId;

    @XmlElement(name = "TrxSts")
    private String trxSts;

    @XmlElement(name = "PayCntrNum")
    private String payCntrNum;

    @XmlElement(name = "TrxStsCode")
    private String trxStsCode;

    // Default no-argument constructor
    public GepgBillTrxInfMapper() {
    }

    // Parameterized constructor
    public GepgBillTrxInfMapper(String billId, String trxSts, String payCntrNum, String trxStsCode) {
        this.billId = billId;
        this.trxSts = trxSts;
        this.payCntrNum = payCntrNum;
        this.trxStsCode = trxStsCode;
    }

    // Getters and setters
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getTrxSts() {
        return trxSts;
    }

    public void setTrxSts(String trxSts) {
        this.trxSts = trxSts;
    }

    public String getPayCntrNum() {
        return payCntrNum;
    }

    public void setPayCntrNum(String payCntrNum) {
        this.payCntrNum = payCntrNum;
    }

    public String getTrxStsCode() {
        return trxStsCode;
    }

    public void setTrxStsCode(String trxStsCode) {
        this.trxStsCode = trxStsCode;
    }

    @Override
    public String toString() {
        return "GepgBillTrxInfMapper{" +
                "billId='" + billId + '\'' +
                ", trxSts='" + trxSts + '\'' +
                ", payCntrNum='" + payCntrNum + '\'' +
                ", trxStsCode='" + trxStsCode + '\'' +
                '}';
    }
}
