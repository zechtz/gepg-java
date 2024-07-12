package com.watabelabs.gepg.mappers.reconciliation;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgReconcTrans class is used for mapping the XML response
 * to the corresponding Java object. This class represents the reconciliation
 * transactions, containing a list of reconciliation transaction information.
 *
 *
 * <p>
 * It contains a single field, reconcTrxInf, which is a list of
 *
 * {@link GepgReconcTrxInf} objects, each representing details of a
 * reconciliation
 * transaction.
 * </p>
 *
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elemen
 * s
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to defin
 * root elemen
 * t name and {@link XmlAccessorType} to specify the access type for
 * the fields.
 * </p>
 */
@XmlRootElement(name = "ReconcTrans")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgReconcTrans {

    /**
     * The
     * list of reconciliation transaction information.
     *
     * <p>
     * This field is mapped to the XML element named "ReconcTrxInf". I
     * holds a list of
     * {@link Recon
     * cTrxInfMapper} objects, each representing details of a reconciliation
     * transaction.
     * </p>
     */
    @XmlElement(name = "ReconcTrxInf")
    private List<GepgReconcTrxInf> reconcTrxInf;

    /**
     * Def
     * ault no-argument constructor.
     * <p>
     * This constructor is required for JA
     * XB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgReconcTrans() {
    }

    /**
     *
     * Parameterized constructor to initialize the object with the provided list of
     * reconciliation transaction information.
     *
     * @param reconcTrxInf the list of reconciliation transaction information to set
     */
    public GepgReconcTrans(List<GepgReconcTrxInf> reconcTrxInf) {
        this.reconcTrxInf = reconcTrxInf;
    }

    /**
     * Retrieves the list of reconciliation transaction information.
     *
     * @return the list of reconciliation transaction information
     */
    public List<GepgReconcTrxInf> getReconcTrxInf() {
        return reconcTrxInf;
    }

    /**
     * Sets the list of reconciliation transaction information.
     *
     * @param reconcTrxInf the list of reconciliation transaction information to set
     */
    public void setReconcTrxInf(List<GepgReconcTrxInf> reconcTrxInf) {
        this.reconcTrxInf = reconcTrxInf;
    }

    /**
     * Ret
     * urns a string representation of the object.
     * <p>
     * This method is overridden to provide a string re
     * presentation of the object
     * that includes the values of the reconcTrxInf field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "ReconcTransMapper{" +
                "reconcTrxInf=" + reconcTrxInf +
                '}';
    }
}
