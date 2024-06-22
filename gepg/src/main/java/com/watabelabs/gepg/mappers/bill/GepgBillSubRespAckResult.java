package com.watabelabs.gepg.mappers.bill;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillSubRespAckResultMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the result of a
 * GEPG bill subscription acknowledgment, containing both the acknowledgment
 * details
 * and the signature.
 *
 * <p>
 * It contains two fields: gepgBillSubRespAck and gepgSignature. The
 * gepgBillSubRespAck
 * field holds the details of the bill subscription acknowledgment, while the
 * gepgSignature
 * field holds the signature associated with the acknowledgment.
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
public class GepgBillSubRespAckResult {

    /**
     * The acknowledgment details of the bill subscription.
     * <p>
     * This field is mapped to the XML element named "gepgBillSubRespAck". It holds
     * the details of the acknowledgment response, encapsulated in a
     * {@link GepgBillSubRespAck} object.
     * </p>
     */
    @XmlElement(name = "gepgBillSubRespAck")
    private GepgBillSubRespAck gepgBillSubRespAck;

    /**
     * The signature associated with the bill subscription acknowledgment.
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
    public GepgBillSubRespAckResult() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * acknowledgment details and signature.
     *
     * @param gepgBillSubRespAck the acknowledgment details to set
     * @param gepgSignature      the signature to set
     */
    public GepgBillSubRespAckResult(GepgBillSubRespAck gepgBillSubRespAck, String gepgSignature) {
        this.gepgBillSubRespAck = gepgBillSubRespAck;
        this.gepgSignature = gepgSignature;
    }

    /**
     * Retrieves the acknowledgment details of the bill subscription.
     *
     * @return the acknowledgment details
     */
    public GepgBillSubRespAck getGepgBillSubRespAck() {
        return gepgBillSubRespAck;
    }

    /**
     * Sets the acknowledgment details of the bill subscription.
     *
     * @param gepgBillSubRespAck the acknowledgment details to set
     */
    public void setGepgBillSubRespAck(GepgBillSubRespAck gepgBillSubRespAck) {
        this.gepgBillSubRespAck = gepgBillSubRespAck;
    }

    /**
     * Retrieves the signature associated with the bill subscription acknowledgment.
     *
     * @return the signature
     */
    public String getGepgSignature() {
        return gepgSignature;
    }

    /**
     * Sets the signature associated with the bill subscription acknowledgment.
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
     * that includes the values of the gepgBillSubRespAck and gepgSignature fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillSubRespAckResultMapper{" +
                "gepgBillSubRespAck=" + gepgBillSubRespAck +
                ", gepgSignature='" + gepgSignature + '\'' +
                '}';
    }
}
