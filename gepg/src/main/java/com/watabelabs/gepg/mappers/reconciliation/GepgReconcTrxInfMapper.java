package com.watabelabs.gepg.mappers.reconciliation;

import java.math.BigDecimal;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The ReconcTrxInfMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the reconciliation
 * transaction information.
 *
 * <p>
 * It contains fields that store details of a reconciliation transaction, such
 * as
 * bill ID, payment reference ID, payment control number, paid amount, currency,
 * transaction date and time, control account number, used payment channel,
 * payer's
 * cell number, payer's name, payer's email, remarks, and various reconciliation
 * reversals.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * the
 * root element name and {@link XmlAccessorType} to specify the access type for
 * the fields.
 * </p>
 */
@XmlRootElement(name = "ReconcTrxInf")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgReconcTrxInfMapper {

    @XmlElement(name = "SpBillId")
    private UUID billId;

    @XmlElement(name = "PayRefId")
    private String payRefId;

    @XmlElement(name = "BillCtrNum")
    private Long payCtrNum;

    @XmlElement(name = "PaidAmt")
    private BigDecimal paidAmt;

    @XmlElement(name = "CCy")
    private String cCy;

    @XmlElement(name = "TrxDtTm")
    private String trxDtTm;

    @XmlElement(name = "CtrAccNum")
    private String ctrAccNum;

    @XmlElement(name = "UsdPayChnl")
    private String usdPayChn;

    @XmlElement(name = "DptCellNum")
    private String pyrCellNum;

    @XmlElement(name = "DptName")
    private String pyrName;

    @XmlElement(name = "DptNameEmailAddr")
    private String pyrEmail;

    @XmlElement(name = "Remarks")
    private String remarks;

    @XmlElement(name = "ReconcRvs01")
    private String reconcRvs01;

    @XmlElement(name = "ReconcRvs02")
    private String reconcRvs02;

    @XmlElement(name = "ReconcRvs03")
    private String reconcRvs03;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgReconcTrxInfMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param billId      the bill ID
     * @param payRefId    the payment reference ID
     * @param payCtrNum   the payment control number
     * @param paidAmt     the paid amount
     * @param cCy         the currency
     * @param trxDtTm     the transaction date and time
     * @param ctrAccNum   the control account number
     * @param usdPayChn   the used payment channel
     * @param pyrCellNum  the payer's cell number
     * @param pyrName     the payer's name
     * @param pyrEmail    the payer's email
     * @param remarks     remarks regarding the transaction
     * @param reconcRvs01 reconciliation reversal 1
     * @param reconcRvs02 reconciliation reversal 2
     * @param reconcRvs03 reconciliation reversal 3
     */
    public GepgReconcTrxInfMapper(UUID billId, String payRefId, Long payCtrNum, BigDecimal paidAmt, String cCy,
            String trxDtTm,
            String ctrAccNum, String usdPayChn, String pyrCellNum, String pyrName, String pyrEmail,
            String remarks, String reconcRvs01, String reconcRvs02, String reconcRvs03) {
        this.billId = billId;
        this.payRefId = payRefId;
        this.payCtrNum = payCtrNum;
        this.paidAmt = paidAmt;
        this.cCy = cCy;
        this.trxDtTm = trxDtTm;
        this.ctrAccNum = ctrAccNum;
        this.usdPayChn = usdPayChn;
        this.pyrCellNum = pyrCellNum;
        this.pyrName = pyrName;
        this.pyrEmail = pyrEmail;
        this.remarks = remarks;
        this.reconcRvs01 = reconcRvs01;
        this.reconcRvs02 = reconcRvs02;
        this.reconcRvs03 = reconcRvs03;
    }

    // Getter and setter methods

    public UUID getBillId() {
        return billId;
    }

    public void setBillId(UUID billId) {
        this.billId = billId;
    }

    public String getPayRefId() {
        return payRefId;
    }

    public void setPayRefId(String payRefId) {
        this.payRefId = payRefId;
    }

    public Long getPayCtrNum() {
        return payCtrNum;
    }

    public void setPayCtrNum(Long payCtrNum) {
        this.payCtrNum = payCtrNum;
    }

    public BigDecimal getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        this.paidAmt = paidAmt;
    }

    public String getCCy() {
        return cCy;
    }

    public void setCCy(String cCy) {
        this.cCy = cCy;
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

    public String getUsdPayChn() {
        return usdPayChn;
    }

    public void setUsdPayChn(String usdPayChn) {
        this.usdPayChn = usdPayChn;
    }

    public String getPyrCellNum() {
        return pyrCellNum;
    }

    public void setPyrCellNum(String pyrCellNum) {
        this.pyrCellNum = pyrCellNum;
    }

    public String getPyrName() {
        return pyrName;
    }

    public void setPyrName(String pyrName) {
        this.pyrName = pyrName;
    }

    public String getPyrEmail() {
        return pyrEmail;
    }

    public void setPyrEmail(String pyrEmail) {
        this.pyrEmail = pyrEmail;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReconcRvs01() {
        return reconcRvs01;
    }

    public void setReconcRvs01(String reconcRvs01) {
        this.reconcRvs01 = reconcRvs01;
    }

    public String getReconcRvs02() {
        return reconcRvs02;
    }

    public void setReconcRvs02(String reconcRvs02) {
        this.reconcRvs02 = reconcRvs02;
    }

    public String getReconcRvs03() {
        return reconcRvs03;
    }

    public void setReconcRvs03(String reconcRvs03) {
        this.reconcRvs03 = reconcRvs03;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the values of the fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "ReconcTrxInfMapper{" +
                "billId=" + billId +
                ", payRefId='" + payRefId + '\'' +
                ", payCtrNum=" + payCtrNum +
                ", paidAmt=" + paidAmt +
                ", cCy='" + cCy + '\'' +
                ", trxDtTm='" + trxDtTm + '\'' +
                ", ctrAccNum='" + ctrAccNum + '\'' +
                ", usdPayChn='" + usdPayChn + '\'' +
                ", pyrCellNum='" + pyrCellNum + '\'' +
                ", pyrName='" + pyrName + '\'' +
                ", pyrEmail='" + pyrEmail + '\'' +
                ", remarks='" + remarks + '\'' +
                ", reconcRvs01='" + reconcRvs01 + '\'' +
                ", reconcRvs02='" + reconcRvs02 + '\'' +
                ", reconcRvs03='" + reconcRvs03 + '\'' +
                '}';
    }
}
