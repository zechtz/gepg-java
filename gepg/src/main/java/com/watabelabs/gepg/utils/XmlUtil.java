package com.watabelabs.gepg.utils;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * Utility class for converting Java objects to XML strings using JAXB.
 */
public class XmlUtil {

    /**
     * Converts a JAXB-annotated object to an XML string.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object
     * @throws Exception if an error occurs during XML conversion
     */
    public static String convertToXmlString(Object object) throws Exception {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(object, sw);
        return sw.toString();
    }

    /**
     * Converts a JAXB-annotated object to an XML string without the XML declaration.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object without the XML declaration
     * @throws Exception if an error occurs during XML conversion
     */
    public static String convertToXmlStringWithoutDeclaration(Object object) throws Exception {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        StringWriter sw = new StringWriter();
        marshaller.marshal(object, sw);
        return sw.toString();
    }
}

