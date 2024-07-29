package com.watabelabs.gepg.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The {@code CDataAdapter} class is a custom XML adapter for handling CDATA
 * sections
 * in JAXB-annotated objects. This adapter ensures that string content is
 * wrapped
 * in CDATA sections when marshaled to XML and correctly unmarshaled from XML.
 * <p>
 * CDATA sections are used in XML to include data that should not be treated as
 * XML
 * markup. This is useful for including text data that may contain characters
 * that
 * would otherwise need to be escaped.
 * </p>
 */
public class CDataAdapter extends XmlAdapter<String, String> {

    /**
     * Marshals the given string into a CDATA section.
     *
     * @param v the string to be wrapped in a CDATA section
     * @return the string wrapped in a CDATA section
     * @throws Exception if an error occurs during the marshalling process
     */
    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" + v + "]]>";
    }

    /**
     * Unmarshals the given string from a CDATA section.
     * <p>
     * Since the input string is already in a suitable format, this method
     * simply returns the input string as-is.
     * </p>
     *
     * @param v the string to be unmarshaled
     * @return the unmarshaled string
     * @throws Exception if an error occurs during the unmarshalling process
     */
    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }
}

