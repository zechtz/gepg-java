package com.watabelabs.gepg.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * The Envelope class wraps any content and the digital signature into an XML
 * structure.
 *
 * @param <T> the type of the content
 */
@XmlRootElement(name = "Gepg")
@XmlAccessorType(XmlAccessType.FIELD)
public class Envelope<T> {

    @XmlAnyElement(lax = true)
    private List<T> content;

    @XmlElement(name = "gepgSignature", required = true)
    private String gepgSignature;

    /**
     * Retrieves the content of the envelope.
     *
     * @return the content of the envelope
     */
    public List<T> getContent() {
        return content;
    }

    /**
     * Sets the content of the envelope.
     *
     * @param content the content to set
     */
    public void setContent(List<T> content) {
        this.content = content;
    }

    /**
     * Retrieves the GePG signature.
     *
     * @return the GePG signature
     */
    public String getGepgSignature() {
        return gepgSignature;
    }

    /**
     * Sets the GePG signature.
     *
     * @param gepgSignature the GePG signature to set
     */
    public void setGepgSignature(String gepgSignature) {
        this.gepgSignature = gepgSignature;
    }
}
