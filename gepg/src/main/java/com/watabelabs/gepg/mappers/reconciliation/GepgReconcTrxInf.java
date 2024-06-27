package com.watabelabs.gepg.mappers.reconciliation;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The ReconcTrxInf class is used for mapping the XML response
 * to the corresponding Java object. This class represents the reconciliation
 * transaction information.
 *
 * <p>
 * It contains fields that store details of a reconciliation transaction, such
 * as bill ID, payment reference ID, payment control number, paid amount,
 * currency,
 * transaction date and time, control account number, used payment channel,
 * payer's cell number, payer's name, payer's email, remarks, and various
 * reconciliation
 * reversals.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * the root element name and {@link XmlAccessorType} to specify the access type
 * for
 * the fields.
 * </p>
 */
@XmlRootElement(name = "ReconcTrxInf")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgReconcTrxInf {

    @XmlElement(name = "SpBillId")
    private UUID spBillId;

    @XmlElement(name = "BillCtrNum")
    private Long billCtrNum;

    @XmlElement(name = "pspTrxId")
    private String pspTrxId;

    @XmlElement(name = "PaidAmt")
    private Double paidAmt;

    @XmlElement(name = "CCy")
    private String cCy;

    @XmlElement(name = "PayRefId")
    private String payRefId;

    @XmlElement(name = "TrxDtTm")
    private String trxDtTm;

    @XmlElement(name = "CtrAccNum")
    private String ctrAccNum;

    @XmlElement(name = "UsdPayChnl")
    private String usdPayChnl;

    @XmlElement(name = "PspName")
    private String pspName;

    @XmlElement(name = "PspCode")
    private String pspCode;

    @XmlElement(name = "DptCellNum")
    private String dptCellNum;

    @XmlElement(name = "DptName")
    private String dptName;

    @XmlElement(name = "DptEmailAddr")
    private String dptEmailAddr;

    @XmlElement(name = "Remarks")
    private String remarks;

    @XmlElement(name = "ReconcRvs1")
    private String reconcRvs1;

    @XmlElement(name = "ReconcRvs2")
    private String reconcRvs2;

    @XmlElement(name = "ReconcRvs3")
    private String reconcRvs3;

    // Default no-argument constructor
    public GepgReconcTrxInf() {
    }

    // Parameterized constructor to initialize the object with the provided values
    public GepgReconcTrxInf(UUID spBillId, Long billCtrNum, String pspTrxId, Double paidAmt, String cCy,
            String payRefId, String trxDtTm, String ctrAccNum, String usdPayChnl, String pspName,
            String pspCode, String dptCellNum, String dptName, String dptEmailAddr, String remarks,
            String reconcRvs1, String reconcRvs2, String reconcRvs3) {
        this.spBillId = spBillId;
        this.billCtrNum = billCtrNum;
        this.pspTrxId = pspTrxId;
        this.paidAmt = paidAmt;
        this.cCy = cCy;
        this.payRefId = payRefId;
        this.trxDtTm = trxDtTm;
        this.ctrAccNum = ctrAccNum;
        this.usdPayChnl = usdPayChnl;
        this.pspName = pspName;
        this.pspCode = pspCode;
        this.dptCellNum = dptCellNum;
        this.dptName = dptName;
        this.dptEmailAddr = dptEmailAddr;
        this.remarks = remarks;
        this.reconcRvs1 = reconcRvs1;
        this.reconcRvs2 = reconcRvs2;
        this.reconcRvs3 = reconcRvs3;
    }

    // Getter and setter methods
    public UUID getSpBillId() {
        return spBillId;
    }

    public void setSpBillId(UUID spBillId) {
        this.spBillId = spBillId;
    }

    public Long getBillCtrNum() {
        return billCtrNum;
    }

    public void setBillCtrNum(Long billCtrNum) {
        this.billCtrNum = billCtrNum;
    }

    public String getPspTrxId() {
        return pspTrxId;
    }

    public void setPspTrxId(String pspTrxId) {
        this.pspTrxId = pspTrxId;
    }

    public Double getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(Double paidAmt) {
        this.paidAmt = paidAmt;
    }

    public String getCCy() {
        return cCy;
    }

    public void setCCy(String cCy) {
        this.cCy = cCy;
    }

    public String getPayRefId() {
        return payRefId;
    }

    public void setPayRefId(String payRefId) {
        this.payRefId = payRefId;
    }

    public String getTrxDtTm() {
        return trxDtTm;
    }

    public void setTrxDtTm(String trxDtTm) {
        this.trxDtTm = trxDtTm;
    }

    public String getCtrAccNum() {
        return ctrAccNum;
    }

    public void setCtrAccNum(String ctrAccNum) {
        this.ctrAccNum = ctrAccNum;
    }

    public String getUsdPayChnl() {
        return usdPayChnl;
    }

    public void setUsdPayChnl(String usdPayChnl) {
        this.usdPayChnl = usdPayChnl;
    }

    public String getPspName() {
        return pspName;
    }

    public void setPspName(String pspName) {
        this.pspName = pspName;
    }

    public String getPspCode() {
        return pspCode;
    }

    public void setPspCode(String pspCode) {
        this.pspCode = pspCode;
    }

    public String getDptCellNum() {
        return dptCellNum;
    }

    public void setDptCellNum(String dptCellNum) {
        this.dptCellNum = dptCellNum;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    public String getDptEmailAddr() {
        return dptEmailAddr;
    }

    public void setDptEmailAddr(String dptEmailAddr) {
        this.dptEmailAddr = dptEmailAddr;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReconcRvs1() {
        return reconcRvs1;
    }

    public void setReconcRvs1(String reconcRvs1) {
        this.reconcRvs1 = reconcRvs1;
    }

    public String getReconcRvs2() {
        return reconcRvs2;
    }

    public void setReconcRvs2(String reconcRvs2) {
        this.reconcRvs2 = reconcRvs2;
    }

    public String getReconcRvs3() {
        return reconcRvs3;
    }

    public void setReconcRvs3(String reconcRvs3) {
        this.reconcRvs3 = reconcRvs3;
    }

    @Override
    public String toString() {
        return "ReconcTrxInf{" +
                "spBillId='" + spBillId + '\'' +
                ", billCtrNum=" + billCtrNum +
                ", pspTrxId='" + pspTrxId + '\'' +
                ", paidAmt=" + paidAmt +
                ", cCy='" + cCy + '\'' +
                ", payRefId='" + payRefId + '\'' +
                ", trxDtTm='" + trxDtTm + '\'' +
                ", ctrAccNum='" + ctrAccNum + '\'' +
                ", usdPayChnl='" + usdPayChnl + '\'' +
                ", pspName='" + pspName + '\'' +
                ", pspCode='" + pspCode + '\'' +
                ", dptCellNum='" + dptCellNum + '\'' +
                ", dptName='" + dptName + '\'' +
                ", dptEmailAddr='" + dptEmailAddr + '\'' +
                ", remarks='" + remarks + '\'' +
                ", reconcRvs1='" + reconcRvs1 + '\'' +
                ", reconcRvs2='" + reconcRvs2 + '\'' +
                ", reconcRvs3='" + reconcRvs3 + '\'' +
                '}';
    }
}

