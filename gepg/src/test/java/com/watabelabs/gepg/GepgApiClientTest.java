package com.watabelabs.gepg;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.watabelabs.gepg.mappers.bill.GepgBillSubResAck;

class GepgApiClientTest {

    @Test
    void testKeyPairMatch() throws Exception {
        GepgApiClient gepgApiClient = new GepgApiClient();
        boolean isMatch = gepgApiClient.checkKeyPair();
        assertTrue(isMatch, "The private key and public key should match.");
    }

    private static GepgBillSubResAck createData() {
        // Creating and populating the Payment Transaction Information

        return new GepgBillSubResAck(7101);
    }

}
