package com.watabelabs.gepg.mappers.bill.responses;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * The GepgBillSubResp class is used for mapping the XML response
 * to the corresponding Java object. This class represents the bill submission
 * response, containing the bill transaction information.
 *
 * <p>
 * It contains a single field, billTrxInf, which stores the bill transaction
 * information of the subscription response.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * the root element name, {@link XmlAccessorType} to specify the access type for
 * the fields, and {@link XmlSeeAlso} to indicate related classes.
 * </p>
 */
@XmlRootElement(name = "gepgBillSubResp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(GepgBillSubResp.GepgBillTrxInf.class)
public class GepgBillSubResp {

    /**
     * The bill transaction information.
     * <p>
     * This field is mapped to the XML element named "BillTrxInf". It holds
     * the details of the bill transaction, encapsulated in a
     * {@link GepgBillTrxInf} object.
     * </p>
     */
    @XmlElement(name = "BillTrxInf")
    private GepgBillTrxInf billTrxInf;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgBillSubResp() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided bill
     * transaction information.
     *
     * @param billTrxInf the bill transaction information to set
     */
    public GepgBillSubResp(GepgBillTrxInf billTrxInf) {
        this.billTrxInf = billTrxInf;
    }

    /**
     * Retrieves the bill transaction information.
     *
     * @return the bill transaction information
     */
    public GepgBillTrxInf getBillTrxInf() {
        return billTrxInf;
    }

    /**
     * Sets the bill transaction information.
     *
     * @param billTrxInf the bill transaction information to set
     */
    public void setBillTrxInf(GepgBillTrxInf billTrxInf) {
        this.billTrxInf = billTrxInf;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the value of the billTrxInf field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillSubResp{" +
                "billTrxInf=" + billTrxInf +
                '}';
    }

    /**
     * The GepgBillTrxInf class is used for mapping the XML response
     * to the corresponding Java object. This class represents the bill transaction
     * information.
     */
    @XmlRootElement(name = "BillTrxInf")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GepgBillTrxInf implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The unique identifier for the bill.
         */
        @XmlElement(name = "BillId")
        private String billId;

        /**
         * The transaction status.
         */
        @XmlElement(name = "TrxSts")
        private String trxSts;

        /**
         * The payment control number.
         */
        @XmlElement(name = "PayCntrNum")
        private String payCntrNum;

        /**
         * The transaction status code.
         */
        @XmlElement(name = "TrxStsCode")
        private String trxStsCode;

        /**
         * Default no-argument constructor.
         * <p>
         * This constructor is required for JAXB to be able to create an instance of
         * the class when unmarshalling XML data.
         * </p>
         */
        public GepgBillTrxInf() {
        }

        /**
         * Parameterized constructor to initialize the object with the provided values.
         *
         * @param billId     the bill ID
         * @param trxSts     the transaction status
         * @param payCntrNum the payment control number
         * @param trxStsCode the transaction status code
         */
        public GepgBillTrxInf(String billId, String trxSts, String payCntrNum, String trxStsCode) {
            this.billId = billId;
            this.trxSts = trxSts;
            this.payCntrNum = payCntrNum;
            this.trxStsCode = trxStsCode;
        }

        // Getters and setters

        /**
         * Gets the bill ID.
         *
         * @return the bill ID
         */
        public String getBillId() {
            return billId;
        }

        /**
         * Sets the bill ID.
         *
         * @param billId the bill ID
         */
        public void setBillId(String billId) {
            this.billId = billId;
        }

        /**
         * Gets the transaction status.
         *
         * @return the transaction status
         */
        public String getTrxSts() {
            return trxSts;
        }

        /**
         * Sets the transaction status.
         *
         * @param trxSts the transaction status
         */
        public void setTrxSts(String trxSts) {
            this.trxSts = trxSts;
        }

        /**
         * Gets the payment control number.
         *
         * @return the payment control number
         */
        public String getPayCntrNum() {
            return payCntrNum;
        }

        /**
         * Sets the payment control number.
         *
         * @param payCntrNum the payment control number
         */
        public void setPayCntrNum(String payCntrNum) {
            this.payCntrNum = payCntrNum;
        }

        /**
         * Gets the transaction status code.
         *
         * @return the transaction status code
         */
        public String getTrxStsCode() {
            return trxStsCode;
        }

        /**
         * Sets the transaction status code.
         *
         * @param trxStsCode the transaction status code
         */
        public void setTrxStsCode(String trxStsCode) {
            this.trxStsCode = trxStsCode;
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
            return "GepgBillTrxInf{" +
                    "billId='" + billId + '\'' +
                    ", trxSts='" + trxSts + '\'' +
                    ", payCntrNum='" + payCntrNum + '\'' +
                    ", trxStsCode=" + trxStsCode +
                    '}';
        }
    }
}
