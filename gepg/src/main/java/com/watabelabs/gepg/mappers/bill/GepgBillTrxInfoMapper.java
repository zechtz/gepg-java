package com.watabelabs.gepg.mappers.bill;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillTrxInfMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the bill transaction
 * information.
 *
 *
 *
 * <p>
 * It contains four fields: billId, payCntrNum, trxSts, and trxSts
 * ode. These fields
 * store the bill ID, payment
 * control number, transaction status, and transaction
 * status code, respectively.
 * </p>
 *
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elemen
 * s
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to defin
 * root elemen
 * t name and {@link XmlAccessorType} to specify the access type for
 * the fields.
 * </p>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillTrxInfoMapper {

    /**
     * The
     * unique identifier of the bill.
     * <p>
     * This field is mapped to the XML element named "BillI
     * d". It holds the unique
     * identifier for the bill, represented as a {@link UUID}.
     * </p>
     */
    @XmlElement(name = "BillId")
    private UUID billId;

    /**
     * The
     * payment control number.
     * <p>
     * This field is mapped to the XML element named "PayCntrNum".
     * It holds the
     * control number for the payment, represented as a {@link Long}.
     * </p>
     */
    @XmlElement(name = "PayCntrNum")
    private Long payCntrNum;

    /**
     * The
     * transaction status.
     * <p>
     * This field is mapped to the XML element named "Tr
     * xSts". It holds the status
     * of the transaction, represented as a {@link String}.
     * </p>
     */
    @XmlElement(name = "TrxSts")
    private String trxSts;

    /**
     * The
     * transaction status code.
     * <p>
     * This field is mapped to the XML element named "TrxStsCode". I
     * t holds the
     * status code of the transaction, represented as a {@link String}.
     * </p>
     */
    @XmlElement(name = "TrxStsCode")
    private String trxStsCode;

    /**
     * Def
     * ault no-argument constructor.
     * <p>
     * This constructor is required for JA
     * XB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgBillTrxInfoMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param billId     the unique identifier of the bill
     * @param payCnt     Num the payment control number
     * @param trxSts     the transaction status
     * @param trxStsCode the transaction status code
     */
    public GepgBillTrxInfoMapper(UUID billId, Long payCntrNum, String trxSts, String trxStsCode) {
        this.billId = billId;
        this.payCntrNum = payCntrNum;
        this.trxSts = trxSts;
        this.trxStsCode = trxStsCode;
    }

    /**
     * Retrieves the unique identifier of the bill.
     *
     * @return the unique identifier of the bill
     */
    public UUID getBillId() {
        return billId;
    }

    /**
     * Sets the unique identifier of the bill.
     *
     * @param billId the unique identifier of the bill to set
     */
    public void setBillId(UUID billId) {
        this.billId = billId;
    }

    /**
     * Retrieves the payment control number.
     *
     * @return the payment control number
     */
    public Long getPayCntrNum() {
        return payCntrNum;
    }

    /**
     * Sets the payment control number.
     *
     * @param payCntrNum the payment control number to set
     */
    public void setPayCntrNum(Long payCntrNum) {
        this.payCntrNum = payCntrNum;
    }

    /**
     * Retrieves the transaction status.
     *
     * @return the transaction status
     */
    public String getTrxSts() {
        return trxSts;
    }

    /**
     * Sets the transaction status.
     *
     * @param trxSts the transaction status to set
     */
    public void setTrxSts(String trxSts) {
        this.trxSts = trxSts;
    }

    /**
     * Retrieves the transaction status code.
     *
     * @return the transaction status code
     */
    public String getTrxStsCode() {
        return trxStsCode;
    }

    /**
     * Sets the transaction status code.
     *
     * @param trxStsCode the transaction status code to set
     */
    public void setTrxStsCode(String trxStsCode) {
        this.trxStsCode = trxStsCode;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the obj
     * ct
     *
     * that includes the values of the billId, payCntrNum, trxSts, and trxStsCode
     * fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillTrxInfMapper{" +
                "billId=" + billId +
                ", payCntrNum=" + payCntrNum +
                ", trxSts='" + trxSts + '\'' +
                ", trxStsCode='" + trxStsCode + '\'' +
                '}';
    }
}
