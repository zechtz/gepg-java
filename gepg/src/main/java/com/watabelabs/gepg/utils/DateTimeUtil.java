package com.watabelabs.gepg.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class providing various helper methods for file conversion, hashing,
 * date-time formatting, and string manipulation.
 */
public final class DateTimeUtil {

    /**
     * Generates a LocalDateTime object representing a future date and time based on
     * the specified number of days from now.
     *
     * @param days the number of days from now to calculate the future date and time
     * @return the LocalDateTime object representing the future date and time
     *
     *         <p>
     *         Example usage:
     *         </p>
     *
     *         <pre>{@code
     * LocalDateTime futureDateTime = Util.getFutureDateTimeInDays(365);
     * System.out.println(Util.formatDateTime(futureDateTime));
     * }</pre>
     *
     *         <p>
     *         Example return value:
     *         </p>
     *
     *         <pre>{@code
     * "2025-07-04T00:00:00"
     * }</pre>
     */
    public static String getFutureDateTimeInDays(int days) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Add the specified number of days
        return formatDateTime(now.plusDays(days).withHour(0).withMinute(0).withSecond(0).withNano(0));
    }

    /**
     * Generates a LocalDateTime object representing a past date and time based on
     * the specified number of days from now.
     *
     * @param days the number of days from now to calculate the past date and time
     * @return the LocalDateTime object representing the future date and time
     *
     *         <p>
     *         Example usage:
     *         </p>
     *
     *         <pre>{@code
     * LocalDateTime pastDate = Util.getPastDateTimeInDays(365);
     * System.out.println(Util.formatDateTime(futureDateTime));
     * }</pre>
     *
     *         <p>
     *         Example return value:
     *         </p>
     *
     *         <pre>{@code
     * "2023-07-04T00:00:00"
     * }</pre>
     */
    public static String getPastDateTimeInDays(int days) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Add the specified number of days
        return formatDateTime(now.minusDays(days).withHour(0).withMinute(0).withSecond(0).withNano(0));
    }

    /**
     * Generates a LocalDateTime object representing the current date and time.
     *
     * @return the LocalDateTime object representing the current date and time
     *
     *         <p>
     *         Example usage:
     *         </p>
     *
     *         <pre>{@code
     * LocalDateTime currentDateTime = Util.getCurrentDateTime();
     * System.out.println(Util.formatDateTime(currentDateTime));
     * }</pre>
     *
     *         <p>
     *         Example return value:
     *         </p>
     *
     *         <pre>{@code
     * "2024-07-04T00:00:00"
     * }</pre>
     */
    public static String getCurrentDateTime() {
        // Get the current date and time
        return formatDateTime(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
    }

    /**
     * Formats a LocalDateTime object into a string with the format
     * "yyyy-MM-dd'T'HH:mm:ss".
     *
     * @param dateTime the LocalDateTime object to be formatted
     * @return the formatted date-time string
     *
     *         <p>
     *         Example usage:
     *         </p>
     *
     *         <pre>{@code
     * LocalDateTime dateTime = LocalDateTime.now();
     * String formattedDateTime = Util.formatDateTime(dateTime);
     * System.out.println(formattedDateTime);
     * }</pre>
     *
     *         <p>
     *         Example return value:
     *         </p>
     *
     *         <pre>{@code
     * "2024-07-04T00:00:00"
     * }</pre>
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        // Define the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // Format the LocalDateTime object
        return dateTime.format(formatter);
    }

}
