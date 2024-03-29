/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.farmab.interfacews.msg.getdevicecertificato;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per dispositivo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="dispositivo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sistemaOperativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="browser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modello" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dispositivo", propOrder = {
    "sistemaOperativo",
    "browser",
    "modello"
})
public class Dispositivo {

    protected String sistemaOperativo;
    protected String browser;
    protected String modello;

    /**
     * Recupera il valore della proprietÃ  sistemaOperativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    /**
     * Imposta il valore della proprietÃ  sistemaOperativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSistemaOperativo(String value) {
        this.sistemaOperativo = value;
    }

    /**
     * Recupera il valore della proprietÃ  browser.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * Imposta il valore della proprietÃ  browser.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrowser(String value) {
        this.browser = value;
    }

    /**
     * Recupera il valore della proprietÃ  modello.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModello() {
        return modello;
    }

    /**
     * Imposta il valore della proprietÃ  modello.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModello(String value) {
        this.modello = value;
    }

}
