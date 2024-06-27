package com.watabelabs.gepg.utils;

public class GepgEndpoints {
    public static final String SUBMIT_BILL = "/api/bill/sigqrequest";
    public static final String REUSE_CONTROL_NUMBER = "/api/bill/sigqrequest_reuse";
    public static final String UPDATE_BILL = "/api/bill/sigqrequest_change";
    public static final String CANCEL_BILL = "/api/bill/sigcancel_request";

    public static final String REQUEST_RECONCILIATION = "/api/reconciliations/sig_sp_qrequest";

    public static final String SEND_PAYMENT = "/api/sp/paymentRequest";
    public static final String CHECK_PAYMENT_STATUS = "/api/sp/statusRequest";

    // Private constructor to prevent instantiation
    private GepgEndpoints() {
    }
}
