package com.watabelabs.gepg.mappers.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgPmtSpInfoAckMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the acknowledgment
 * of the payment service provider information.
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
@XmlRootElement(name = "gepgPmtSpInfoAck")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgPmtSpInfoAck {

    /**
     * The transaction status code.
     * <p>
     * This field is mapped to the XML element named "TrxStsCode". It holds the
     * status code of the transaction, indicating whether it was successful or if
     * there was an error.
     * </p>
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
    public GepgPmtSpInfoAck() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * transaction status code.
     *
     * @param trxStsCode the transaction status code to set
     */
    public GepgPmtSpInfoAck(String trxStsCode) {
        this.trxStsCode = trxStsCode;
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
     * that includes the value of the trxStsCode field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgPmtSpInfoAckMapper{" +
                "trxStsCode='" + trxStsCode + '\'' +
                '}';
    }
}
