/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.farab;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://farab.msg.interfacews.farmab.dma.csi.it/}serviceRequest">
 *       &lt;sequence>
 *         &lt;element name="uuidDevice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceOtp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cfCittadino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dispositivo" type="{http://farab.msg.interfacews.farmab.dma.csi.it/}dispositivo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "uuidDevice",
    "codiceOtp",
    "cfCittadino",
    "dispositivo"
})
@XmlRootElement(name = "certificaDeviceConOtp")
public class CertificaDeviceConOtp
    extends ServiceRequest
{

    protected String uuidDevice;
    protected String codiceOtp;
    protected String cfCittadino;
    protected Dispositivo dispositivo;

    /**
     * Recupera il valore della proprietÃ  uuidDevice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuidDevice() {
        return uuidDevice;
    }

    /**
     * Imposta il valore della proprietÃ  uuidDevice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuidDevice(String value) {
        this.uuidDevice = value;
    }

    /**
     * Recupera il valore della proprietÃ  codiceOtp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceOtp() {
        return codiceOtp;
    }

    /**
     * Imposta il valore della proprietÃ  codiceOtp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceOtp(String value) {
        this.codiceOtp = value;
    }

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
     * Recupera il valore della proprietÃ  dispositivo.
     * 
     * @return
     *     possible object is
     *     {@link Dispositivo }
     *     
     */
    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    /**
     * Imposta il valore della proprietÃ  dispositivo.
     * 
     * @param value
     *     allowed object is
     *     {@link Dispositivo }
     *     
     */
    public void setDispositivo(Dispositivo value) {
        this.dispositivo = value;
    }

}
