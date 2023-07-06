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
 * <p>Classe Java per datiFarmaciaRichiedente complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="datiFarmaciaRichiedente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applicazione" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="applicativoVerticale" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="codFarmacia" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="pIvaFarmacia" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="cfFarmacista" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="ruolo" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="gestionale" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiFarmaciaRichiedente", propOrder = {
    "applicazione",
    "applicativoVerticale",
    "codFarmacia",
    "pIvaFarmacia",
    "cfFarmacista",
    "ruolo",
    "gestionale"
})
public class DatiFarmaciaRichiedente {

    @XmlElement(required = true)
    protected String applicazione;
    @XmlElement(required = true)
    protected String applicativoVerticale;
    @XmlElement(required = true)
    protected String codFarmacia;
    @XmlElement(required = true)
    protected String pIvaFarmacia;
    @XmlElement(required = true)
    protected String cfFarmacista;
    @XmlElement(required = true)
    protected String ruolo;
    @XmlElement(required = true)
    protected String gestionale;

    /**
     * Recupera il valore della proprietÃ  applicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicazione() {
        return applicazione;
    }

    /**
     * Imposta il valore della proprietÃ  applicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicazione(String value) {
        this.applicazione = value;
    }

    /**
     * Recupera il valore della proprietÃ  applicativoVerticale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicativoVerticale() {
        return applicativoVerticale;
    }

    /**
     * Imposta il valore della proprietÃ  applicativoVerticale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicativoVerticale(String value) {
        this.applicativoVerticale = value;
    }

    /**
     * Recupera il valore della proprietÃ  codFarmacia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFarmacia() {
        return codFarmacia;
    }

    /**
     * Imposta il valore della proprietÃ  codFarmacia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFarmacia(String value) {
        this.codFarmacia = value;
    }

    /**
     * Recupera il valore della proprietÃ  pIvaFarmacia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPIvaFarmacia() {
        return pIvaFarmacia;
    }

    /**
     * Imposta il valore della proprietÃ  pIvaFarmacia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPIvaFarmacia(String value) {
        this.pIvaFarmacia = value;
    }

    /**
     * Recupera il valore della proprietÃ  cfFarmacista.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCfFarmacista() {
        return cfFarmacista;
    }

    /**
     * Imposta il valore della proprietÃ  cfFarmacista.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCfFarmacista(String value) {
        this.cfFarmacista = value;
    }

    /**
     * Recupera il valore della proprietÃ  ruolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il valore della proprietÃ  ruolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuolo(String value) {
        this.ruolo = value;
    }

    /**
     * Recupera il valore della proprietÃ  gestionale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGestionale() {
        return gestionale;
    }

    /**
     * Imposta il valore della proprietÃ  gestionale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGestionale(String value) {
        this.gestionale = value;
    }

}
