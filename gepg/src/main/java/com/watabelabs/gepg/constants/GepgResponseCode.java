package com.watabelabs.gepg.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ResponseCode} class provides a set of constants representing
 * various response codes
 * and their corresponding messages. It includes a method to retrieve the
 * message associated with a given code.
 */
public class GepgResponseCode {

    private static final Map<Integer, String> responseMessages = new HashMap<>();

    static {
        responseMessages.put(7101, "SUCCESSFUL");
        responseMessages.put(7201, "FAILURE");
        responseMessages.put(7202, "REQUIRED_HEADER_IS_NOT_GIVEN");
        responseMessages.put(7203, "UNAUTHORIZED");
        responseMessages.put(7204, "BILL_DOES_NOT_EXIST");
        responseMessages.put(7205, "INVALID_SERVICE_PROVIDER");
        responseMessages.put(7206, "SERVICE_PROVIDER_IS_NOT_ACTIVE");
        responseMessages.put(7207, "DUPLICATE_PAYMENT");
        responseMessages.put(7208, "INVALID_BUSINESS_ACCOUNT");
        responseMessages.put(7209, "BUSINESS_ACCOUNT_IS_NOT_ACTIVE");
        responseMessages.put(7210, "COLLECTION_ACCOUNT_BALANCE_LIMIT_REACHED");
        responseMessages.put(7211, "PAYMENT_SERVICE_PROVIDER_CODE_DID_NOT_MATCH_BILL_SERVICE_PROVIDER_CODE");
        responseMessages.put(7212, "PAYMENT_CURRENCY_DID_NOT_MATCH_BILL_CURRENCY");
        responseMessages.put(7213, "BILL_HAS_EXPIRED");
        responseMessages.put(7214, "INSUFFICIENT_AMOUNT_PAID");
        responseMessages.put(7215, "INVALID_PAYMENT_SERVICE_PROVIDER");
        responseMessages.put(7216, "PAYMENT_SERVICE_PROVIDER_IS_NOT_ACTIVE");
        responseMessages.put(7217, "NO_PAYER_EMAIL_OR_PHONE_NUMBER");
        responseMessages.put(7218, "WRONG_PAYER_IDENTITY");
        responseMessages.put(7219, "WRONG_CURRENCY");
        responseMessages.put(7220, "SUB_SERVICE_PROVIDER_IS_INACTIVE");
        responseMessages.put(7221, "WRONG_BILL_EQUIVALENT_AMOUNT");
        responseMessages.put(7222, "WRONG_BILL_MISCELLANEOUS_AMOUNT");
        responseMessages.put(7223, "INVALID_OR_INACTIVE_GFS_OR_SERVICE_TYPE");
        responseMessages.put(7224, "WRONG_BILL_AMOUNT");
        responseMessages.put(7225, "INVALID_BILL_REFERENCE_NUMBER_OR_CODE");
        responseMessages.put(7226, "DUPLICATE_BILL_INFORMATION");
        responseMessages.put(7227, "BLANK_BILL_IDENTIFICATION_NUMBER");
        responseMessages.put(7228, "INVALID_SUB_SERVICE_PROVIDER");
        responseMessages.put(7229, "WRONG_BILL_ITEM_GFS_OR_PAYMENT_TYPE_CODE");
        responseMessages.put(7230, "WRONG_BILL_GENERATION_DATE");
        responseMessages.put(7231, "WRONG_BILL_EXPIRY_DATE");
        responseMessages.put(7232, "CONSUMER_ALREADY_STARTED_BY_ANOTHER_PROCESS");
        responseMessages.put(7233, "CONSUMER_ALREADY_STOPPED_BY_ANOTHER_PROCESS");
        responseMessages.put(7234, "WRONG_BILL_PAYMENT_OPTION");
        responseMessages.put(7235, "BILL_CREATION_COMPLETED_SUCCESSFULLY");
        responseMessages.put(7236, "BILL_CREATION_COMPLETED_WITH_ERRORS");
        responseMessages.put(7237, "BILL_DETAIL_CREATION_COMPLETED_SUCCESSFULLY");
        responseMessages.put(7238, "BILL_DETAIL_CREATION_COMPLETED_WITH_ERRORS");
        responseMessages.put(7239, "NO_EXTERNAL_BILL_SYSTEM_SETTINGS_FOUND");
        responseMessages.put(7240, "FAILED_TO_SAVE_TRANSACTION");
        responseMessages.put(7241, "INVALID_SESSION");
        responseMessages.put(7242, "INVALID_REQUEST_DATA");
        responseMessages.put(7243, "INVALID_CREDIT_ACCOUNT");
        responseMessages.put(7244, "INVALID_TRANSFER_AMOUNT");
        responseMessages.put(7245, "INVALID_CREDIT_ACCOUNT_NAME");
        responseMessages.put(7246, "INVALID_DEBIT_ACCOUNT");
        responseMessages.put(7247, "INVALID_TRANSFER_TRANSACTION_DESCRIPTION");
        responseMessages.put(7248, "INVALID_DEBITOR_BIC");
        responseMessages.put(7249, "WRONG_TRANSFER_DATE");
        responseMessages.put(7250, "INVALID_VALUE_IN_TRANSFER_RESERVED_FIELD_ONE");
        responseMessages.put(7251, "INVALID_TRANSFER_TRANSACTION_NUMBER");
        responseMessages.put(7252, "TRANSFER_TRANSACTION_CREATED_SUCCESSFULLY");
        responseMessages.put(7253, "TRANSFER_TRANSACTION_CREATED_WITH_ERRORS");
        responseMessages.put(7254, "INVALID_USE_PAYMENT_REFERENCE_USE_Y_OR_N");
        responseMessages.put(7255, "INVALID_ITEM_BILLED_AMOUNT");
        responseMessages.put(7256, "INVALID_ITEM_EQUIVALENT_AMOUNT");
        responseMessages.put(7257, "INVALID_ITEM_MISCELLANEOUS_AMOUNT");
        responseMessages.put(7258, "TOTAL_ITEM_BILLED_AMOUNT_MISMATCHES_THE_BILL_AMOUNT");
        responseMessages.put(7259, "TOTAL_ITEM_EQUIVALENT_AMOUNT_MISMATCHES_THE_BILL_EQUIVALENT_AMOUNT");
        responseMessages.put(7260, "TOTAL_ITEM_MISCELLANEOUS_AMOUNT_MISMATCHES_THE_BILL_MISCELLANEOUS_AMOUNT");
        responseMessages.put(7261, "DEFECT_BILL_SAVED_SUCCESSFULLY");
        responseMessages.put(7262, "DEFECT_BILL_SAVED_WITH_ERRORS");
        responseMessages.put(7263, "DEFECT_BILL_ITEMS_SAVED_SUCCESSFULLY");
        responseMessages.put(7264, "DEFECT_BILL_ITEMS_SAVED_WITH_ERRORS");
        responseMessages.put(7265, "BILL_ITEMS_SAVED_SUCCESSFULLY");
        responseMessages.put(7266, "BILL_ITEMS_SAVED_WITH_ERRORS");
        responseMessages.put(7267, "INVALID_EMAIL_ADDRESS");
        responseMessages.put(7268, "INVALID_PHONE_NUMBER");
        responseMessages.put(7269, "INVALID_OR_INACTIVE_SERVICE_PROVIDER_SYSTEM_ID");
        responseMessages.put(7270, "TRANSFER_TRANSACTION_UPDATE_COMPLETED_SUCCESSFULLY");
        responseMessages.put(7271, "TRANSFER_TRANSACTION_UPDATE_COMPLETED_WITH_ERRORS");
        responseMessages.put(7272, "DEFECT_TRANSFER_TRANSACTION_SAVED_SUCCESSFULLY");
        responseMessages.put(7273, "DEFECT_TRANSFER_TRANSACTION_SAVED_WITH_ERRORS");
        responseMessages.put(7274, "DUPLICATE_TRANSFER_TRANSACTION");
        responseMessages.put(7275, "INVALID_SERVICE_PROVIDER_PAYER_ID");
        responseMessages.put(7276, "INVALID_SERVICE_PROVIDER_PAYER_NAME");
        responseMessages.put(7277, "INVALID_BILL_DESCRIPTION");
        responseMessages.put(7278, "INVALID_BILL_APPROVAL_USER");
        responseMessages.put(7279, "BILL_ALREADY_SETTLED");
        responseMessages.put(7280, "BILL_EXPIRED_AND_BILL_MOVE_PROCESS_FAILED");
        responseMessages.put(7281, "INVALID_PAYMENT_TRANSACTION_DATE");
        responseMessages.put(7282, "INVALID_PAYER_EMAIL_OR_PHONE_NUMBER");
        responseMessages.put(7283, "BILL_HAS_BEEN_CANCELLED");
        responseMessages.put(7284, "PAYMENT_CURRENCY_DID_NOT_MATCH_COLLECTION_ACCOUNT_CURRENCY");
        responseMessages.put(7285, "INVALID_BILL_GENERATION_USER");
        responseMessages.put(7286, "BILL_CANCELLATION_PROCESS_FAILED");
        responseMessages.put(7287, "BILL_REFERENCE_NUMBER_DOES_NOT_MEET_REQUIRED_BILL_CONTROL_NUMBER_SPECIFICATIONS");
        responseMessages.put(7288, "DISBURSEMENT_REQUEST_DID_NOT_MATCH_SIGNATURE");
        responseMessages.put(7289, "INVALID_BATCH_GENERATED_DATE");
        responseMessages.put(7290, "TOTAL_BATCH_AMOUNT_CANNOT_BE_ZERO");
        responseMessages.put(7291, "TOTAL_BATCH_AMOUNT_IS_NOT_EQUAL_TO_SUMMATION_OF_ITEMS");
        responseMessages.put(7292, "DUPLICATE_DISBURSEMENT_BATCH");
        responseMessages.put(7293, "INVALID_DISBURSEMENT_PAY_OPTION");
        responseMessages.put(7294, "INVALID_DISBURSEMENT_BATCH_SCHEDULED_DATE");
        responseMessages.put(7295, "INVALID_DISBURSEMENT_NOTIFICATION_TEMPLATE");
        responseMessages.put(7296, "DISBURSEMENT_NOTIFICATION_TEMPLATE_IS_NOT_ACTIVE");
        responseMessages.put(7297, "INACTIVE_CURRENCY");
        responseMessages.put(7298, "INVALID_CURRENCY_FOR_DISBURSEMENT");
        responseMessages.put(7299, "BATCH_ITEM_RECIPIENTS_SHOULD_NOT_EXCEED");
        responseMessages.put(7301, "BILL_HAS_BEEN_PAID_PARTIALLY");
        responseMessages.put(7302, "PAID_AMOUNT_IS_NOT_EXACT_BILLED_AMOUNT");
        responseMessages.put(7303, "INVALID_SIGNATURE");
        responseMessages.put(7304, "INVALID_SIGNATURE_CONFIGURATION_MISSING_PARAMETERS");
        responseMessages.put(7305, "INVALID_BATCH_START_AND_END_DATE");
        responseMessages.put(7306, "BATCH_HAS_NO_ITEM");
        responseMessages.put(7307, "INCONSISTENCY_BATCH_START_END_AND_GENERATED_DATE");
        responseMessages.put(7308, "INVALID_VALUE_IN_TRANSFER_RESERVED_FIELD_TWO");
        responseMessages.put(7309, "INVALID_VALUE_IN_TRANSFER_RESERVED_FIELD_THREE");
        responseMessages.put(7310, "INVALID_TRANSFER_CREDIT_OR_DEBIT_ACCOUNT");
        responseMessages.put(7311, "INVALID_GEPG_CONFIGURATIONS_MISSING_PARAMETERS");
        responseMessages.put(7312, "BATCH_DOES_NOT_EXIST");
        responseMessages.put(7313, "CANCEL_IS_ONLY_FOR_AUTO_PAY_BATCH");
        responseMessages.put(7314, "BATCH_ALREADY_ON_DISBURSEMENT_PROCESS_CANCELLATION_PROCESS_FAILED");
        responseMessages.put(7315, "BATCH_CANCELLATION_PROCESS_FAILED");
        responseMessages.put(7316, "BATCH_ALREADY_CANCELED");
        responseMessages.put(7317, "ERROR_ON_PROCESSING_REQUEST");
        responseMessages.put(7318, "INVALID_RECONCILIATION_REQUEST_DATE");
        responseMessages.put(7319, "RECONCILIATION_REQUEST_DATE_IS_OUT_OF_ALLOWABLE_RANGE");
        responseMessages.put(7320, "INVALID_RECONCILIATION_REQUEST_OPTIONS");
        responseMessages.put(7321, "REQUEST_CANNOT_BE_COMPLETED_TRY_LATER_NO_RECON_FILE");
        responseMessages.put(7322, "INACTIVE_COMMUNICATION_PROTOCOL");
        responseMessages.put(7323, "INVALID_CODE_MISMATCH_OF_SUPPLIED_CODE_ON_INFORMATION_AND_HEADER");
        responseMessages.put(7324, "NO_PAYMENT_FOUND_FOR_SPECIFIED_BILL_CONTROL_NUMBER");
    }

    /**
     * Returns a message corresponding to the given code.
     *
     * @param code the code for which to retrieve the message
     * @return the message corresponding to the given code
     * @throws IllegalArgumentException if the code is not recognized
     */
    public static String getResponseMessage(int code) {
        String message = responseMessages.get(code);
        if (message == null) {
            throw new IllegalArgumentException("Unknown code: " + code);
        }
        return message;
    }
}
