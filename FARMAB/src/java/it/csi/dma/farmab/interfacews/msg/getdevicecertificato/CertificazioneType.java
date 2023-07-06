/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.getdevicecertificato;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per certificazioneType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="certificazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uuidDevice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataCertificazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fonte" type="{http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/}codifica" minOccurs="0"/>
 *         &lt;element name="numTelefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cfCittadino" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dispositivo" type="{http://getdevicecertificato.msg.interfacews.farmab.dma.csi.it/}dispositivo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "certificazioneType", propOrder = {
    "id",
    "uuidDevice",
    "dataCertificazione",
    "fonte",
    "numTelefono",
    "cfCittadino",
    "dispositivo"
})
public class CertificazioneType {

    protected String id;
    protected String uuidDevice;
    protected String dataCertificazione;
    protected Codifica fonte;
    protected String numTelefono;
    protected String cfCittadino;
    protected Dispositivo dispositivo;

    /**
     * Recupera il valore della proprietÃ  id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietÃ  id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

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
     * Recupera il valore della proprietÃ  dataCertificazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataCertificazione() {
        return dataCertificazione;
    }

    /**
     * Imposta il valore della proprietÃ  dataCertificazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataCertificazione(String value) {
        this.dataCertificazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  fonte.
     * 
     * @return
     *     possible object is
     *     {@link Codifica }
     *     
     */
    public Codifica getFonte() {
        return fonte;
    }

    /**
     * Imposta il valore della proprietÃ  fonte.
     * 
     * @param value
     *     allowed object is
     *     {@link Codifica }
     *     
     */
    public void setFonte(Codifica value) {
        this.fonte = value;
    }

    /**
     * Recupera il valore della proprietÃ  numTelefono.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelefono() {
        return numTelefono;
    }

    /**
     * Imposta il valore della proprietÃ  numTelefono.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelefono(String value) {
        this.numTelefono = value;
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
