/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmafarma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per errore complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="errore">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="descErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errore", propOrder = {
    "codErrore",
    "descErrore"
})
public class Errore {

    protected String codErrore;
    protected String descErrore;

    /**
     * Recupera il valore della proprietÃ  codErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodErrore() {
        return codErrore;
    }

    /**
     * Imposta il valore della proprietÃ  codErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodErrore(String value) {
        this.codErrore = value;
    }

    /**
     * Recupera il valore della proprietÃ  descErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescErrore() {
        return descErrore;
    }

    /**
     * Imposta il valore della proprietÃ  descErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescErrore(String value) {
        this.descErrore = value;
    }

}
