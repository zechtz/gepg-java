package com.watabelabs.gepg.mappers.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgPmtSpInfoPayloadMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the payload for
 * GEPG payment service provider information, containing both the payment
 * service provider information and the signature.
 *
 * <p>
 * It contains two fields: gepgPmtSpInfo and gepgSignature. The gepgPmtSpInfo
 * field holds the details of the payment service provider information, while
 * the
 * gepgSignature field holds the signature associated with the information.
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
@XmlRootElement(name = "Gepg")
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgPmtSpInfoPayloadMapper {

    /**
     * The payment service provider information.
     * <p>
     * This field is mapped to the XML element named "gepgPmtSpInfo". It holds the
     * details of the payment service provider information, encapsulated in a
     * {@link GepgPmtSpInfoMapper} object.
     * </p>
     */
    @XmlElement(name = "gepgPmtSpInfo")
    private GepgPmtSpInfoMapper gepgPmtSpInfo;

    /**
     * The signature associated with the payment service provider information.
     * <p>
     * This field is mapped to the XML element named "gepgSignature". It holds the
     * signature string that verifies the authenticity of the payment service
     * provider
     * information.
     * </p>
     */
    @XmlElement(name = "gepgSignature")
    private String gepgSignature;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgPmtSpInfoPayloadMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided payment
     * service provider information and signature.
     *
     * @param gepgPmtSpInfo the payment service provider information to set
     * @param gepgSignature the signature to set
     */
    public GepgPmtSpInfoPayloadMapper(GepgPmtSpInfoMapper gepgPmtSpInfo, String gepgSignature) {
        this.gepgPmtSpInfo = gepgPmtSpInfo;
        this.gepgSignature = gepgSignature;
    }

    /**
     * Retrieves the payment service provider information.
     *
     * @return the payment service provider information
     */
    public GepgPmtSpInfoMapper getGepgPmtSpInfo() {
        return gepgPmtSpInfo;
    }

    /**
     * Sets the payment service provider information.
     *
     * @param gepgPmtSpInfo the payment service provider information to set
     */
    public void setGepgPmtSpInfo(GepgPmtSpInfoMapper gepgPmtSpInfo) {
        this.gepgPmtSpInfo = gepgPmtSpInfo;
    }

    /**
     * Retrieves the signature associated with the payment service provider
     * information.
     *
     * @return the signature
     */
    public String getGepgSignature() {
        return gepgSignature;
    }

    /**
     * Sets the signature associated with the payment service provider information.
     *
     * @param gepgSignature the signature to set
     */
    public void setGepgSignature(String gepgSignature) {
        this.gepgSignature = gepgSignature;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the values of the gepgPmtSpInfo and gepgSignature fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgPmtSpInfoPayloadMapper{" +
                "gepgPmtSpInfo=" + gepgPmtSpInfo +
                ", gepgSignature='" + gepgSignature + '\'' +
                '}';
    }
}
