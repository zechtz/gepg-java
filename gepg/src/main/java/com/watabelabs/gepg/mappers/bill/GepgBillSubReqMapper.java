package com.watabelabs.gepg.mappers.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Root class representing the GePG Bill Submission Request.
 */
@XmlRootElement(name = "gepgBillSubReq")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "billHdr", "billTrxInf" })
public class GepgBillSubReqMapper {

    @XmlElement(name = "GepgBillHeaderMapper", required = true)
    private GepgBillHdrMapper billHdr;

    @XmlElement(name = "BillTrxInf", required = true)
    private GepgBillTrxInfoMapper billTrxInf;

    /**
     * Default no-args constructor.
     */
    public GepgBillSubReqMapper() {
    }

    /**
     * All-args constructor.
     *
     * @param billHdr    the bill header
     * @param billTrxInf the bill transaction information
     */
    public GepgBillSubReqMapper(GepgBillHdrMapper billHdr, GepgBillTrxInfoMapper billTrxInf) {
        this.billHdr = billHdr;
        this.billTrxInf = billTrxInf;
    }

    /**
     * Gets the bill header.
     *
     * @return the bill header
     */
    public GepgBillHdrMapper getGepgBillHeaderMapper() {
        return billHdr;
    }

    /**
     * Sets the bill header.
     *
     * @param billHdr the new bill header
     */
    public void setGepgBillHeaderMapper(GepgBillHdrMapper billHdr) {
        this.billHdr = billHdr;
    }

    /**
     * Gets the bill transaction information.
     *
     * @return the bill transaction information
     */
    public GepgBillTrxInfoMapper getBillTrxInf() {
        return billTrxInf;
    }

    /**
     * Sets the bill transaction information.
     *
     * @param billTrxInf the new bill transaction information
     */
    public void setBillTrxInf(GepgBillTrxInfoMapper billTrxInf) {
        this.billTrxInf = billTrxInf;
    }

    /**
     * Generates a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GePgBillSubReq{" +
                "billHdr=" + billHdr +
                ", billTrxInf=" + billTrxInf +
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

        GepgBillSubReqMapper that = (GepgBillSubReqMapper) o;

        if (billHdr != null ? !billHdr.equals(that.billHdr) : that.billHdr != null)
            return false;
        return billTrxInf != null ? billTrxInf.equals(that.billTrxInf) : that.billTrxInf == null;
    }

    /**
     * Generates a hash code for this object.
     *
     * @return a hash code for this object
     */
    @Override
    public int hashCode() {
        int result = billHdr != null ? billHdr.hashCode() : 0;
        result = 31 * result + (billTrxInf != null ? billTrxInf.hashCode() : 0);
        return result;
    }
}
