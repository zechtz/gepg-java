package com.watabelabs.gepg.mappers.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * The GepgBillSubRespMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the bill submission
 * response, containing the bill transaction information.
 *
 * <p>
 * It contains a single field, billTrxInf, which stores the bill transaction
 * information of the subscription response.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * the
 * root element name, {@link XmlAccessorType} to specify the access type for
 * the fields, and {@link XmlSeeAlso} to indicate related classes.
 * </p>
 */
@XmlRootElement(name = "gepgBillSubResp")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(GepgBillTrxInfoMapper.class)
public class GepgBillSubRespMapper {

    /**
     * The bill transaction information.
     * <p>
     * This field is mapped to the XML element named "BillTrxInf". It holds
     * the details of the bill transaction, encapsulated in a
     * {@link GepgBillTrxInfMapper} object.
     * </p>
     */
    @XmlElement(name = "BillTrxInf")
    private GepgBillTrxInfMapper billTrxInf;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgBillSubRespMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided bill
     * transaction information.
     *
     * @param billTrxInf the bill transaction information to set
     */
    public GepgBillSubRespMapper(GepgBillTrxInfMapper billTrxInf) {
        this.billTrxInf = billTrxInf;
    }

    /**
     * Retrieves the bill transaction information.
     *
     * @return the bill transaction information
     */
    public GepgBillTrxInfMapper getBillTrxInf() {
        return billTrxInf;
    }

    /**
     * Sets the bill transaction information.
     *
     * @param billTrxInf the bill transaction information to set
     */
    public void setBillTrxInf(GepgBillTrxInfMapper billTrxInf) {
        this.billTrxInf = billTrxInf;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the value of the billTrxInf field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillSubRespMapper{" +
                "billTrxInf=" + billTrxInf +
                '}';
    }
}
