package com.watabelabs.gepg.mappers.reconciliation.responses;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.watabelabs.gepg.mappers.reconciliation.requests.GepgReconcBatchInfo;
import com.watabelabs.gepg.mappers.reconciliation.requests.GepgReconcTrans;

/**
 * The GepgSpReconcResp class is used for mapping the XML response
 * to the corresponding Java object. This class represents the entire
 * reconciliation response, containing batch information and a list
 * of reconciliation transactions.
 *
 * <p>
 * It contains fields for batch information and a list of reconciliation
 * transactions.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * the root element name and {@link XmlAccessorType} to specify the access type
 * for the fields.
 * </p>
 */
@XmlRootElement(name = "gepgSpReconcResp")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgSpReconcResp {

    /**
     * The batch information of the reconciliation response.
     * <p>
     * This field is mapped to the XML element named "ReconcBatchInfo". It holds
     * the details of the reconciliation batch, encapsulated in a
     * {@link GepgReconcBatchInfo} object.
     * </p>
     */
    @XmlElement(name = "ReconcBatchInfo")
    private GepgReconcBatchInfo reconcBatchInfo;

    /**
     * The reconciliation transactions of the reconciliation response.
     * <p>
     * This field is mapped to the XML element named "ReconcTrans". It holds
     * the details of the reconciliation transactions, encapsulated in a
     * {@link GepgReconcTrans} object.
     * </p>
     */
    @XmlElement(name = "ReconcTrans")
    private GepgReconcTrans reconcTrans;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgSpReconcResp() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param reconcBatchInfo the batch information of the reconciliation response
     * @param reconcTrans     the reconciliation transactions of the reconciliation
     *                        response
     */
    public GepgSpReconcResp(GepgReconcBatchInfo reconcBatchInfo, GepgReconcTrans reconcTrans) {
        this.reconcBatchInfo = reconcBatchInfo;
        this.reconcTrans = reconcTrans;
    }

    /**
     * Gets the batch information of the reconciliation response.
     *
     * @return the batch information of the reconciliation response
     */
    public GepgReconcBatchInfo getReconcBatchInfo() {
        return reconcBatchInfo;
    }

    /**
     * Sets the batch information of the reconciliation response.
     *
     * @param reconcBatchInfo the batch information of the reconciliation response
     */
    public void setReconcBatchInfo(GepgReconcBatchInfo reconcBatchInfo) {
        this.reconcBatchInfo = reconcBatchInfo;
    }

    /**
     * Gets the reconciliation transactions of the reconciliation response.
     *
     * @return the reconciliation transactions of the reconciliation response
     */
    public GepgReconcTrans getReconcTrans() {
        return reconcTrans;
    }

    /**
     * Sets the reconciliation transactions of the reconciliation response.
     *
     * @param reconcTrans the reconciliation transactions of the reconciliation
     *                    response
     */
    public void setReconcTrans(GepgReconcTrans reconcTrans) {
        this.reconcTrans = reconcTrans;
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
        return "GepgSpReconcResp{" +
                "reconcBatchInfo=" + reconcBatchInfo +
                ", reconcTrans=" + reconcTrans +
                '}';
    }
}
