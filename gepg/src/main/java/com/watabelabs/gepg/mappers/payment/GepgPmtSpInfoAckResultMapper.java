package com.watabelabs.gepg.mappers.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgPmtSpInfoAckResultMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the result of a
 * GEPG payment service provider information acknowledgment, containing both
 * the acknowledgment details and the signature.
 *
 * <p>
 * It contains two fields: gepgPmtSpInfoAck and gepgSignature. The
 * gepgPmtSpInfoAck
 * field holds the details of the payment service provider information
 * acknowledgment,
 * while the gepgSignature field holds the signature associated with the
 * acknowledgment.
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
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgPmtSpInfoAckResultMapper {

    /**
     * The acknowledgment details of the payment service provider information.
     * <p>
     * This field is mapped to the XML element named "gepgPmtSpInfoAck". It holds
     * the details of the acknowledgment response, encapsulated in a
     * {@link GepgPmtSpInfoAckMapper} object.
     * </p>
     */
    @XmlElement(name = "gepgPmtSpInfoAck")
    private GepgPmtSpInfoAckMapper gepgPmtSpInfoAck;

    /**
     * The signature associated with the payment service provider information
     * acknowledgment.
     * <p>
     * This field is mapped to the XML element named "gepgSignature". It holds
     * the signature string that verifies the authenticity of the acknowledgment
     * response.
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
    public GepgPmtSpInfoAckResultMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * acknowledgment details and signature.
     *
     * @param gepgPmtSpInfoAck the acknowledgment details to set
     * @param gepgSignature    the signature to set
     */
    public GepgPmtSpInfoAckResultMapper(GepgPmtSpInfoAckMapper gepgPmtSpInfoAck, String gepgSignature) {
        this.gepgPmtSpInfoAck = gepgPmtSpInfoAck;
        this.gepgSignature = gepgSignature;
    }

    /**
     * Retrieves the acknowledgment details of the payment service provider
     * information.
     *
     * @return the acknowledgment details
     */
    public GepgPmtSpInfoAckMapper getGepgPmtSpInfoAck() {
        return gepgPmtSpInfoAck;
    }

    /**
     * Sets the acknowledgment details of the payment service provider information.
     *
     * @param gepgPmtSpInfoAck the acknowledgment details to set
     */
    public void setGepgPmtSpInfoAck(GepgPmtSpInfoAckMapper gepgPmtSpInfoAck) {
        this.gepgPmtSpInfoAck = gepgPmtSpInfoAck;
    }

    /**
     * Retrieves the signature associated with the payment service provider
     * information acknowledgment.
     *
     * @return the signature
     */
    public String getGepgSignature() {
        return gepgSignature;
    }

    /**
     * Sets the signature associated with the payment service provider information
     * acknowledgment.
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
     * that includes the values of the gepgPmtSpInfoAck and gepgSignature fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgPmtSpInfoAckResultMapper{" +
                "gepgPmtSpInfoAck=" + gepgPmtSpInfoAck +
                ", gepgSignature='" + gepgSignature + '\'' +
                '}';
    }
}
