package com.watabelabs.gepg.mappers.reconciliation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgSpReconcRespAckResultMapper class is used for mapping the XML
 * response
 * to the corresponding Java object. This class represents the result of a
 * GEPG service provider reconciliation response acknowledgment, containing both
 * the acknowledgment details and the signature.
 *
 * <p>
 * It contains two fields: gepgSpReconcRespAck and gepgSignature. The
 * gepgSpReconcRespAck
 * field holds the details of the reconciliation response acknowledgment, while
 * the
 * gepgSignature field holds the signature associated with the acknowledgment.
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
public class GepgSpReconcRespAckResultMapper {

    /**
     * The acknowledgment details of the service provider reconciliation response.
     * <p>
     * This field is mapped to the XML element named "gepgSpReconcRespAck". It holds
     * the details of the acknowledgment response, encapsulated in a
     * {@link GepgSpReconcRespAckMapper} object.
     * </p>
     */
    @XmlElement(name = "gepgSpReconcRespAck")
    private GepgSpReconcRespAckMapper gepgSpReconcRespAck;

    /**
     * The signature associated with the service provider reconciliation response
     * acknowledgment.
     * <p>
     * This field is mapped to the XML element named "gepgSignature". It holds the
     * signature string that verifies the authenticity of the reconciliation
     * response
     * acknowledgment.
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
    public GepgSpReconcRespAckResultMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * reconciliation response acknowledgment details and signature.
     *
     * @param gepgSpReconcRespAck the reconciliation response acknowledgment details
     *                            to set
     * @param gepgSignature       the signature to set
     */
    public GepgSpReconcRespAckResultMapper(GepgSpReconcRespAckMapper gepgSpReconcRespAck, String gepgSignature) {
        this.gepgSpReconcRespAck = gepgSpReconcRespAck;
        this.gepgSignature = gepgSignature;
    }

    /**
     * Retrieves the acknowledgment details of the service provider reconciliation
     * response.
     *
     * @return the acknowledgment details
     */
    public GepgSpReconcRespAckMapper getGepgSpReconcRespAck() {
        return gepgSpReconcRespAck;
    }

    /**
     * Sets the acknowledgment details of the service provider reconciliation
     * response.
     *
     * @param gepgSpReconcRespAck the acknowledgment details to set
     */
    public void setGepgSpReconcRespAck(GepgSpReconcRespAckMapper gepgSpReconcRespAck) {
        this.gepgSpReconcRespAck = gepgSpReconcRespAck;
    }

    /**
     * Retrieves the signature associated with the service provider reconciliation
     * response acknowledgment.
     *
     * @return the signature
     */
    public String getGepgSignature() {
        return gepgSignature;
    }

    /**
     * Sets the signature associated with the service provider reconciliation
     * response acknowledgment.
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
     * that includes the values of the gepgSpReconcRespAck and gepgSignature fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgSpReconcRespAckResultMapper{" +
                "gepgSpReconcRespAck=" + gepgSpReconcRespAck +
                ", gepgSignature='" + gepgSignature + '\'' +
                '}';
    }
}
