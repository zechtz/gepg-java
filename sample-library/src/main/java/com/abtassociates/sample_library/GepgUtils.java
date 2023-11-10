package com.abtassociates.sample_library;

/**
 * Utility class for interacting with GePG (Government Electronic Payment
 * Gateway).
 */
public class GepgUtils {
	/**
	 * Generates a control number for a given bill using GePG.
	 *
	 * @param bill
	 *            The bill object for which the control number is requested.
	 * @return The generated control number.
	 */
	public static String requestControlNumber(Object bill) {
		return getControlNumber(bill);
	}

	/**
	 * Private method to make HTTP requests to GePG and
	 * retrieve the control number for a bill.
	 *
	 * @param bill
	 *            The bill object for which the control number is requested.
	 * @return The generated control number.
	 */
	private static String getControlNumber(Object bill) {
		// Add HTTP Requests to GePG
		// For illustration purposes,
		// a sample control number is returned.
		return "sample";
	}
}
