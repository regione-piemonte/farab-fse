/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmacontatti.integration.soap.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ricercaServiziServiceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaServiziServiceResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ricercaServiziResponse" type="{http://deleghebe.csi.it/}ricercaServiziResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaServiziServiceResponse", propOrder = {
    "ricercaServiziResponse"
})
public class RicercaServiziServiceResponse {

    protected RicercaServiziResponse ricercaServiziResponse;

    /**
     * Gets the value of the ricercaServiziResponse property.
     * 
     * @return
     *     possible object is
     *     {@link RicercaServiziResponse }
     *     
     */
    public RicercaServiziResponse getRicercaServiziResponse() {
        return ricercaServiziResponse;
    }

    /**
     * Sets the value of the ricercaServiziResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link RicercaServiziResponse }
     *     
     */
    public void setRicercaServiziResponse(RicercaServiziResponse value) {
        this.ricercaServiziResponse = value;
    }

}
