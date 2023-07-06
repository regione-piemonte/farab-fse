/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmafarma;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per GetDelegantiFarmaciaRequest complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="GetDelegantiFarmaciaRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pinCode" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="datiFarmaciaRichiedente" type="{http://dmafarma.dma.csi.it/}datiFarmaciaRichiedente" form="qualified"/>
 *         &lt;element name="cfCittadinoDelegato" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetDelegantiFarmaciaRequest", propOrder = {
    "pinCode",
    "datiFarmaciaRichiedente",
    "cfCittadinoDelegato"
})
public class GetDelegantiFarmaciaRequest {

    @XmlElement(required = true)
    protected String pinCode;
    @XmlElement(required = true)
    protected DatiFarmaciaRichiedente datiFarmaciaRichiedente;
    @XmlElement(required = true)
    protected String cfCittadinoDelegato;

    /**
     * Recupera il valore della proprietÃ  pinCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * Imposta il valore della proprietÃ  pinCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPinCode(String value) {
        this.pinCode = value;
    }

    /**
     * Recupera il valore della proprietÃ  datiFarmaciaRichiedente.
     * 
     * @return
     *     possible object is
     *     {@link DatiFarmaciaRichiedente }
     *     
     */
    public DatiFarmaciaRichiedente getDatiFarmaciaRichiedente() {
        return datiFarmaciaRichiedente;
    }

    /**
     * Imposta il valore della proprietÃ  datiFarmaciaRichiedente.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiFarmaciaRichiedente }
     *     
     */
    public void setDatiFarmaciaRichiedente(DatiFarmaciaRichiedente value) {
        this.datiFarmaciaRichiedente = value;
    }

    /**
     * Recupera il valore della proprietÃ  cfCittadinoDelegato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfCittadinoDelegato() {
        return cfCittadinoDelegato;
    }

    /**
     * Imposta il valore della proprietÃ  cfCittadinoDelegato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfCittadinoDelegato(String value) {
        this.cfCittadinoDelegato = value;
    }

}
