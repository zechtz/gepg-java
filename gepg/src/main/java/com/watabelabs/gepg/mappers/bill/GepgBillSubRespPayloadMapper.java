package com.watabelabs.gepg.mappers.bill;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillSubRespPayloadMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the payload for
 * GEPG bill subscription response, containing both the subscription response
 * and the signature.
 *
 * <p>
 * It contains two fields: gepgBillSubResp and gepgSignature. The
 * gepgBillSubResp
 * field holds the details of the bill subscription response, while the
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
public class GepgBillSubRespPayloadMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The bill subscription response details.
     * <p>
     * This field is mapped to the XML element named "gepgBillSubResp". It holds the
     * details of the bill subscription response, encapsulated in a
     * {@link GepgBillSubRespMapper} object.
     * </p>
     */
    @XmlElement(name = "gepgBillSubResp")
    private GepgBillSubRespMapper gepgBillSubResp;

    /**
     * The signature associated with the bill subscription response.
     * <p>
     * This field is mapped to the XML element named "gepgSignature". It holds the
     * signature string that verifies the authenticity of the bill subscription
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
    public GepgBillSubRespPayloadMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided bill
     * subscription response and signature.
     *
     * @param gepgBillSubResp the bill subscription response to set
     * @param gepgSignature   the signature to set
     */
    public GepgBillSubRespPayloadMapper(GepgBillSubRespMapper gepgBillSubResp, String gepgSignature) {
        this.gepgBillSubResp = gepgBillSubResp;
        this.gepgSignature = gepgSignature;
    }

    /**
     * Retrieves the bill subscription response details.
     *
     * @return the bill subscription response details
     */
    public GepgBillSubRespMapper getGepgBillSubResp() {
        return gepgBillSubResp;
    }

    /**
     * Sets the bill subscription response details.
     *
     * @param gepgBillSubResp the bill subscription response details to set
     */
    public void setGepgBillSubResp(GepgBillSubRespMapper gepgBillSubResp) {
        this.gepgBillSubResp = gepgBillSubResp;
    }

    /**
     * Retrieves the signature associated with the bill subscription response.
     *
     * @return the signature
     */
    public String getGepgSignature() {
        return gepgSignature;
    }

    /**
     * Sets the signature associated with the bill subscription response.
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
     * that includes the values of the gepgBillSubResp and gepgSignature fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillSubRespPayloadMapper{" +
                "gepgBillSubResp=" + gepgBillSubResp +
                ", gepgSignature='" + gepgSignature + '\'' +
                '}';
    }
}
