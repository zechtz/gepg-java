package com.watabelabs.gepg.mappers.bill;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.watabelabs.gepg.utils.LocalDateTimeAdapter;

/**
 * The GepgBillTrxInf class is used for mapping the XML response
 * to the corresponding Java object. This class represents the bill transaction
 * information.
 *
 * <p>
 * It contains fields to store the bill ID, payment control number, transaction
 * status, and transaction status code, among others.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * the root element name and {@link XmlAccessorType} to specify the access type
 * for the fields.
 * </p>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillTrxInf {

    @XmlElement(name = "BillId", required = true)
    private String billId;

    @XmlElement(name = "SubSpCode", required = true)
    private String subSpCode;

    @XmlElement(name = "SpSysId", required = true)
    private String spSysId;

    @XmlElement(name = "BillAmt", required = true)
    private BigDecimal billAmt;

    @XmlElement(name = "MiscAmt", required = true)
    private BigDecimal miscAmt;

    @XmlElement(name = "BillExprDt", required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime billExprDt;

    @XmlElement(name = "PyrId", required = true)
    private String pyrId;

    @XmlElement(name = "PyrName", required = true)
    private String pyrName;

    @XmlElement(name = "BillDesc", required = true)
    private String billDesc;

    @XmlElement(name = "BillGenDt", required = true)
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime billGenDt;

    @XmlElement(name = "BillGenBy", required = true)
    private String billGenBy;

    @XmlElement(name = "BillApprBy", required = true)
    private String billApprBy;

    @XmlElement(name = "PyrCellNum", required = true)
    private String pyrCellNum;

    @XmlElement(name = "PyrEmail", required = true)
    private String pyrEmail;

    @XmlElement(name = "Ccy", required = true)
    private String ccy;

    @XmlElement(name = "BillEqvAmt", required = true)
    private BigDecimal billEqvAmt;

    @XmlElement(name = "RemFlag", required = true)
    private boolean remFlag;

    @XmlElement(name = "BillPayOpt", required = true)
    private int billPayOpt;

    @XmlElement(name = "PayCntrNum", required = true)
    private String payCntrNum;

    @XmlElementWrapper(name = "BillItems")
    @XmlElement(name = "BillItem", required = true)
    private List<GepgBillItem> billItems;

    /**
     * Default no-args constructor.
     */
    public GepgBillTrxInf() {
    }

    /**
     * All-args constructor except payCntrNum.
     *
     * @param billId     the bill ID
     * @param subSpCode  the sub service provider code
     * @param spSysId    the service provider system ID
     * @param billAmt    the bill amount
     * @param miscAmt    the miscellaneous amount
     * @param billExprDt the bill expiration date
     * @param pyrId      the payer ID
     * @param pyrName    the payer name
     * @param billDesc   the bill description
     * @param billGenDt  the bill generation date
     * @param billGenBy  the bill generated by
     * @param billApprBy the bill approved by
     * @param pyrCellNum the payer cell number
     * @param pyrEmail   the payer email
     * @param ccy        the currency
     * @param billEqvAmt the bill equivalent amount
     * @param remFlag    the reminder flag
     * @param billPayOpt the bill payment option
     * @param billItems  the list of bill items
     */
    public GepgBillTrxInf(String billId, String subSpCode, String spSysId, BigDecimal billAmt, BigDecimal miscAmt,
            LocalDateTime billExprDt, String pyrId, String pyrName, String billDesc, LocalDateTime billGenDt,
            String billGenBy, String billApprBy, String pyrCellNum, String pyrEmail, String ccy,
            BigDecimal billEqvAmt, boolean remFlag, int billPayOpt, List<GepgBillItem> billItems) {
        this.billId = billId;
        this.subSpCode = subSpCode;
        this.spSysId = spSysId;
        this.billAmt = billAmt;
        this.miscAmt = miscAmt;
        this.billExprDt = billExprDt;
        this.pyrId = pyrId;
        this.pyrName = pyrName;
        this.billDesc = billDesc;
        this.billGenDt = billGenDt;
        this.billGenBy = billGenBy;
        this.billApprBy = billApprBy;
        this.pyrCellNum = pyrCellNum;
        this.pyrEmail = pyrEmail;
        this.ccy = ccy;
        this.billEqvAmt = billEqvAmt;
        this.remFlag = remFlag;
        this.billPayOpt = billPayOpt;
        this.billItems = billItems;
    }

    /**
     * All-args constructor including payCntrNum.
     *
     * @param billId     the bill ID
     * @param subSpCode  the sub service provider code
     * @param spSysId    the service provider system ID
     * @param billAmt    the bill amount
     * @param miscAmt    the miscellaneous amount
     * @param billExprDt the bill expiration date
     * @param pyrId      the payer ID
     * @param pyrName    the payer name
     * @param billDesc   the bill description
     * @param billGenDt  the bill generation date
     * @param billGenBy  the bill generated by
     * @param billApprBy the bill approved by
     * @param pyrCellNum the payer cell number
     * @param pyrEmail   the payer email
     * @param ccy        the currency
     * @param billEqvAmt the bill equivalent amount
     * @param remFlag    the reminder flag
     * @param billPayOpt the bill payment option
     * @param payCntrNum the payment control number
     * @param billItems  the list of bill items
     */
    public GepgBillTrxInf(String billId, String subSpCode, String spSysId, BigDecimal billAmt, BigDecimal miscAmt,
            LocalDateTime billExprDt, String pyrId, String pyrName, String billDesc, LocalDateTime billGenDt,
            String billGenBy, String billApprBy, String pyrCellNum, String pyrEmail, String ccy,
            BigDecimal billEqvAmt, boolean remFlag, int billPayOpt, String payCntrNum, List<GepgBillItem> billItems) {
        this.billId = billId;
        this.subSpCode = subSpCode;
        this.spSysId = spSysId;
        this.billAmt = billAmt;
        this.miscAmt = miscAmt;
        this.billExprDt = billExprDt;
        this.pyrId = pyrId;
        this.pyrName = pyrName;
        this.billDesc = billDesc;
        this.billGenDt = billGenDt;
        this.billGenBy = billGenBy;
        this.billApprBy = billApprBy;
        this.pyrCellNum = pyrCellNum;
        this.pyrEmail = pyrEmail;
        this.ccy = ccy;
        this.billEqvAmt = billEqvAmt;
        this.remFlag = remFlag;
        this.billPayOpt = billPayOpt;
        this.payCntrNum = payCntrNum;
        this.billItems = billItems;
    }

    // Getters and Setters

    // (Include getters and setters for all fields)

    /**
     * Generates a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "BillTrxInf{" +
                "billId='" + billId + '\'' +
                ", subSpCode='" + subSpCode + '\'' +
                ", spSysId='" + spSysId + '\'' +
                ", billAmt=" + billAmt +
                ", miscAmt=" + miscAmt +
                ", billExprDt=" + billExprDt +
                ", pyrId='" + pyrId + '\'' +
                ", pyrName='" + pyrName + '\'' +
                ", billDesc='" + billDesc + '\'' +
                ", billGenDt=" + billGenDt +
                ", billGenBy='" + billGenBy + '\'' +
                ", billApprBy='" + billApprBy + '\'' +
                ", pyrCellNum='" + pyrCellNum + '\'' +
                ", pyrEmail='" + pyrEmail + '\'' +
                ", ccy='" + ccy + '\'' +
                ", billEqvAmt=" + billEqvAmt +
                ", remFlag=" + remFlag +
                ", billPayOpt=" + billPayOpt +
                ", payCntrNum=" + payCntrNum +
                ", billItems=" + billItems +
                '}';
    }

    // Additional getters and setters for new fields

    public String getPayCntrNum() {
        return payCntrNum;
    }

    public void setPayCntrNum(String payCntrNum) {
        this.payCntrNum = payCntrNum;
    }
}
