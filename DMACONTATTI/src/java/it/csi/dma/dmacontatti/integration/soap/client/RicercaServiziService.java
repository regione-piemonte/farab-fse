/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmacontatti.integration.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ricercaServiziService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaServiziService"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="getServizi" type="{http://deleghebe.csi.it/}ricercaServizi" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaServiziService", propOrder = {
    "getServizi"
})
public class RicercaServiziService {

    protected RicercaServizi getServizi;

    /**
     * Gets the value of the getServizi property.
     * 
     * @return
     *     possible object is
     *     {@link RicercaServizi }
     *     
     */
    public RicercaServizi getGetServizi() {
        return getServizi;
    }

    /**
     * Sets the value of the getServizi property.
     * 
     * @param value
     *     allowed object is
     *     {@link RicercaServizi }
     *     
     */
    public void setGetServizi(RicercaServizi value) {
        this.getServizi = value;
    }

}
