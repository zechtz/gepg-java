package com.watabelabs.gepg.mappers.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgBillSubReqMapper class is used for mapping the XML request
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
public class GepgBillSubRequestMapper {

    @XmlElement(name = "BillHdr")
    private GepgBillHeaderMapper billHdr;

    @XmlElement(name = "BillTrxInf")
    private GepgBillTrxInfoMapper billTrxInf;

    // Getters and setters

    public GepgBillHeaderMapper getBillHdr() {
        return billHdr;
    }

    public void setBillHdr(GepgBillHeaderMapper billHdr) {
        this.billHdr = billHdr;
    }

    public GepgBillTrxInfoMapper getBillTrxInf() {
        return billTrxInf;
    }

    public void setBillTrxInf(GepgBillTrxInfoMapper billTrxInf) {
        this.billTrxInf = billTrxInf;
    }
}
