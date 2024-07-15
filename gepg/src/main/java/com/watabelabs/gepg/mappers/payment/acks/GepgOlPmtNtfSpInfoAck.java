package com.watabelabs.gepg.mappers.payment.acks;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgOlPmtNtfSpInfoAck class is used for mapping the XML response
 * to the corresponding Java object. This class represents the acknowledgment
 * of the bill subscription request.
 *
 * <p>
 * It contains a single field, olStsCode, which stores the transaction status
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
@XmlRootElement(name = "gepgOlPmtNtfSpAck")
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgOlPmtNtfSpInfoAck {

    @XmlElement(name = "OlStsCode")
    private int olStsCode;

    /**
     * Default no-argument constructor.
     */
    public GepgOlPmtNtfSpInfoAck() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * transaction status code.
     *
     * @param olStsCode the transaction status code to set
     */
    public GepgOlPmtNtfSpInfoAck(int olStsCode) {
        this.olStsCode = olStsCode;
    }

    /**
     * Retrieves the transaction status code.
     *
     * @return the transaction status code
     */
    public int getOlStsCode() {
        return olStsCode;
    }

    /**
     * Sets the transaction status code.
     *
     * @param olStsCode the transaction status code to set
     */
    public void setOlStsCode(int olStsCode) {
        this.olStsCode = olStsCode;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the value of the olStsCode field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgOlPmtSpInfoAckMapper{" +
                "olStsCode='" + olStsCode + '\'' +
                '}';
    }
}
