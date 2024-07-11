package com.watabelabs.gepg.mappers.bill;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillCanclResp class is used for mapping the XML response
 * to the corresponding Java object. This class represents the bill cancellation
 * response.
 *
 * <p>
 * It contains a list of bill cancellation transaction details. Each transaction
 * detail
 * is represented by the nested class {@link BillCanclTrxDtMapper}.
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
@XmlRootElement(name = "gepgBillCanclResp")
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillCanclResp {

    @XmlElement(name = "BillCanclTrxDt")
    private List<BillCanclTrxDtMapper> billCanclTrxDt;

    /**
     * Default no-argument constructor.
     */
    public GepgBillCanclResp() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided list of
     * bill cancellation transaction details.
     *
     * @param billCanclTrxDt the list of bill cancellation transaction details to
     *                       set
     */
    public GepgBillCanclResp(List<BillCanclTrxDtMapper> billCanclTrxDt) {
        this.billCanclTrxDt = billCanclTrxDt;
    }

    /**
     * Retrieves the list of bill cancellation transaction details.
     *
     * @return the list of bill cancellation transaction details
     */
    public List<BillCanclTrxDtMapper> getBillCanclTrxDt() {
        return billCanclTrxDt;
    }

    /**
     * Sets the list of bill cancellation transaction details.
     *
     * @param billCanclTrxDt the list of bill cancellation transaction details to
     *                       set
     */
    public void setBillCanclTrxDt(List<BillCanclTrxDtMapper> billCanclTrxDt) {
        this.billCanclTrxDt = billCanclTrxDt;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the values of the billCanclTrxDt field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "BillCancellationRespMapper{" +
                "billCanclTrxDt=" + billCanclTrxDt +
                '}';
    }

    /**
     * The BillCanclTrxDtMapper class represents the details of an individual bill
     * cancellation transaction.
     *
     * <p>
     * It contains fields for the bill ID, transaction status, and transaction
     * status code.
     * Each field is annotated with JAXB annotations to specify how the XML elements
     * should be mapped to the Java fields.
     * </p>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BillCanclTrxDtMapper {

        @XmlElement(name = "BillId")
        private UUID billId;

        @XmlElement(name = "TrxSts")
        private String trxSts;

        @XmlElement(name = "TrxStsCode")
        private String trxStsCode;

        /**
         * Default no-argument constructor.
         */
        public BillCanclTrxDtMapper() {
        }

        /**
         * Parameterized constructor to initialize the object with the provided values.
         *
         * @param billId     the bill ID
         * @param trxSts     the transaction status
         * @param trxStsCode the transaction status code
         */
        public BillCanclTrxDtMapper(UUID billId, String trxSts, String trxStsCode) {
            this.billId = billId;
            this.trxSts = trxSts;
            this.trxStsCode = trxStsCode;
        }

        /**
         * Retrieves the bill ID.
         *
         * @return the bill ID
         */
        public UUID getBillId() {
            return billId;
        }

        /**
         * Sets the bill ID.
         *
         * @param billId the bill ID to set
         */
        public void setBillId(UUID billId) {
            this.billId = billId;
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
         * This method is overridden to provide a string representation of the object
         * that includes the values of the fields.
         * </p>
         *
         * @return a string representation of the object
         */
        @Override
        public String toString() {
            return "BillCanclTrxDtMapper{" +
                    "billId='" + billId + '\'' +
                    ", trxSts='" + trxSts + '\'' +
                    ", trxStsCode='" + trxStsCode + '\'' +
                    '}';
        }
    }
}
