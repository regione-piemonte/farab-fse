/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmacontatti.integration.soap.client;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ricercaServizi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaServizi"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://deleghebe.csi.it/}serviceRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="servizio" type="{http://deleghebe.csi.it/}servizio" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ricercaServizi")
@XmlType(name = "ricercaServizi", propOrder = {
    "servizio"
})
public class RicercaServizi
    extends ServiceRequest
{

    @XmlElement(namespace = "")
    protected Servizio servizio;

    /**
     * Gets the value of the servizio property.
     * 
     * @return
     *     possible object is
     *     {@link Servizio }
     *     
     */
    public Servizio getServizio() {
        return servizio;
    }

    /**
     * Sets the value of the servizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link Servizio }
     *     
     */
    public void setServizio(Servizio value) {
        this.servizio = value;
    }

}
