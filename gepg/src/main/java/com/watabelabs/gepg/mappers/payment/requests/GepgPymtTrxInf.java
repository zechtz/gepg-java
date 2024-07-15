package com.watabelabs.gepg.mappers.payment.requests;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgPymtTrxInf class is used for mapping the XML response
 * to the corresponding Java object. This class represents the payment
 * transaction information.
 *
 * <p>
 * It contains various fields that store details of the payment transaction,
 * such as transaction ID, service provider code, payment reference ID, bill ID,
 * payment control number, bill amount, paid amount, bill payment option,
 * currency, transaction date and time, used payment channel, payer's cell
 * number,
 * payer's name, payer's email, PSP receipt number, PSP name, and control
 * account number.
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
@XmlRootElement(name = "PymtTrxInf")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgPymtTrxInf {

    @XmlElement(name = "TrxId")
    private String trxId;

    @XmlElement(name = "SpCode")
    private String spCode;

    @XmlElement(name = "PayRefId")
    private String payRefId;

    @XmlElement(name = "BillId")
    private UUID billId;

    @XmlElement(name = "PayCtrNum")
    private String payCtrNum;

    @XmlElement(name = "BillAmt")
    private Double billAmt;

    @XmlElement(name = "PaidAmt")
    private Double paidAmt;

    @XmlElement(name = "BillPayOpt")
    private String billPayOptString;

    private GepgBillPaymentOption billPayOpt;

    @XmlElement(name = "CCy")
    private String CCy;

    @XmlElement(name = "TrxDtTm")
    private String trxDtTm;

    @XmlElement(name = "UsdPayChnl")
    private String usdPayChn;

    @XmlElement(name = "PyrCellNum")
    private String pyrCellNum;

    @XmlElement(name = "PyrName")
    private String pyrName;

    @XmlElement(name = "PyrEmail")
    private String pyrEmail;

    @XmlElement(name = "PspReceiptNumber")
    private String pspReceiptNumber;

    @XmlElement(name = "PspName")
    private String pspName;

    @XmlElement(name = "CtrAccNum")
    private String ctrAccNum;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgPymtTrxInf() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param trxId            the transaction ID
     * @param spCode           the service provider code
     * @param payRefId         the payment reference ID
     * @param billId           the bill ID
     * @param payCtrNum        the payment control number
     * @param billAmt          the bill amount
     * @param paidAmt          the paid amount
     * @param billPayOptString the bill payment option string value
     * @param CCy              the currency
     * @param trxDtTm          the transaction date and time
     * @param usdPayChn        the used payment channel
     * @param pyrCellNum       the payer's cell number
     * @param pyrName          the payer's name
     * @param pyrEmail         the payer's email
     * @param pspReceiptNumber the PSP receipt number
     * @param pspName          the PSP name
     * @param ctrAccNum        the control account number
     */
    public GepgPymtTrxInf(String trxId, String spCode, String payRefId, UUID billId, String payCtrNum,
            Double billAmt, Double paidAmt, String billPayOptString, String CCy,
            String trxDtTm, String usdPayChn, String pyrCellNum, String pyrName,
            String pyrEmail, String pspReceiptNumber, String pspName, String ctrAccNum) {
        this.trxId = trxId;
        this.spCode = spCode;
        this.payRefId = payRefId;
        this.billId = billId;
        this.payCtrNum = payCtrNum;
        this.billAmt = billAmt;
        this.paidAmt = paidAmt;
        this.billPayOptString = billPayOptString;
        // this.billPayOpt = GepgBillPaymentOption.fromValue(billPayOptString);
        this.CCy = CCy;
        this.trxDtTm = trxDtTm;
        this.usdPayChn = usdPayChn;
        this.pyrCellNum = pyrCellNum;
        this.pyrName = pyrName;
        this.pyrEmail = pyrEmail;
        this.pspReceiptNumber = pspReceiptNumber;
        this.pspName = pspName;
        this.ctrAccNum = ctrAccNum;
    }

    // Getter and setter methods

    /**
     * Gets the transaction ID.
     *
     * @return the transaction ID
     */
    public String getTrxId() {
        return trxId;
    }

    /**
     * Sets the transaction ID.
     *
     * @param trxId the transaction ID
     */
    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    /**
     * Gets the service provider code.
     *
     * @return the service provider code
     */
    public String getSpCode() {
        return spCode;
    }

    /**
     * Sets the service provider code.
     *
     * @param spCode the service provider code
     */
    public void setSpCode(String spCode) {
        this.spCode = spCode;
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
     * Gets the bill ID.
     *
     * @return the bill ID
     */
    public UUID getBillId() {
        return billId;
    }

    /**
     * Sets the bill ID.
     *
     * @param billId the bill ID
     */
    public void setBillId(UUID billId) {
        this.billId = billId;
    }

    /**
     * Gets the payment control number.
     *
     * @return the payment control number
     */
    public String getPayCtrNum() {
        return payCtrNum;
    }

    /**
     * Sets the payment control number.
     *
     * @param payCtrNum the payment control number
     */
    public void setPayCtrNum(String payCtrNum) {
        this.payCtrNum = payCtrNum;
    }

    /**
     * Gets the bill amount.
     *
     * @return the bill amount
     */
    public Double getBillAmt() {
        return billAmt;
    }

    /**
     * Sets the bill amount.
     *
     * @param billAmt the bill amount
     */
    public void setBillAmt(Double billAmt) {
        this.billAmt = billAmt;
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
     * Gets the bill payment option.
     *
     * @return the bill payment option
     */
    public GepgBillPaymentOption getBillPayOpt() {
        return billPayOpt;
    }

    /**
     * Sets the bill payment option.
     *
     * @param billPayOpt the bill payment option
     */
    public void setBillPayOpt(GepgBillPaymentOption billPayOpt) {
        this.billPayOpt = billPayOpt;
        this.billPayOptString = billPayOpt.getValue();
    }

    /**
     * Gets the bill payment option string value.
     *
     * @return the bill payment option string value
     */
    public String getBillPayOptString() {
        return billPayOptString;
    }

    /**
     * Sets the bill payment option string value.
     *
     * @param billPayOptString the bill payment option string value
     */
    public void setBillPayOptString(String billPayOptString) {
        this.billPayOptString = billPayOptString;
        this.billPayOpt = GepgBillPaymentOption.fromValue(billPayOptString);
    }

    /**
     * Gets the currency.
     *
     * @return the currency
     */
    public String getCCy() {
        return CCy;
    }

    /**
     * Sets the currency.
     *
     * @param CCy the currency
     */
    public void setCCy(String CCy) {
        this.CCy = CCy;
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
     * Gets the used payment channel.
     *
     * @return the used payment channel
     */
    public String getUsdPayChn() {
        return usdPayChn;
    }

    /**
     * Sets the used payment channel.
     *
     * @param usdPayChn the used payment channel
     */
    public void setUsdPayChn(String usdPayChn) {
        this.usdPayChn = usdPayChn;
    }

    /**
     * Gets the payer's cell number.
     *
     * @return the payer's cell number
     */
    public String getPyrCellNum() {
        return pyrCellNum;
    }

    /**
     * Sets the payer's cell number.
     *
     * @param pyrCellNum the payer's cell number
     */
    public void setPyrCellNum(String pyrCellNum) {
        this.pyrCellNum = pyrCellNum;
    }

    /**
     * Gets the payer's name.
     *
     * @return the payer's name
     */
    public String getPyrName() {
        return pyrName;
    }

    /**
     * Sets the payer's name.
     *
     * @param pyrName the payer's name
     */
    public void setPyrName(String pyrName) {
        this.pyrName = pyrName;
    }

    /**
     * Gets the payer's email.
     *
     * @return the payer's email
     */
    public String getPyrEmail() {
        return pyrEmail;
    }

    /**
     * Sets the payer's email.
     *
     * @param pyrEmail the payer's email
     */
    public void setPyrEmail(String pyrEmail) {
        this.pyrEmail = pyrEmail;
    }

    /**
     * Gets the PSP receipt number.
     *
     * @return the PSP receipt number
     */
    public String getPspReceiptNumber() {
        return pspReceiptNumber;
    }

    /**
     * Sets the PSP receipt number.
     *
     * @param pspReceiptNumber the PSP receipt number
     */
    public void setPspReceiptNumber(String pspReceiptNumber) {
        this.pspReceiptNumber = pspReceiptNumber;
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
        return "GepgPymtTrxInf{" +
                "trxId='" + trxId + '\'' +
                ", spCode='" + spCode + '\'' +
                ", payRefId='" + payRefId + '\'' +
                ", billId='" + billId + '\'' +
                ", payCtrNum='" + payCtrNum + '\'' +
                ", billAmt=" + billAmt +
                ", paidAmt=" + paidAmt +
                ", billPayOpt=" + billPayOpt +
                ", CCy='" + CCy + '\'' +
                ", trxDtTm='" + trxDtTm + '\'' +
                ", usdPayChn='" + usdPayChn + '\'' +
                ", pyrCellNum='" + pyrCellNum + '\'' +
                ", pyrName='" + pyrName + '\'' +
                ", pyrEmail='" + pyrEmail + '\'' +
                ", pspReceiptNumber='" + pspReceiptNumber + '\'' +
                ", pspName='" + pspName + '\'' +
                ", ctrAccNum='" + ctrAccNum + '\'' +
                '}';
    }
}

