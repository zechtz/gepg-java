package com.watabelabs.gepg.mappers.bill;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * The GepgBillItemMapper class is used for mapping the XML response
 * to the corresponding Java object. This class represents a collection
 * of bill items within a GEPG bill transaction.
 *
 * <p>
 * It contains a single field, billItem, which is a list of
 * {@link BillItemMapper} objects. Each {@link BillItemMapper} object
 * represents the details of an individual bill item.
 * </p>
 *
 * <p>
 * The class is annotated with JAXB annotations to specify how the XML elements
 * should be mapped to the Java fields. It uses {@link XmlAccessorType} to
 * specify
 * the access type for the fields.
 * </p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GepgBillItem {

    @XmlElement(name = "BillItemRef", required = true)
    private String billItemRef;

    @XmlElement(name = "UseItemRefOnPay", required = true)
    private String useItemRefOnPay;

    @XmlElement(name = "BillItemAmt", required = true)
    private double billItemAmt;

    @XmlElement(name = "BillItemEqvAmt", required = true)
    private double billItemEqvAmt;

    @XmlElement(name = "BillItemMiscAmt", required = true)
    private double billItemMiscAmt;

    @XmlElement(name = "GfsCode", required = true)
    private String gfsCode;

    /**
     * Default no-args constructor.
     */
    public GepgBillItem() {
    }

    /**
     * All-args constructor.
     *
     * @param billItemRef     the bill item reference
     * @param useItemRefOnPay the use item reference on pay
     * @param billItemAmt     the bill item amount
     * @param billItemEqvAmt  the bill item equivalent amount
     * @param billItemMiscAmt the bill item miscellaneous amount
     * @param gfsCode         the GFS code
     */
    public GepgBillItem(String billItemRef, String useItemRefOnPay, double billItemAmt, double billItemEqvAmt,
            Double billItemMiscAmt, String gfsCode) {
        this.billItemRef = billItemRef;
        this.useItemRefOnPay = useItemRefOnPay;
        this.billItemAmt = billItemAmt;
        this.billItemEqvAmt = billItemEqvAmt;
        this.billItemMiscAmt = billItemMiscAmt;
        this.gfsCode = gfsCode;
    }

    /**
     * Gets the bill item reference.
     *
     * @return the bill item reference
     */
    public String getBillItemRef() {
        return billItemRef;
    }

    /**
     * Sets the bill item reference.
     *
     * @param billItemRef the new bill item reference
     */
    public void setBillItemRef(String billItemRef) {
        this.billItemRef = billItemRef;
    }

    /**
     * Gets the use item reference on pay flag.
     *
     * @return the use item reference on pay flag
     */
    public String getUseItemRefOnPay() {
        return useItemRefOnPay;
    }

    /**
     * Sets the use item reference on pay flag.
     *
     * @param useItemRefOnPay the new use item reference on pay flag
     */
    public void setUseItemRefOnPay(String useItemRefOnPay) {
        this.useItemRefOnPay = useItemRefOnPay;
    }

    /**
     * Gets the bill item amount.
     *
     * @return the bill item amount
     */
    public double getBillItemAmt() {
        return billItemAmt;
    }

    /**
     * Sets the bill item amount.
     *
     * @param billItemAmt the new bill item amount
     */
    public void setBillItemAmt(double billItemAmt) {
        this.billItemAmt = billItemAmt;
    }

    /**
     * Gets the bill item equivalent amount.
     *
     * @return the bill item equivalent amount
     */
    public double getBillItemEqvAmt() {
        return billItemEqvAmt;
    }

    /**
     * Sets the bill item equivalent amount.
     *
     * @param billItemEqvAmt the new bill item equivalent amount
     */
    public void setBillItemEqvAmt(double billItemEqvAmt) {
        this.billItemEqvAmt = billItemEqvAmt;
    }

    /**
     * Gets the bill item miscellaneous amount.
     *
     * @return the bill item miscellaneous amount
     */
    public double getBillItemMiscAmt() {
        return billItemMiscAmt;
    }

    /**
     * Sets the bill item miscellaneous amount.
     *
     * @param billItemMiscAmt the new bill item miscellaneous amount
     */
    public void setBillItemMiscAmt(double billItemMiscAmt) {
        this.billItemMiscAmt = billItemMiscAmt;
    }

    /**
     * Gets the GFS code.
     *
     * @return the GFS code
     */
    public String getGfsCode() {
        return gfsCode;
    }

    /**
     * Sets the GFS code.
     *
     * @param gfsCode the new GFS code
     */
    public void setGfsCode(String gfsCode) {
        this.gfsCode = gfsCode;
    }

    /**
     * Generates a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "BillItem{" +
                "billItemRef='" + billItemRef + '\'' +
                ", useItemRefOnPay='" + useItemRefOnPay + '\'' +
                ", billItemAmt=" + billItemAmt +
                ", billItemEqvAmt=" + billItemEqvAmt +
                ", billItemMiscAmt=" + billItemMiscAmt +
                ", gfsCode='" + gfsCode + '\'' +
                '}';
    }

    /**
     * Generates a hash code for this object.
     *
     * @return a hash code for this object
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = billItemRef != null ? billItemRef.hashCode() : 0;
        result = 31 * result + (useItemRefOnPay != null ? useItemRefOnPay.hashCode() : 0);
        temp = Double.doubleToLongBits(billItemAmt);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(billItemEqvAmt);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(billItemMiscAmt);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (gfsCode != null ? gfsCode.hashCode() : 0);
        return result;
    }
}
