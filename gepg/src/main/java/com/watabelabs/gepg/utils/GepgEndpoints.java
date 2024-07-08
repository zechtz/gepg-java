package com.watabelabs.gepg.utils;

/**
 * This class defines the endpoints used in the GePG API.
 * It contains constants representing the various endpoints for bill submission,
 * control number reuse, bill updates, bill cancellations, reconciliation
 * requests,
 * payment submissions, and payment status checks.
 */
public class GepgEndpoints {

    /**
     * Endpoint for submitting a bill.
     */
    public static final String SUBMIT_BILL = "/api/bill/sigqrequest";

    /**
     * Endpoint for reusing a control number.
     */
    public static final String REUSE_CONTROL_NUMBER = "/api/bill/sigqrequest_reuse";

    /**
     * Endpoint for updating a bill.
     */
    public static final String UPDATE_BILL = "/api/bill/sigqrequest_change";

    /**
     * Endpoint for canceling a bill.
     */
    public static final String CANCEL_BILL = "/api/bill/sigcancel_request";

    /**
     * Endpoint for requesting reconciliation.
     */
    public static final String REQUEST_RECONCILIATION = "/api/reconciliations/sig_sp_qrequest";

    /**
     * Endpoint for sending a payment.
     */
    public static final String SEND_PAYMENT = "/api/sp/paymentRequest";

    /**
     * Endpoint for checking payment status.
     */
    public static final String CHECK_PAYMENT_STATUS = "/api/sp/statusRequest";

    /**
     * Private constructor to prevent instantiation.
     * This class is intended to be used as a utility class for accessing endpoint
     * constants.
     */
    private GepgEndpoints() {
        // Private constructor to prevent instantiation
    }
}
