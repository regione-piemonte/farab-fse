/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmafarma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per ricetta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricetta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="dataRicetta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0" form="qualified"/>
 *         &lt;element name="cfMedicoPrescrittore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricetta", propOrder = {
    "nre",
    "dataRicetta",
    "cfMedicoPrescrittore"
})
public class Ricetta {

    protected String nre;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRicetta;
    protected String cfMedicoPrescrittore;

    /**
     * Recupera il valore della proprietÃ  nre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNre() {
        return nre;
    }

    /**
     * Imposta il valore della proprietÃ  nre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNre(String value) {
        this.nre = value;
    }

    /**
     * Recupera il valore della proprietÃ  dataRicetta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRicetta() {
        return dataRicetta;
    }

    /**
     * Imposta il valore della proprietÃ  dataRicetta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRicetta(XMLGregorianCalendar value) {
        this.dataRicetta = value;
    }

    /**
     * Recupera il valore della proprietÃ  cfMedicoPrescrittore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfMedicoPrescrittore() {
        return cfMedicoPrescrittore;
    }

    /**
     * Imposta il valore della proprietÃ  cfMedicoPrescrittore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfMedicoPrescrittore(String value) {
        this.cfMedicoPrescrittore = value;
    }

}
