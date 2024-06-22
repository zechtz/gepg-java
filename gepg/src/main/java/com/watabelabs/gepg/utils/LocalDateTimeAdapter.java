package com.watabelabs.gepg.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v, DATE_FORMAT);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(DATE_FORMAT);
    }
}
