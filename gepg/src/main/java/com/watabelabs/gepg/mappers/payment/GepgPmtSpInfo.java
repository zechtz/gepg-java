package com.watabelabs.gepg.mappers.payment;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

/**
 * The GepgPmtSpInfoMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the payment
 * service provider information, containing the payment transaction information.
 *
 * <p>
 * It contains a single field, pymtTrxInf, which stores the payment transaction
 * information of the service provider.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * the
 * root element name and {@link XmlAccessorType} to specify the access type for
 * the fields. The {@link XmlSeeAlso} annotation indicates related classes.
 * </p>
 */
@XmlRootElement(name = "gepgPmtSpInfo")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlSeeAlso({ GepgPymtTrxInf.class })
public class GepgPmtSpInfo {

    /**
     * The payment transaction information.
     * <p>
     * This field is mapped to the XML element named "PymtTrxInf". It holds the
     * details of the payment transaction, encapsulated in a
     * {@link GepgPymtTrxInf} object.
     * </p>
     */
    @XmlElement(name = "PymtTrxInf")
    private GepgPymtTrxInf pymtTrxInf;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgPmtSpInfo() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided payment
     * transaction information.
     *
     * @param pymtTrxInf the payment transaction information to set
     */
    public GepgPmtSpInfo(GepgPymtTrxInf pymtTrxInf) {
        this.pymtTrxInf = pymtTrxInf;
    }

    /**
     * Retrieves the payment transaction information.
     *
     * @return the payment transaction information
     */
    public GepgPymtTrxInf getPymtTrxInf() {
        return pymtTrxInf;
    }

    /**
     * Sets the payment transaction information.
     *
     * @param pymtTrxInf the payment transaction information to set
     */
    public void setPymtTrxInf(GepgPymtTrxInf pymtTrxInf) {
        this.pymtTrxInf = pymtTrxInf;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the value of the pymtTrxInf field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgPmtSpInfoMapper{" +
                "pymtTrxInf=" + pymtTrxInf +
                '}';
    }
}