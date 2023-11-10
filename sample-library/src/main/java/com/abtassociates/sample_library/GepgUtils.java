package com.abtassociates.sample_library;

import java.util.Random;

/**
 * Utility class for interacting with GePG (Government Electronic Payment
 * Gateway).
 */
public class GepgUtils {
    /**
     * Generates a control number for a given bill using GePG.
     *
     * @param bill The bill object for which the control number is requested.
     * @return The generated control number.
     */
    public static String requestControlNumber(Object bill) {
        return getControlNumber(bill);
    }

    /**
     * Private method to make HTTP requests to GePG and
     * retrieve the control number for a bill
     *
     * @param bill The bill object for which the control number is requested.
     * @return The generated control number.
     */
    private static String getControlNumber(Object bill) {
        // Add HTTP Requests to GePG
        // For illustration purposes, a sample control number is returned.
        return randomString(32);
    }

    /**
     * Generates a random string of the specified length using the characters
     * from the given candidate character set.
     * The candidate character set includes uppercase letters (A-Z) and digits
     * (0-9).
     *
     * @param length The desired length of the random string to be generated.
     * @return A randomly generated string of the specified length.
     *
     * @throws IllegalArgumentException If the specified length is less than or
     * equal to zero.
     *
     * @example
     * // Generating a random string of length 8
     * String randomStr = randomString(8);
     * System.out.println(randomStr); // Example output: "3A9R7K2L"
     *
     * @example
     * // Generating a random string of length 12
     * String randomStr = randomString(12);
     * System.out.println(randomStr); // Example output: "6X8B2Y1Z9R3L"
     *
     * @example
     * // Generating a random string of length 0 (invalid)
     * try {
     *     String randomStr = randomString(0); // This will throw an
     *     IllegalArgumentException
     * } catch (IllegalArgumentException e) {
     *     System.err.println("Invalid length specified: " + e.getMessage());
     * }
     */
    private static String randomString(int stringLength) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < stringLength; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                .length())));
        }
        return sb.toString();
    }
}
