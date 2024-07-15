package com.watabelabs.gepg.mappers.reconciliation.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The GepgReconcBatchInfo class is used for mapping the XML response
 * to the corresponding Java object. This class represents the reconciliation
 * batch information.
 *
 * <p>
 * It contains four fields: spReconcReqId, spCode, spName, and reconcStsCode.
 * These fields
 * store the service provider reconciliation request ID, service provider code,
 * service provider name, and reconciliation status code, respectively.
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
@XmlRootElement(name = "ReconcBatchInfo")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class GepgReconcBatchInfo {

    /**
     * The service provider reconciliation request ID.
     * <p>
     * This field is mapped to the XML element named "SpReconcReqId". It holds the
     * unique identifier for the service provider's reconciliation request.
     * </p>
     */
    @XmlElement(name = "SpReconcReqId")
    private Long spReconcReqId;

    /**
     * The service provider code.
     * <p>
     * This field is mapped to the XML element named "SpCode". It holds the unique
     * code of the service provider.
     * </p>
     */
    @XmlElement(name = "SpCode")
    private String spCode;

    /**
     * The service provider name.
     * <p>
     * This field is mapped to the XML element named "SpName". It holds the name of
     * the service provider.
     * </p>
     */
    @XmlElement(name = "SpName")
    private String spName;

    /**
     * The reconciliation status code.
     * <p>
     * This field is mapped to the XML element named "ReconcStsCode". It holds the
     * status code of the reconciliation, indicating whether it was successful or if
     * there was an error.
     * </p>
     */
    @XmlElement(name = "ReconcStsCode")
    private String reconcStsCode;

    /**
     * Default no-argument constructor.
     * <p>
     * This constructor is required for JAXB to be able to create an instance of
     * the class when unmarshalling XML data.
     * </p>
     */
    public GepgReconcBatchInfo() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param spReconcReqId the service provider reconciliation request ID
     * @param spCode        the service provider code
     * @param spName        the service provider name
     * @param reconcStsCode the reconciliation status code
     */
    public GepgReconcBatchInfo(Long spReconcReqId, String spCode, String spName, String reconcStsCode) {
        this.spReconcReqId = spReconcReqId;
        this.spCode = spCode;
        this.spName = spName;
        this.reconcStsCode = reconcStsCode;
    }

    /**
     * Retrieves the service provider reconciliation request ID.
     *
     * @return the service provider reconciliation request ID
     */
    public Long getSpReconcReqId() {
        return spReconcReqId;
    }

    /**
     * Sets the service provider reconciliation request ID.
     *
     * @param spReconcReqId the service provider reconciliation request ID to set
     */
    public void setSpReconcReqId(Long spReconcReqId) {
        this.spReconcReqId = spReconcReqId;
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
     * Retrieves the service provider name.
     *
     * @return the service provider name
     */
    public String getSpName() {
        return spName;
    }

    /**
     * Sets the service provider name.
     *
     * @param spName the service provider name to set
     */
    public void setSpName(String spName) {
        this.spName = spName;
    }

    /**
     * Retrieves the reconciliation status code.
     *
     * @return the reconciliation status code
     */
    public String getReconcStsCode() {
        return reconcStsCode;
    }

    /**
     * Sets the reconciliation status code.
     *
     * @param reconcStsCode the reconciliation status code to set
     */
    public void setReconcStsCode(String reconcStsCode) {
        this.reconcStsCode = reconcStsCode;
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
        return "ReconcBatchInfoMapper{" +
                "spReconcReqId=" + spReconcReqId +
                ", spCode='" + spCode + '\'' +
                ", spName='" + spName + '\'' +
                ", reconcStsCode='" + reconcStsCode + '\'' +
                '}';
    }
}
