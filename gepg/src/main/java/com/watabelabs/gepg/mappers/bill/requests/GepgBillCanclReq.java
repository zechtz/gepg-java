package com.watabelabs.gepg.mappers.bill.requests;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * The GepgBillCanclReq class is used for mapping the XML request
 * to the corresponding Java object. This class represents the bill cancellation
 * request.
 *
 *
 * <p>
 * It contains fields for the service provider code, service provider system ID,
 * cance
 * llation reason, and a list of bill IDs. Each field is annotated with JAXB
 * annotations
 * to specify how the XML elements should be mapped to the Java fields.
 * </p>
 */
@XmlRootElement(name = "gepgBillCanclReq")
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillCanclReq {

    @XmlElement(name = "SpCode")
    private String spCode;

    @XmlElement(name = "SpSysId")
    private String spSysId;

    @XmlElement(name = "CanclReasn")
    private String canclReasn;

    @XmlElement(name = "BillId")
    private List<UUID> billIds;

    /**
     * Default no-argument constructor.
     */
    public GepgBillCanclReq() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided values.
     *
     * @param spCode     the service provider code
     * @param spSysId    the service provider system ID
     * @param canclReasn the cancellation reason
     * @param billIds    the list of bill IDs
     */
    public GepgBillCanclReq(String spCode, String spSysId, String canclReasn, List<UUID> billIds) {
        this.spCode = spCode;
        this.spSysId = spSysId;
        this.canclReasn = canclReasn;
        this.billIds = billIds;
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
     * Retrieves the service provider system ID.
     *
     * @return the service provider system ID
     */
    public String getSpSysId() {
        return spSysId;
    }

    /**
     * Sets the service provider system ID.
     *
     * @param spSysId the service provider system ID to set
     */
    public void setSpSysId(String spSysId) {
        this.spSysId = spSysId;
    }

    /**
     * Retrieves the cancellation reason.
     *
     * @return the cancellation reason
     */
    public String getCanclReasn() {
        return canclReasn;
    }

    /**
     * Sets the cancellation reason.
     *
     * @param canclReasn the cancellation reason to set
     */
    public void setCanclReasn(String canclReasn) {
        this.canclReasn = canclReasn;
    }

    /**
     * Retrieves the list of bill IDs.
     *
     * @return the list of bill IDs
     */
    public List<UUID> getBillIds() {
        return billIds;
    }

    /**
     * Sets the list of bill IDs.
     *
     * @param billIds the list of bill IDs to set
     */
    public void setBillIds(List<UUID> billIds) {
        this.billIds = billIds;
    }

    /**
     * Returns a string repr
     * esentation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the values of the fields.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillCancellationReqMapper{" +
                "spCode='" + spCode + '\'' +
                ", spSysId='" + spSysId + '\'' +
                ", canclReasn='" + canclReasn + '\'' +
                ", billIds=" + billIds +
                '}';
    }
}
