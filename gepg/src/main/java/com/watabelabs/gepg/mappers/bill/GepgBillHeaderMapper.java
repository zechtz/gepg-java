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

    @XmlElement(name = "SpCode", required = true)
    private String spCode;

    @XmlElement(name = "RtrRespFlg", required = true)
    private boolean rtrRespFlg;

    /**
     * Default no-args constructor.
     */
    public GepgBillHeaderMapper() {
    }

    /**
     * All-args constructor.
     *
     * @param spCode     the service provider code
     * @param rtrRespFlg the return response flag
     */
    public GepgBillHeaderMapper(String spCode, boolean rtrRespFlg) {
        this.spCode = spCode;
        this.rtrRespFlg = rtrRespFlg;
    }

    /**
     * Gets the service provider code.
     *
     * @return the service provider code
     */
    public String getSpCode() {
        return spCode;
    }

    /**
     * Sets the service provider code.
     *
     * @param spCode the new service provider code
     */
    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }

    /**
     * Gets the return response flag.
     *
     * @return the return response flag
     */
    public boolean isRtrRespFlg() {
        return rtrRespFlg;
    }

    /**
     * Sets the return response flag.
     *
     * @param rtrRespFlg the new return response flag
     */
    public void setRtrRespFlg(boolean rtrRespFlg) {
        this.rtrRespFlg = rtrRespFlg;
    }

    /**
     * Generates a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "BillHdr{" +
                "spCode='" + spCode + '\'' +
                ", rtrRespFlg=" + rtrRespFlg +
                '}';
    }

    /**
     * Checks if this object is equal to another object.
     *
     * @param o the other object
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GepgBillHeaderMapper billHdr = (GepgBillHeaderMapper) o;

        if (rtrRespFlg != billHdr.rtrRespFlg)
            return false;
        return spCode != null ? spCode.equals(billHdr.spCode) : billHdr.spCode == null;
    }

    /**
     * Generates a hash code for this object.
     *
     * @return a hash code for this object
     */
    @Override
    public int hashCode() {
        int result = spCode != null ? spCode.hashCode() : 0;
        result = 31 * result + (rtrRespFlg ? 1 : 0);
        return result;
    }
}
