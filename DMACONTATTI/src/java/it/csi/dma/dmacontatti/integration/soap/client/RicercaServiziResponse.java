/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmacontatti.integration.soap.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ricercaServiziResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaServiziResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://deleghebe.csi.it/}serviceResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="servizi" minOccurs="0" form="unqualified"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="servizio" type="{http://deleghebe.csi.it/}servizio" maxOccurs="unbounded" minOccurs="0" form="unqualified"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaServiziResponse", propOrder = {
    "servizi"
})
@XmlRootElement(name="ricercaServiziResponse")
public class RicercaServiziResponse
    extends ServiceResponse
{

    @XmlElement(namespace = "")
    protected RicercaServiziResponse.Servizi servizi;

    /**
     * Gets the value of the servizi property.
     * 
     * @return
     *     possible object is
     *     {@link RicercaServiziResponse.Servizi }
     *     
     */
    public RicercaServiziResponse.Servizi getServizi() {
        return servizi;
    }

    /**
     * Sets the value of the servizi property.
     * 
     * @param value
     *     allowed object is
     *     {@link RicercaServiziResponse.Servizi }
     *     
     */
    public void setServizi(RicercaServiziResponse.Servizi value) {
        this.servizi = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="servizio" type="{http://deleghebe.csi.it/}servizio" maxOccurs="unbounded" minOccurs="0" form="unqualified"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "servizio"
    })
    public static class Servizi {

        @XmlElement(namespace = "")
        protected List<Servizio> servizio;

        /**
         * Gets the value of the servizio property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the servizio property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getServizio().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Servizio }
         * 
         * 
         */
        public List<Servizio> getServizio() {
            if (servizio == null) {
                servizio = new ArrayList<Servizio>();
            }
            return this.servizio;
        }

    }

}
