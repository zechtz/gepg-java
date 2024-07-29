package com.watabelabs.gepg.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * This class is an adapter for JAXB to convert between
 * LocalDateTime and its string representation in the format
 * "yyyy-MM-dd'T'HH:mm:ss'Z'".
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    /**
     * DateTimeFormatter for the format "yyyy-MM-dd'T'HH:mm:ss'Z'".
     */
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /**
     * Unmarshals a string to a LocalDateTime object.
     *
     * @param v the string to be converted
     * @return the corresponding LocalDateTime object
     * @throws Exception if the string cannot be parsed
     */
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DATE_FORMAT);
    }

    /**
     * Marshals a LocalDateTime object to its string representation.
     *
     * @param v the LocalDateTime object to be converted
     * @return the corresponding string representation
     * @throws Exception if the LocalDateTime object cannot be formatted
     */
    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(DATE_FORMAT);
    }
}

