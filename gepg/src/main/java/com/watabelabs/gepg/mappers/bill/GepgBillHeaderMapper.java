package com.watabelabs.gepg.mappers.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * The GepgBillHeaderMapper class represents the bill header information.
 *
 * <p>
 * It contains fields for the service provider code and the return response
 * flag.
 * Each field is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields.
 * </p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillHeaderMapper {

    @XmlElement(name = "SpCode")
    private String spCode;

    @XmlElement(name = "RtrRespFlg")
    private boolean rtrRespFlg;

    /**
     * Default no-argument constructor.
     */
    public GepgBillHeaderMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param spCode     the service provider code
     * @param rtrRespFlg the return response flag
     */
    public GepgBillHeaderMapper(String spCode, boolean rtrRespFlg) {
        this.spCode = spCode;
        this.rtrRespFlg = rtrRespFlg;
    }

    /**
     * Retrieves the service provider code.
     *
     * @return the service provider code
     */
    public String getSpCode() {
        return spCode;
    }

    /**
     * Sets the service provider code.
     *
     * @param spCode the service provider code to set
     */
    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    /**
     * Retrieves the return response flag.
     *
     * @return the return response flag
     */
    public boolean isRtrRespFlg() {
        return rtrRespFlg;
    }

    /**
     * Sets the return response flag.
     *
     * @param rtrRespFlg the return response flag to set
     */
    public void setRtrRespFlg(boolean rtrRespFlg) {
        this.rtrRespFlg = rtrRespFlg;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the values of the fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillHeaderMapper{" +
                "spCode='" + spCode + '\'' +
                ", rtrRespFlg=" + rtrRespFlg +
                '}';
    }
}
