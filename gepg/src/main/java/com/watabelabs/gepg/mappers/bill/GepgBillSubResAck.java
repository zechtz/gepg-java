package com.watabelabs.gepg.mappers.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillSubReqAckMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the acknowledgment
 * of the bill subscription request.
 *
 * <p>
 * It contains a single field, trxStsCode, which stores the transaction status
 * code of the acknowledgment response.
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
@XmlRootElement(name = "gepgBillSubResAck")
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillSubResAck {

    @XmlElement(name = "TrxStsCode")
    private int trxStsCode;

    /**
     * Default no-argument constructor.
     */
    public GepgBillSubResAck() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * transaction status code.
     *
     * @param trxStsCode the transaction status code to set
     */
    public GepgBillSubResAck(int trxStsCode) {
        this.trxStsCode = trxStsCode;
    }

    /**
     * Retrieves the transaction status code.
     *
     * @return the transaction status code
     */
    public int getTrxStsCode() {
        return trxStsCode;
    }

    /**
     * Sets the transaction status code.
     *
     * @param trxStsCode the transaction status code to set
     */
    public void setTrxStsCode(int trxStsCode) {
        this.trxStsCode = trxStsCode;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the value of the trxStsCode field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillSubResAckMapper{" +
                "trxStsCode='" + trxStsCode + '\'' +
                '}';
    }
}
