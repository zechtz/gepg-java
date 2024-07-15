package com.watabelabs.gepg.mappers.reconciliation.responses;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

    @XmlElement(name = "ReconcBatchInfo")
    private GepgReconcBatchInfo reconcBatchInfo;

    @XmlElement(name = "ReconcTrans")
    private GepgReconcTrans reconcTrans;

    // Default no-argument constructor
    public GepgSpReconcResp() {
    }

    // Parameterized constructor
    public GepgSpReconcResp(GepgReconcBatchInfo reconcBatchInfo, GepgReconcTrans reconcTrans) {
        this.reconcBatchInfo = reconcBatchInfo;
        this.reconcTrans = reconcTrans;
    }

    // Getter and setter methods
    public GepgReconcBatchInfo getReconcBatchInfo() {
        return reconcBatchInfo;
    }

    public void setReconcBatchInfo(GepgReconcBatchInfo reconcBatchInfo) {
        this.reconcBatchInfo = reconcBatchInfo;
    }

    public GepgReconcTrans getReconcTrans() {
        return reconcTrans;
    }

    public void setReconcTrans(GepgReconcTrans reconcTrans) {
        this.reconcTrans = reconcTrans;
    }

    @Override
    public String toString() {
        return "GepgSpReconcResp{" +
                "reconcBatchInfo=" + reconcBatchInfo +
                ", reconcTrans=" + reconcTrans +
                '}';
    }
}
