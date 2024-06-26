package com.watabelabs.gepg.mappers.payment;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.watabelabs.gepg.utils.LocalDateTimeAdapter;
import com.watabelabs.gepg.utils.LocalDateTimeAdapterWithoutZone;

/**
 * The GepgPymtTrxInf class is used for mapping the XML response
 * to the corresponding Java object. This class represents the payment
 * transaction information.
 *
 * <p>
 * It contains various fields that store details of the payment transaction,
 * such as transaction ID, service provider code, payment reference ID, bill ID,
 * payment control number, bill amount, paid amount, bill payment option,
 * currency, transaction date and time, used payment channel, payer's cell number,
 * payer's name, payer's email, PSP receipt number, PSP name, and control account number.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define the
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
    @XmlJavaTypeAdapter(LocalDateTimeAdapterWithoutZone.class)
    private LocalDateTime trxDtTm;

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
            LocalDateTime trxDtTm, String usdPayChn, String pyrCellNum, String pyrName,
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

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public String getSpCode() {
        return spCode;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    public String getPayRefId() {
        return payRefId;
    }

    public void setPayRefId(String payRefId) {
        this.payRefId = payRefId;
    }

    public UUID getBillId() {
        return billId;
    }

    public void setBillId(UUID billId) {
        this.billId = billId;
    }

    public String getPayCtrNum() {
        return payCtrNum;
    }

    public void setPayCtrNum(String payCtrNum) {
        this.payCtrNum = payCtrNum;
    }

    public Double getBillAmt() {
        return billAmt;
    }

    public void setBillAmt(Double billAmt) {
        this.billAmt = billAmt;
    }

    public Double getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(Double paidAmt) {
        this.paidAmt = paidAmt;
    }

    public GepgBillPaymentOption getBillPayOpt() {
        return billPayOpt;
    }

    public void setBillPayOpt(GepgBillPaymentOption billPayOpt) {
        this.billPayOpt = billPayOpt;
        this.billPayOptString = billPayOpt.getValue();
    }

    public String getBillPayOptString() {
        return billPayOptString;
    }

    public void setBillPayOptString(String billPayOptString) {
        this.billPayOptString = billPayOptString;
        this.billPayOpt = GepgBillPaymentOption.fromValue(billPayOptString);
    }

    public String getCCy() {
        return CCy;
    }

    public void setCCy(String CCy) {
        this.CCy = CCy;
    }

    public LocalDateTime getTrxDtTm() {
        return trxDtTm;
    }

    public void setTrxDtTm(LocalDateTime trxDtTm) {
        this.trxDtTm = trxDtTm;
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

    public String getPspReceiptNumber() {
        return pspReceiptNumber;
    }

    public void setPspReceiptNumber(String pspReceiptNumber) {
        this.pspReceiptNumber = pspReceiptNumber;
    }

    public String getPspName() {
        return pspName;
    }

    public void setPspName(String pspName) {
        this.pspName = pspName;
    }

    public String getCtrAccNum() {
        return ctrAccNum;
    }

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

