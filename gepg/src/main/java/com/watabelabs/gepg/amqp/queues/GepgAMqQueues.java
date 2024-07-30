package com.watabelabs.gepg.amqp.queues;

/**
 * This class contains constants for RabbitMQ queue names used in the GePG
 * application.
 * These queue names are used to identify different types of messages in the
 * messaging system.
 */
public class GepgAMqQueues {

    /**
     * Queue for bill submission requests.
     */
    public static final String BILL_SUBMISSION_QUEUE = "BILL_SUBMISSION_QUEUE";

    /**
     * Queue for payment submissions requests
     */
    public static final String PAYMENT_SUBMISSION_QUEUE = "PAYMENT_SUBMISSION_QUEUE";

    /**
     * Queue for reconciliation requests.
     */
    public static final String RECONCILIATION_SUBMISSION_QUEUE = "RECONCILIATION_SUBMISSION_QUEUE ";
}
