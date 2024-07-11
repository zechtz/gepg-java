package com.watabelabs.gepg.mappers.reconciliation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgSpReconcRespAckMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the acknowledgment
 * of the reconciliation status response.
 *
 * <p>
 * It contains a single field, reconcStsCode, which stores the reconciliation
 * status code of the acknowledgment response.
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
@XmlRootElement(name = "gepgSpReconcRespAck")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgSpReconcRespAck {

    /**
     * The reconciliation status code.
     * <p>
     * This field is mapped to the XML element named "ReconcStsCode". It holds the
     * status code of the reconciliation, indicating whether it was successful or if
     * there was an error.
     * </p>
     */
    @XmlElement(name = "ReconcStsCode")
    private int reconcStsCode;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgSpReconcRespAck() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * reconciliation status code.
     *
     * @param reconcStsCode the reconciliation status code to set
     */
    public GepgSpReconcRespAck(int reconcStsCode) {
        this.reconcStsCode = reconcStsCode;
    }

    /**
     * Retrieves the reconciliation status code.
     *
     * @return the reconciliation status code
     */
    public int getReconcStsCode() {
        return reconcStsCode;
    }

    /**
     * Sets the reconciliation status code.
     *
     * @param reconcStsCode the reconciliation status code to set
     */
    public void setReconcStsCode(int reconcStsCode) {
        this.reconcStsCode = reconcStsCode;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the value of the reconcStsCode field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgSpReconcRespAckMapper{" +
                "reconcStsCode='" + reconcStsCode + '\'' +
                '}';
    }
}
