package com.watabelabs.gepg.mappers.payment.requests;

/**
 * Enum representing the different bill payment options.
 *
 * <ul>
 * <li>FULL: The bill must be paid in one installment with the amount paid equal
 * to or greater than the billed amount.</li>
 * <li>PARTIAL: The bill may be paid in multiple installments with the last
 * installment being greater than or equal to the billed amount.</li>
 * <li>EXACT: The bill must be paid in one installment with the amount paid
 * being exactly the billed amount.</li>
 * </ul>
 */
public enum GepgBillPaymentOption {
    /**
     * The bill must be paid in one installment with the amount paid equal to or
     * greater than the billed amount.
     */
    FULL,

    /**
     * The bill may be paid in multiple installments with the last installment
     * being greater than or equal to the billed amount.
     */
    PARTIAL,

    /**
     * The bill must be paid in one installment with the amount paid being exactly
     * the billed amount.
     */
    EXACT;

    /**
     * Returns the GepgBillPaymentOption corresponding to the specified string
     * value.
     *
     * @param value the string value of the bill payment option
     * @return the corresponding GepgBillPaymentOption
     * @throws IllegalArgumentException if the value does not correspond to any
     *                                  GepgBillPaymentOption
     */
    public static GepgBillPaymentOption fromValue(String value) {
        switch (value) {
            case "1":
                return FULL;
            case "2":
                return PARTIAL;
            case "3":
                return EXACT;
            default:
                throw new IllegalArgumentException("Invalid BillPaymentOption value: " + value);
        }
    }

    /**
     * Gets the string value associated with the bill payment option.
     *
     * @return the string value of the bill payment option
     */
    public String getValue() {
        switch (this) {
            case FULL:
                return "1";
            case PARTIAL:
                return "2";
            case EXACT:
                return "3";
            default:
                throw new IllegalArgumentException("Invalid BillPaymentOption");
        }
    }
}
