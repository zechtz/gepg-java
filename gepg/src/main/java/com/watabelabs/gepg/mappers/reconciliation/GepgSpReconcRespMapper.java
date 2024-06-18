package com.watabelabs.gepg.mappers.reconciliation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * The GepgSpReconcRespMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents the service provider
 * reconciliation response, containing both the reconciliation batch information
 * and the reconciliation transactions.
 *
 * <p>
 * I
 * t contains two fields: reconcBatchInfo and reconcTrans. The reconcBatchInfo
 * field holds the details of the reconciliation batch, while the reconcTrans
 * ield
 * holds the reconciliation transactions.<
 * /p>
 *
 * <p>
 * Th
 * e class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlRootElement} to define
 * t
 * e
 * root element name and {@link XmlAccessorType} to specify the access type for
 *
 * he fields. The {@link XmlSeeAlso} annotation indicates related classes.
 * </p
 * >
 */
@XmlRootElement(name = "gepgSpReconcResp")
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlSeeAlso({ GepgReconcBatchInfoMapper.class, GepgReconcTransMapper.class })
public class GepgSpReconcRespMapper {

    /**
     * The reconciliation batch information.
     * <p>
     * Thi
     * s field is mapped to the XML element named "ReconcBatchInfo". It holds
     * the details of the reconciliation batch, encapsulated in a
     *
     * link ReconcBatchInfoMapper} object.
     * </p>
     *
     */
    @XmlElement(name = "ReconcBatchInfo")
    private GepgReconcBatchInfoMapper reconcBatchInfo;

    /**
     * The reconciliation transactions.
     * <p>
     * This
     * field is mapped to the XML element named "ReconcTrans". It holds the
     * details of the reconciliation transactions, encapsulated in a
     *
     * ink ReconcTransMapper} object.
     * </p>
     *
     */
    @XmlElement(name = "ReconcTrans")
    private GepgReconcTransMapper reconcTrans;

    /**
     * Default no-argument constructor.
     * <p>
     * This
     * constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     *
     */
    public GepgSpReconcRespMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided reco
     * ciliation batch information and transactions.
     *
     * @param reconcBatchInfo the reconciliation batch information to set
     * @param reconcTrans     the r conciliation transactions to set
     */
    public GepgSpReconcRespMapper(GepgReconcBatchInfoMapper reconcBatchInfo, GepgReconcTransMapper reconcTrans) {
        this.reconcBatchInfo = reconcBatchInfo;
        this.reconcTrans = reconcTrans;
    }

    /**
     * Retrieves the reconciliation batch information.
     *
     * @return the reconciliation batch information
     */
    public GepgReconcBatchInfoMapper getReconcBatchInfo() {
        return reconcBatchInfo;
    }

    /**
     * Sets the reconciliation batch information.
     *
     * @param reconcBatchInfo the reconciliation batch information to set
     */
    public void setReconcBatchInfo(GepgReconcBatchInfoMapper reconcBatchInfo) {
        this.reconcBatchInfo = reconcBatchInfo;
    }

    /**
     * Retrieves the reconciliation transactions.
     *
     * @return the reconciliation transactions
     */
    public GepgReconcTransMapper getReconcTrans() {
        return reconcTrans;
    }

    /**
     * Sets the reconciliation transactions.
     *
     * @param reconcTrans the reconciliation transactions to set
     */
    public void setReconcTrans(GepgReconcTransMapper reconcTrans) {
        this.reconcTrans = reconcTrans;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This metho
     * d is overridden to provide a string representation of the object
     * that includes the values of the reconcBatchInfo and reconcTrans fields.
     * </p>
     *
     * *
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgSpReconcRespMapper{" +
                "reconcBatchInfo=" + reconcBatchInfo +
                ", reconcTrans=" + reconcTrans +
                '}';
    }
}
