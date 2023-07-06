/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.ricercadocumentiini.dmacc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import it.csi.ricercadocumentiini.dma.RicercaDocumentiIN;
import it.csi.ricercadocumentiini.dma.Richiedente;


/**
 * <p>Classe Java per ricercaDocumentiRequeste complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricercaDocumentiRequeste">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ricercaDocumentiIN" type="{http://dma.csi.it/}ricercaDocumentiIN" minOccurs="0"/>
 *         &lt;element name="richiedente" type="{http://dma.csi.it/}richiedente" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaDocumentiRequeste", propOrder = {
    "ricercaDocumentiIN",
    "richiedente"
})
public class RicercaDocumentiRequeste {

    protected RicercaDocumentiIN ricercaDocumentiIN;
    protected Richiedente richiedente;

    /**
     * Recupera il valore della proprietÃ  ricercaDocumentiIN.
     * 
     * @return
     *     possible object is
     *     {@link RicercaDocumentiIN }
     *     
     */
    public RicercaDocumentiIN getRicercaDocumentiIN() {
        return ricercaDocumentiIN;
    }

    /**
     * Imposta il valore della proprietÃ  ricercaDocumentiIN.
     * 
     * @param value
     *     allowed object is
     *     {@link RicercaDocumentiIN }
     *     
     */
    public void setRicercaDocumentiIN(RicercaDocumentiIN value) {
        this.ricercaDocumentiIN = value;
    }

    /**
     * Recupera il valore della proprietÃ  richiedente.
     * 
     * @return
     *     possible object is
     *     {@link Richiedente }
     *     
     */
    public Richiedente getRichiedente() {
        return richiedente;
    }

    /**
     * Imposta il valore della proprietÃ  richiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link Richiedente }
     *     
     */
    public void setRichiedente(Richiedente value) {
        this.richiedente = value;
    }

}
