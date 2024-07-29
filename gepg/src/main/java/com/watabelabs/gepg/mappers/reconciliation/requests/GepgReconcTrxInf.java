package com.watabelabs.gepg.mappers.reconciliation.requests;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

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
    private String spBillId;

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

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgReconcTrxInf() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param spBillId     the service provider bill ID
     * @param billCtrNum   the bill control number
     * @param pspTrxId     the PSP transaction ID
     * @param paidAmt      the paid amount
     * @param cCy          the currency
     * @param payRefId     the payment reference ID
     * @param trxDtTm      the transaction date and time
     * @param ctrAccNum    the control account number
     * @param usdPayChnl   the used payment channel
     * @param pspName      the PSP name
     * @param pspCode      the PSP code
     * @param dptCellNum   the department cell number
     * @param dptName      the department name
     * @param dptEmailAddr the department email address
     * @param remarks      any remarks for the transaction
     * @param reconcRvs1   the first reconciliation reversal
     * @param reconcRvs2   the second reconciliation reversal
     * @param reconcRvs3   the third reconciliation reversal
     */
    public GepgReconcTrxInf(String spBillId, Long billCtrNum, String pspTrxId, Double paidAmt, String cCy,
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

    /**
     * Gets the service provider bill ID.
     *
     * @return the service provider bill ID
     */
    public String getSpBillId() {
        return spBillId;
    }

    /**
     * Sets the service provider bill ID.
     *
     * @param spBillId the service provider bill ID
     */
    public void setSpBillId(String spBillId) {
        this.spBillId = spBillId;
    }

    /**
     * Gets the bill control number.
     *
     * @return the bill control number
     */
    public Long getBillCtrNum() {
        return billCtrNum;
    }

    /**
     * Sets the bill control number.
     *
     * @param billCtrNum the bill control number
     */
    public void setBillCtrNum(Long billCtrNum) {
        this.billCtrNum = billCtrNum;
    }

    /**
     * Gets the PSP transaction ID.
     *
     * @return the PSP transaction ID
     */
    public String getPspTrxId() {
        return pspTrxId;
    }

    /**
     * Sets the PSP transaction ID.
     *
     * @param pspTrxId the PSP transaction ID
     */
    public void setPspTrxId(String pspTrxId) {
        this.pspTrxId = pspTrxId;
    }

    /**
     * Gets the paid amount.
     *
     * @return the paid amount
     */
    public Double getPaidAmt() {
        return paidAmt;
    }

    /**
     * Sets the paid amount.
     *
     * @param paidAmt the paid amount
     */
    public void setPaidAmt(Double paidAmt) {
        this.paidAmt = paidAmt;
    }

    /**
     * Gets the currency.
     *
     * @return the currency
     */
    public String getCCy() {
        return cCy;
    }

    /**
     * Sets the currency.
     *
     * @param cCy the currency
     */
    public void setCCy(String cCy) {
        this.cCy = cCy;
    }

    /**
     * Gets the payment reference ID.
     *
     * @return the payment reference ID
     */
    public String getPayRefId() {
        return payRefId;
    }

    /**
     * Sets the payment reference ID.
     *
     * @param payRefId the payment reference ID
     */
    public void setPayRefId(String payRefId) {
        this.payRefId = payRefId;
    }

    /**
     * Gets the transaction date and time.
     *
     * @return the transaction date and time
     */
    public String getTrxDtTm() {
        return trxDtTm;
    }

    /**
     * Sets the transaction date and time.
     *
     * @param trxDtTm the transaction date and time
     */
    public void setTrxDtTm(String trxDtTm) {
        this.trxDtTm = trxDtTm;
    }

    /**
     * Gets the control account number.
     *
     * @return the control account number
     */
    public String getCtrAccNum() {
        return ctrAccNum;
    }

    /**
     * Sets the control account number.
     *
     * @param ctrAccNum the control account number
     */
    public void setCtrAccNum(String ctrAccNum) {
        this.ctrAccNum = ctrAccNum;
    }

    /**
     * Gets the used payment channel.
     *
     * @return the used payment channel
     */
    public String getUsdPayChnl() {
        return usdPayChnl;
    }

    /**
     * Sets the used payment channel.
     *
     * @param usdPayChnl the used payment channel
     */
    public void setUsdPayChnl(String usdPayChnl) {
        this.usdPayChnl = usdPayChnl;
    }

    /**
     * Gets the PSP name.
     *
     * @return the PSP name
     */
    public String getPspName() {
        return pspName;
    }

    /**
     * Sets the PSP name.
     *
     * @param pspName the PSP name
     */
    public void setPspName(String pspName) {
        this.pspName = pspName;
    }

    /**
     * Gets the PSP code.
     *
     * @return the PSP code
     */
    public String getPspCode() {
        return pspCode;
    }

    /**
     * Sets the PSP code.
     *
     * @param pspCode the PSP code
     */
    public void setPspCode(String pspCode) {
        this.pspCode = pspCode;
    }

    /**
     * Gets the department cell number.
     *
     * @return the department cell number
     */
    public String getDptCellNum() {
        return dptCellNum;
    }

    /**
     * Sets the department cell number.
     *
     * @param dptCellNum the department cell number
     */
    public void setDptCellNum(String dptCellNum) {
        this.dptCellNum = dptCellNum;
    }

    /**
     * Gets the department name.
     *
     * @return the department name
     */
    public String getDptName() {
        return dptName;
    }

    /**
     * Sets the department name.
     *
     * @param dptName the department name
     */
    public void setDptName(String dptName) {
        this.dptName = dptName;
    }

    /**
     * Gets the department email address.
     *
     * @return the department email address
     */
    public String getDptEmailAddr() {
        return dptEmailAddr;
    }

    /**
     * Sets the department email address.
     *
     * @param dptEmailAddr the department email address
     */
    public void setDptEmailAddr(String dptEmailAddr) {
        this.dptEmailAddr = dptEmailAddr;
    }

    /**
     * Gets any remarks for the transaction.
     *
     * @return any remarks for the transaction
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets any remarks for the transaction.
     *
     * @param remarks any remarks for the transaction
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * Gets the first reconciliation reversal.
     *
     * @return the first reconciliation reversal
     */
    public String getReconcRvs1() {
        return reconcRvs1;
    }

    /**
     * Sets the first reconciliation reversal.
     *
     * @param reconcRvs1 the first reconciliation reversal
     */
    public void setReconcRvs1(String reconcRvs1) {
        this.reconcRvs1 = reconcRvs1;
    }

    /**
     * Gets the second reconciliation reversal.
     *
     * @return the second reconciliation reversal
     */
    public String getReconcRvs2() {
        return reconcRvs2;
    }

    /**
     * Sets the second reconciliation reversal.
     *
     * @param reconcRvs2 the second reconciliation reversal
     */
    public void setReconcRvs2(String reconcRvs2) {
        this.reconcRvs2 = reconcRvs2;
    }

    /**
     * Gets the third reconciliation reversal.
     *
     * @return the third reconciliation reversal
     */
    public String getReconcRvs3() {
        return reconcRvs3;
    }

    /**
     * Sets the third reconciliation reversal.
     *
     * @param reconcRvs3 the third reconciliation reversal
     */
    public void setReconcRvs3(String reconcRvs3) {
        this.reconcRvs3 = reconcRvs3;
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
