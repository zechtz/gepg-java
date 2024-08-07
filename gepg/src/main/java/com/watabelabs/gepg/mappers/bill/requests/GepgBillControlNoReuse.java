package com.watabelabs.gepg.mappers.bill.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillControlNoReuse class is used for mapping the XML request
 * to the corresponding Java object. This class represents the bill subscription
 * request.
 *
 * <p>
 * It contains two main fields: billHdr and billTrxInf. The billHdr field holds
 * the bill header information, while the billTrxInf field holds the bill
 * transaction
 * information.
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
@XmlRootElement(name = "gepgBillSubReq")
@XmlAccessorType(XmlAccessType.FIELD)

public class GepgBillControlNoReuse {
    @XmlElement(name = "BillHdr")
    private GepgBillHdr billHdr;

    @XmlElement(name = "BillTrxInf")
    private GepgBillTrxInf billTrxInf;

    /**
     * Default no-argument constructor.
     */
    public GepgBillControlNoReuse() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided bill
     * header and transaction information.
     *
     * @param billHdr    the bill header information to set
     * @param billTrxInf the bill transaction information to set
     */
    public GepgBillControlNoReuse(GepgBillHdr billHdr, GepgBillTrxInf billTrxInf) {
        this.billHdr = billHdr;
        this.billTrxInf = billTrxInf;
    }

    /**
     * Sets the bill header information.
     *
     * @param billHdr the bill header information to set
     */
    public void setBillHdr(GepgBillHdr billHdr) {
        this.billHdr = billHdr;
    }

    /**
     * Retrieves the bill transaction information.
     *
     * @return the bill transaction information
     */
    public GepgBillTrxInf getBillTrxInf() {
        return billTrxInf;
    }

    /**
     * Sets the bill transaction information.
     *
     * @param billTrxInf the bill transaction information to set
     */
    public void setBillTrxInf(GepgBillTrxInf billTrxInf) {
        this.billTrxInf = billTrxInf;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the values of the billHdr and billTrxInf fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillSubReqMapper{" +
                "billHdr=" + billHdr +
                ", billTrxInf=" + billTrxInf +
                '}';
    }
}
