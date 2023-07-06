/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.getgeneraotpdevice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per getGeneraOtpDeviceRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="getGeneraOtpDeviceRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://getgeneraotpdevice.msg.interfacews.farmab.dma.csi.it/}serviceRequest">
 *       &lt;sequence>
 *         &lt;element name="cfCittadino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telCittadino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getGeneraOtpDeviceRequest", propOrder = {
    "cfCittadino",
    "telCittadino"
})
public class GetGeneraOtpDeviceRequest
    extends ServiceRequest
{

    protected String cfCittadino;
    protected String telCittadino;

    /**
     * Recupera il valore della proprietÃ  cfCittadino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfCittadino() {
        return cfCittadino;
    }

    /**
     * Imposta il valore della proprietÃ  cfCittadino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfCittadino(String value) {
        this.cfCittadino = value;
    }

    /**
     * Recupera il valore della proprietÃ  telCittadino.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelCittadino() {
        return telCittadino;
    }

    /**
     * Imposta il valore della proprietÃ  telCittadino.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelCittadino(String value) {
        this.telCittadino = value;
    }

}
