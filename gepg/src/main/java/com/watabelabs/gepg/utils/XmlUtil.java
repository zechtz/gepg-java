package com.watabelabs.gepg.utils;

import java.io.StringReader;
import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

/**
 * Utility class for converting Java objects to XML strings using JAXB
 * and for performing XML operations such as key checks.
 */
public class XmlUtil {

    /**
     * Converts a JAXB-annotated object to an XML string.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object
     * @throws Exception if an error occurs during XML conversion
     *
     *                   Example usage:
     *
     *                   <pre>{@code
     * MyJaxbObject obj = new MyJaxbObject();
     * String xml = XmlUtil.convertToXmlString(obj);
     * System.out.println(xml);
     * }</pre>
     */
    public static String convertToXmlString(Object object) throws Exception {
        String xmlContent = null;
        try {
            // Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            // Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);

            // Write XML to StringWriter
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(object, sw);
            // Verify XML Content
            xmlContent = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new Exception("Error converting object to XML string", e);
        }
        return xmlContent;
    }

    /**
     * Converts a JAXB-annotated object to an XML string without the XML
     * declaration.
     *
     * @param object the JAXB-annotated object to convert
     * @return the XML string representation of the object without the XML
     *         declaration
     * @throws Exception if an error occurs during XML conversion
     *
     *                   Example usage:
     *
     *                   <pre>{@code
     * MyJaxbObject obj = new MyJaxbObject();
     * String xmlWithoutDecl = XmlUtil.convertToXmlStringWithoutDeclaration(obj);
     * System.out.println(xmlWithoutDecl);
     * }</pre>
     */
    public static String convertToXmlStringWithoutDeclaration(Object object) throws Exception {
        // Create a JAXB context for the object's class
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        // Create a Marshaller
        Marshaller marshaller = context.createMarshaller();
        // Set Marshaller properties
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        // Create a custom XML stream writer
        StringWriter stringWriter = new StringWriter();
        XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter = new CustomXmlStreamWriter(
                xMLOutputFactory.createXMLStreamWriter(stringWriter));

        // Marshal the object to the custom XML stream writer
        marshaller.marshal(object, xmlStreamWriter);

        // Return the transformed XML string
        return sanitizeRequest(stringWriter.toString());
    }

    /**
     * Sanitizes an XML string by removing the XML declaration if it exists.
     *
     * @param inputXml The XML string to sanitize.
     * @return A sanitized XML string with the XML declaration removed, or the
     *         original XML in case of
     *         an error.
     */
    public static String sanitizeRequest(String inputXml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(inputXml)));

            // Serialize the modified document back to a string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return inputXml; // Return the input XML as-is in case of an error
        }
    }

    /**
     * Escapes special characters in the provided XML string.
     *
     * @param xmlString The XML string to be escaped.
     * @return The escaped XML string.
     */

    public static String escapeCharacter(String xmlString) {
        // Unescape any pre-escaped characters first
        String unescapedString = htmlUnescape(xmlString);

        // Remove newlines and spaces between tags but keep the inner spaces
        String compressedString = unescapedString.replaceAll(">\\s+<", "><").trim();

        return compressedString;
    }

    /**
     * Unescapes special characters in the provided XML string.
     *
     * @param xmlString The XML string to be unescaped.
     * @return The unescaped XML string.
     */
    private static String htmlUnescape(String xmlString) {
        return xmlString
                .replace("&amp;", "&")
                .replace("&apos;", "'")
                .replace("&Ouml;", "Ö")
                .replace("&quot;", "\"")
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }

    /**
     * Checks if the provided XML string contains all specified keys.
     *
     * @param xmlString the XML string to check
     * @param keys      the keys to check for in the XML string
     * @return true if all keys are found in the XML string, false otherwise
     * @throws Exception if an error occurs during XML parsing or XPath evaluation
     *
     *                   Example usage:
     *
     *                   <pre>{@code
     * String xml = "<root><key1>value1</key1><key2>value2</key2></root>";
     * boolean containsKeys = XmlUtil.checkKeys(xml, "key1", "key2");
     * System.out.println(containsKeys); // Outputs: true
     * }</pre>
     */
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
