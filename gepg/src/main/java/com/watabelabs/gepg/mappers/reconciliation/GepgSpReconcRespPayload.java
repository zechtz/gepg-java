package com.watabelabs.gepg.mappers.reconciliation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgSpReconcRespPayload class is used for mapping the XML response
 * to the corresponding Java object. This class represents the payload for
 * GEPG service provider reconciliation response, containing both the
 * reconciliation
 * response and the signature.
 *
 * <p>
 * It contains two fields: gepgSpReconcResp and gepgSignature. The
 * gepgSpReconcResp
 * field holds the details of the reconciliation response, while the
 * gepgSignature
 * field holds the signature associated with the response.
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
public class GepgSpReconcRespPayload {

    /**
     * The reconciliation response details.
     * <p>
     * This field is mapped to the XML element named "gepgSpReconcResp". It holds
     * the
     * details of the reconciliation response, encapsulated in a
     * {@link GepgSpReconcRespMapper} object.
     * </p>
     */
    @XmlElement(name = "gepgSpReconcResp")
    private GepgSpReconcRespMapper gepgSpReconcResp;

    /**
     * The signature associated with the reconciliation response.
     * <p>
     * This field is mapped to the XML element named "gepgSignature". It holds the
     * signature string that verifies the authenticity of the reconciliation
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
    public GepgSpReconcRespPayload() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided
     * reconciliation response and signature.
     *
     * @param gepgSpReconcResp the reconciliation response to set
     * @param gepgSignature    the signature to set
     */
    public GepgSpReconcRespPayload(GepgSpReconcRespMapper gepgSpReconcResp, String gepgSignature) {
        this.gepgSpReconcResp = gepgSpReconcResp;
        this.gepgSignature = gepgSignature;
    }

    /**
     * Retrieves the reconciliation response details.
     *
     * @return the reconciliation response details
     */
    public GepgSpReconcRespMapper getGepgSpReconcResp() {
        return gepgSpReconcResp;
    }

    /**
     * Sets the reconciliation response details.
     *
     * @param gepgSpReconcResp the reconciliation response details to set
     */
    public void setGepgSpReconcResp(GepgSpReconcRespMapper gepgSpReconcResp) {
        this.gepgSpReconcResp = gepgSpReconcResp;
    }

    /**
     * Retrieves the signature associated with the reconciliation response.
     *
     * @return the signature
     */
    public String getGepgSignature() {
        return gepgSignature;
    }

    /**
     * Sets the signature associated with the reconciliation response.
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
     * that includes the values of the gepgSpReconcResp and gepgSignature fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgSpReconcRespPayload{" +
                "gepgSpReconcResp=" + gepgSpReconcResp +
                ", gepgSignature='" + gepgSignature + '\'' +
                '}';
    }
}
