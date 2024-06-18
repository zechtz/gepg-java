package com.watabelabs.gepg.mappers.bill;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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
public class GepgBillItemMapper {

    /**
     * The list of bill items.
     * <p>
     * This field is mapped to the XML element named "BillItem". It holds a list of
     * {@link BillItemMapper} objects, each representing details of an individual
     * bill item.
     * </p>
     */
    @XmlElement(name = "BillItem")
    private List<BillItemMapper> billItem;

    /**
     * Default no-argument constructor.
     */
    public GepgBillItemMapper() {
    }

    /**
     * Parameterized constructor to initialize the object with the provided list of
     * bill items.
     *
     * @param billItem the list of bill items to set
     */
    public GepgBillItemMapper(List<BillItemMapper> billItem) {
        this.billItem = billItem;
    }

    /**
     * Retrieves the list of bill items.
     *
     * @return the list of bill items
     */
    public List<BillItemMapper> getBillItem() {
        return billItem;
    }

    /**
     * Sets the list of bill items.
     *
     * @param billItem the list of bill items to set
     */
    public void setBillItem(List<BillItemMapper> billItem) {
        this.billItem = billItem;
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method is overridden to provide a string representation of the object
     * that includes the values of the billItem field.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GepgBillItemMapper{" +
                "billItem=" + billItem +
                '}';
    }

    /**
     * The BillItemMapper class represents the details of an individual bill item.
     *
     * <p>
     * It contains fields for the bill item reference, whether to use the item
     * reference
     * on payment, the bill item amount, the equivalent amount, the miscellaneous
     * amount,
     * and the GFS code. Each field is annotated with JAXB annotations to specify
     * how the
     * XML elements should be mapped to the Java fields.
     * </p>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BillItemMapper {

        @XmlElement(name = "BillItemRef")
        private String billItemRef;

        @XmlElement(name = "UseItemRefOnPay")
        private String useItemRefOnPay;

        @XmlElement(name = "BillItemAmt")
        private BigDecimal billItemAmt;

        @XmlElement(name = "BillItemEqvAmt")
        private BigDecimal billItemEqvAmt;

        @XmlElement(name = "BillItemMiscAmt")
        private BigDecimal billItemMiscAmt;

        @XmlElement(name = "GfsCode")
        private String gfsCode;

        /**
         * Default no-argument constructor.
         */
        public BillItemMapper() {
        }

        /**
         * Parameterized constructor to initialize the object with the provided values.
         *
         * @param billItemRef     the bill item reference
         * @param useItemRefOnPay whether to use the item reference on payment
         * @param billItemAmt     the bill item amount
         * @param billItemEqvAmt  the equivalent amount of the bill item
         * @param billItemMiscAmt the miscellaneous amount of the bill item
         * @param gfsCode         the GFS code for the bill item
         */
        public BillItemMapper(String billItemRef, String useItemRefOnPay, BigDecimal billItemAmt,
                BigDecimal billItemEqvAmt, BigDecimal billItemMiscAmt, String gfsCode) {
            this.billItemRef = billItemRef;
            this.useItemRefOnPay = useItemRefOnPay;
            this.billItemAmt = billItemAmt;
            this.billItemEqvAmt = billItemEqvAmt;
            this.billItemMiscAmt = billItemMiscAmt;
            this.gfsCode = gfsCode;
        }

        /**
         * Retrieves the bill item reference.
         *
         * @return the bill item reference
         */
        public String getBillItemRef() {
            return billItemRef;
        }

        /**
         * Sets the bill item reference.
         *
         * @param billItemRef the bill item reference to set
         */
        public void setBillItemRef(String billItemRef) {
            this.billItemRef = billItemRef;
        }

        /**
         * Retrieves whether to use the item reference on payment.
         *
         * @return the value indicating whether to use the item reference on payment
         */
        public String getUseItemRefOnPay() {
            return useItemRefOnPay;
        }

        /**
         * Sets whether to use the item reference on payment.
         *
         * @param useItemRefOnPay the value indicating whether to use the item reference
         *                        on payment
         */
        public void setUseItemRefOnPay(String useItemRefOnPay) {
            this.useItemRefOnPay = useItemRefOnPay;
        }

        /**
         * Retrieves the bill item amount.
         *
         * @return the bill item amount
         */
        public BigDecimal getBillItemAmt() {
            return billItemAmt;
        }

        /**
         * Sets the bill item amount.
         *
         * @param billItemAmt the bill item amount to set
         */
        public void setBillItemAmt(BigDecimal billItemAmt) {
            this.billItemAmt = billItemAmt;
        }

        /**
         * Retrieves the equivalent amount of the bill item.
         *
         * @return the equivalent amount of the bill item
         */
        public BigDecimal getBillItemEqvAmt() {
            return billItemEqvAmt;
        }

        /**
         * Sets the equivalent amount of the bill item.
         *
         * @param billItemEqvAmt the equivalent amount of the bill item to set
         */
        public void setBillItemEqvAmt(BigDecimal billItemEqvAmt) {
            this.billItemEqvAmt = billItemEqvAmt;
        }

        /**
         * Retrieves the miscellaneous amount of the bill item.
         *
         * @return the miscellaneous amount of the bill item
         */
        public BigDecimal getBillItemMiscAmt() {
            return billItemMiscAmt;
        }

        /**
         * Sets the miscellaneous amount of the bill item.
         *
         * @param billItemMiscAmt the miscellaneous amount of the bill item to set
         */
        public void setBillItemMiscAmt(BigDecimal billItemMiscAmt) {
            this.billItemMiscAmt = billItemMiscAmt;
        }

        /**
         * Retrieves the GFS code for the bill item.
         *
         * @return the GFS code for the bill item
         */
        public String getGfsCode() {
            return gfsCode;
        }

        /**
         * Sets the GFS code for the bill item.
         *
         * @param gfsCode the GFS code for the bill item to set
         */
        public void setGfsCode(String gfsCode) {
            this.gfsCode = gfsCode;
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
            return "BillItemMapper{" +
                    "billItemRef='" + billItemRef + '\'' +
                    ", useItemRefOnPay='" + useItemRefOnPay + '\'' +
                    ", billItemAmt=" + billItemAmt +
                    ", billItemEqvAmt=" + billItemEqvAmt +
                    ", billItemMiscAmt=" + billItemMiscAmt +
                    ", gfsCode='" + gfsCode + '\'' +
                    '}';
        }
    }
}
