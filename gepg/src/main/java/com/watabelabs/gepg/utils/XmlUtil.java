package com.watabelabs.gepg.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

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
     * Converts a JAXB-annotated object to an XML string without the XML
     * declaration.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object without the XML
     *         declaration
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

    public static boolean checkKeys(String xmlString, String... keys) throws Exception {
        // Parse the XML string into a DOM document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlString)));

        // Create an XPath expression to check for the keys
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        for (String key : keys) {
            XPathExpression expression = xPath.compile("//*[local-name() = '" + key + "']");
            Node node = (Node) expression.evaluate(document, XPathConstants.NODE);
            if (node == null) {
                return false;
            }
        }

        return true;
    }
}
